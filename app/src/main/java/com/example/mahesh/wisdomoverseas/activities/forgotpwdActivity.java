package com.example.mahesh.wisdomoverseas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.network.SharedPref;

public class forgotpwdActivity extends AppCompatActivity {

    TextView mflogin,mRetrievepwd;
    EditText mfetlogin;
    Button mfbtn;
    SharedPref sharedPref;
    String userName,pwd;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpwd);

        mflogin = findViewById(R.id.ftxtlogin);
        mfetlogin = findViewById(R.id.fetlogin);
        mfbtn = findViewById(R.id.btnfpwd);
        mRetrievepwd = findViewById(R.id.tvfpwd);

       /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
       //final String floginid =mfetlogin.getText().toString();
       /* toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
       /* getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/

        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserNamePwd();
         pwd = sharedPref.getPwd();

         mfbtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if (mfetlogin.getText().toString().equals(userName)) {
                     SharedPref dataProccessor = new SharedPref(getApplicationContext());

                     pwd = sharedPref.getPwd();
                     mRetrievepwd.setText("Your Password is :"+pwd);
                     mfbtn.setText("Login");
                     mfbtn.setOnClickListener(new View.OnClickListener() {
                         @Override
                         public void onClick(View v) {
                             startActivity(new Intent(forgotpwdActivity.this,LoginActivity.class));
                         }
                     });
                 }
                 else{
                     //Log.e("log","fpwd");
                     Toast.makeText(forgotpwdActivity.this,"Please Provide valid loginid",Toast.LENGTH_SHORT).show();

                 }

             }
         });
    }


    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        //startActivity(new Intent(this, LoginActivity.class));
        return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
