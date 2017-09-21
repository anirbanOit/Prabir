package com.oit.test.getdata;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by OPTLPT049 on 9/7/2017.
 */

public class Homepage extends AppCompatActivity {
    private String[] arraySpinner;
    private EditText text_date;
    private Button postApi;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_ID = 100;
    private Dialog dialog;
    private static int RESULT_LOAD = 1;
    private ImageView photo, date_picker;
    private TextView male, female;
    private TextView mResponseTv;
    private String gender;
    private ApiService mAPIService;
    private EditText fname, lname, dob;
    private Spinner dept;
    private Bitmap bitmap;
    String TAG = "Homepage: ";

    String imageString;
    ArrayAdapter adapter;

     private Pattern p,p1;
     private Matcher m,m1;
    public static final String regEx="[a-zA-Z]{3,30}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        dept = (Spinner) findViewById(R.id.dept);
        photo = (ImageView) findViewById(R.id.imageView);
        male = (TextView) findViewById(R.id.male);
        female = (TextView) findViewById(R.id.female);
        postApi = (Button) findViewById(R.id.addbtn);
        fname = (EditText) findViewById(R.id.first_name);
        lname = (EditText) findViewById(R.id.last_name);
        dob = (EditText) findViewById(R.id.text_date);

        mAPIService = RetroClient.getClient().create(ApiService.class);
        arraySpinner = new String[]{
                "Select Department", "android", "ios", ".Net", "php", "ui", "ux", "Testing"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        dept.setAdapter(adapter);
        setCurrentDate();
        setOnClickListener();

    }

   public boolean firstnameval(String firstName, String lastName) {
        p = Pattern.compile(regEx);
        m = p.matcher(firstName);
        p1 = Pattern.compile(regEx);
        m1 = p1.matcher(lastName);
        if (!m.matches() || !m1.matches()) {
            Toast.makeText(Homepage.this, "Name is not valid", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

// date pic

    public void setCurrentDate() {
        text_date = (EditText) findViewById(R.id.text_date);
        date_picker = (ImageView) findViewById(R.id.date_pic);
        final Calendar calendar = Calendar.getInstance();

        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        // set current date into textview
        text_date.setText(new StringBuilder()
                // Month is 0 based, so you have to add 1
                .append(month + 1).append("-")
                .append(day).append("-")
                .append(year).append(" "));

        // set current date into Date Picker
        //date_picker.init(year, month, day, null);

    }

    public void date(View v) {
        showDialog(DATE_DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case DATE_DIALOG_ID:
                // set date picker as current date
                dialog = new DatePickerDialog(this, datePickerListener, year, month, day);
                return dialog;
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            // set selected date into Text View
            text_date.setText(new StringBuilder().append(month + 1)
                    .append("-").append(day).append("-").append(year).append(" "));
            // set selected date into Date Picker
            // date_picker.init(year, month, day, null);
            dialog.dismiss();
        }
    };


    //image load from gallery
    private void setOnClickListener() {


        //male female textView

        male.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = male.getText().toString().trim();
                genderToggle(gender);

            }
        });

        female.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gender = female.getText().toString().trim();
                genderToggle(gender);

            }
        });


        //image click load

        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImg();
            }
        });

//  data post to api


        postApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = fname.getText().toString().trim();
                String lastName = lname.getText().toString().trim();
                String dateOfBirth = dob.getText().toString().trim();
                String department = dept.getSelectedItem().toString().trim();

                if(firstnameval(firstName, lastName)) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap = ((BitmapDrawable) photo.getDrawable()).getBitmap();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageBytes = baos.toByteArray();
                    String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                    String image = imageString.trim();

                    if (!TextUtils.isEmpty(firstName) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(department)
                            && !TextUtils.isEmpty(dateOfBirth) && !TextUtils.isEmpty(image)) {
                        Model post = new Model(firstName, lastName, gender, dateOfBirth, department, image);
                        sendPost(post);
                    }
                }
            }
        });
    }

    public void loadImg() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, RESULT_LOAD);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                photo.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(Homepage.this, "Something went wrong", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(Homepage.this, "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    //gender toggle

    public void genderToggle(String genderSelection) {
        if (genderSelection.equalsIgnoreCase("Male")) {
            //male.setBackgroundColor(Color.parseColor("#00CED1"));
            male.setBackgroundResource(R.drawable.gender);
            ShapeDrawable shapedrawable = new ShapeDrawable();
            shapedrawable.setShape(new RectShape());
            shapedrawable.getPaint().setColor(Color.BLACK);
            shapedrawable.getPaint().setStrokeWidth(10f);
            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
            female.setBackgroundDrawable(shapedrawable);
            //female.setBackgroundColor(Color.parseColor("#E6E6E6"));
        }
        if (genderSelection.equalsIgnoreCase("Female")) {
            // female.setBackgroundColor(Color.parseColor("#00CED1"));
            female.setBackgroundResource(R.drawable.gender);
            ShapeDrawable shapedrawable = new ShapeDrawable();
            shapedrawable.setShape(new RectShape());
            shapedrawable.getPaint().setColor(Color.BLACK);
            shapedrawable.getPaint().setStrokeWidth(10f);
            shapedrawable.getPaint().setStyle(Paint.Style.STROKE);
            male.setBackgroundDrawable(shapedrawable);
            // male.setBackgroundColor(Color.parseColor("#E6E6E6"));
        }
    }


    public void sendPost(Model post) {
        mAPIService.savePost(post).enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.isSuccessful()) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(Homepage.this);
                    } else {
                        builder = new AlertDialog.Builder(Homepage.this);
                    }
                    builder.setTitle("Add User")
                            .setMessage("Data successfully inserted")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Log.e(TAG, "Unable to submit post to API.");
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(Homepage.this);
                } else {
                    builder = new AlertDialog.Builder(Homepage.this);
                }
                builder.setTitle("Add User")
                        .setMessage("Data not Inserted due to Timeout. Please Try Again.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });
    }


    public void getdata(View view) {
        Intent obj = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(obj);
    }


}

