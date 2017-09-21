package com.oit.test.phonebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by OPTLPT049 on 8/25/2017.
 */

public class LoginVal extends Activity {

    EditText user,password;
    Button btn;
    Pattern p;
    Pattern p1;
    Matcher m;
    Matcher m1;


    public static final String regEx = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
            +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
            +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
            +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
            +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";
    final String PASSWORD_PATTERN = "^([a-zA-Z0-9@*#]{8,15})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.login);

        user = (EditText) findViewById(R.id.edtuser);
        password = (EditText) findViewById(R.id.edtpass);
        btn = (Button) findViewById(R.id.btnLogin);




       btn.setOnClickListener(new View.OnClickListener() {


                                       @Override

                                       public void onClick(View arg0) {
                                           String email = user.getText().toString().trim();
                                           String pass = password.getText().toString().trim();

                                           p = Pattern.compile(regEx);
                                           m = p.matcher(email);
                                           p1 = Pattern.compile(PASSWORD_PATTERN);
                                           m1 = p1.matcher(pass);

                                           if (!m.matches()) {
                                               Toast.makeText(LoginVal.this, "Email is not valid", Toast.LENGTH_LONG).show();
                                           }
                                           else {
                                               if (!m1.matches()) {
                                                   Toast.makeText(LoginVal.this, "Password is not valid", Toast.LENGTH_LONG).show();
                                               }
                                               else{
                                                   log(email,pass);
                                               }

                                               }
                                           }

                                       }

       );

       }

       public void log(String email, String pass){
           if (email.equalsIgnoreCase(AppConstant.userName) &&
                   pass.equalsIgnoreCase(AppConstant.password)){
               Intent it = new Intent(getApplicationContext(), HomePage.class);
               startActivity(it);

           }
           else {
               Toast.makeText(LoginVal.this, "Username/Password doesnot match", Toast.LENGTH_LONG).show();

           }

       }


   }

