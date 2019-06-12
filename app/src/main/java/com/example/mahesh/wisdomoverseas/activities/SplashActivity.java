package com.example.mahesh.wisdomoverseas.activities;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.LoginActivity;
import com.example.mahesh.wisdomoverseas.network.SharedPref;

public class SplashActivity extends AppCompatActivity {
    Context context;
    SharedPref sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPref=new SharedPref(getApplicationContext());
        overridePendingTransition(R.anim.slide_out, R.anim.in);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    if(sharedPref.isLoggedIn()){
                       // ActivityOptions options =
                         //       ActivityOptions.makeCustomAnimation(context, R.anim.in, R.anim.slide_out);
                        startActivity(new Intent(getApplicationContext(),DashBoardActivity.class));
                        finish();
                    }else{
                        Intent i=new Intent(getBaseContext(),LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                   /* Intent i=new Intent(getBaseContext(),LoginActivity.class);
                    startActivity(i);
                    finish();
*/

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }
}
