package com.oit.test.phonebook;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ListView;


public class MainActivity extends AppCompatActivity {

    private ListView lvPhone;
    private static final int PERMISSIONS_REQUEST_READ_CONTACTS = 100;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
            //After this point you wait for callback in onRequestPermissionsResult(int, String[], int[]) overriden method


        }
        else {

            lvPhone = (ListView) findViewById(R.id.listPhone);

            List<PhoneBook> listPhoneBook = new ArrayList<PhoneBook>();

            Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,null,null);

            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                listPhoneBook.add(new PhoneBook(
                        BitmapFactory.decodeResource(getResources(), R.drawable.ic_perm_identity_black_24dp),
                        name, phoneNumber, "syantan@gmail.com"));
            }


            PhoneBookAdapter adapter = new PhoneBookAdapter(this, listPhoneBook);
            lvPhone.setAdapter(adapter);
        }
    }
}


