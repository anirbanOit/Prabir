package com.oit.test.getdata;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.oit.test.getdata.R.id.list;
import static com.oit.test.getdata.R.id.search;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ApiService apiInterface;
    EditText inputSearch;
    CustomAdepter adapter;
    ArrayList<Model> mFilteredList = new ArrayList<>();
    ArrayList<Model> StudentList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiInterface = RetroClient.getClient().create(ApiService.class);
        final ProgressDialog pd = new ProgressDialog(MainActivity.this);
        pd.setMessage("loading");
        pd.show();
        Call<ArrayList<Model>> obj = apiInterface.doGetListResources();
        obj.enqueue(new Callback<ArrayList<Model>>() {

            @Override
            public void onResponse(Call<ArrayList<Model>> call, Response<ArrayList<Model>> response) {
                Log.d("TAG", response.body().toString());
                pd.dismiss();

                ArrayList<Model> ob = response.body();

                for (int i = 0; i < ob.size(); i++) {
                    Model obj;
                    obj = (Model) ob.get(i);
                    StudentList.add(obj);
                }
                mFilteredList.addAll(response.body());
                lv = (ListView) findViewById(list);
                adapter = new CustomAdepter(MainActivity.this, ob);
                lv.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ArrayList<Model>> call, Throwable t) {
                pd.dismiss();
                call.cancel();
            }
        });

        inputSearch = (EditText) findViewById(search);

        // Capture Text in EditText
        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                String srch;
                srch = arg0.toString();
                mFilteredList.clear();
                if (srch.isEmpty()) {
                    mFilteredList.addAll(StudentList);
                } else {
                    for (Model student : StudentList) {
                        if (student.getFirstName().contains(srch)) {
                            mFilteredList.add(student);
                        }
                    }
                    if (mFilteredList.isEmpty()) {
                        Toast.makeText(MainActivity.this, "No Data Found", Toast.LENGTH_LONG).show();
                    }
                }
                adapter = new CustomAdepter(MainActivity.this, mFilteredList);
                lv.setAdapter(adapter);
            }
        });
    }
}

