package com.oit.test.fragment;

import android.content.Context;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Surface;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements Communicate{

    private static FrameLayout mFragment;
    private static LinearLayout llayout;
    Fragment2 frg2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_mainl);
        }*/
        llayout = (LinearLayout) findViewById(R.id.or);
        getScreenOrientation(this);
        Fragment1 frg = new Fragment1();
        frg2 = new Fragment2();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fmt = fm.beginTransaction();
        fmt.add(R.id.Frag1, frg, "Fragment 1");
        fmt.add(R.id.Frag2, frg2, "Fragment 2");
        fmt.commit();

    }
    public void getScreenOrientation(Context context){
        final int screenOrientation = getResources().getConfiguration().orientation;
        //Toast.makeText(MainActivity.this,"orient :-" + screenOrientation,Toast.LENGTH_LONG).show();
        switch (screenOrientation) {
            case Configuration.ORIENTATION_PORTRAIT:
                llayout.setOrientation(LinearLayout.VERTICAL);
                break;

          /*  case Surface.ROTATION_90:
                llayout.setOrientation(LinearLayout.HORIZONTAL);
                break;

            case Surface.ROTATION_180:
                llayout.setOrientation(LinearLayout.VERTICAL);
                break;*/
            default:
                llayout.setOrientation(LinearLayout.HORIZONTAL);
        }


    }

    @Override
    public void sendData(String a, String b) {
        frg2.showData(a,b);
    }
}


