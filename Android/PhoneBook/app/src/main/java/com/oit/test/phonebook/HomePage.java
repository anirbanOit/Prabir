package com.oit.test.phonebook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomePage extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


    }
    public void gmap(View vew){
        Intent obj1=new Intent(getApplicationContext(),GMap.class);
        startActivity(obj1);
    }
    public void phbook(View view){


       Intent obj=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(obj);
    }


}









