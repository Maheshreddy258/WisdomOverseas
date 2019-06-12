package com.example.mahesh.wisdomoverseas.activities;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.IncomingcallAdapter;
import com.example.mahesh.wisdomoverseas.models.Newresponses.IncomingResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IncomingcallsActivity extends AppCompatActivity {

    Context context;
    Toolbar toolbar;
    RecyclerView mRecycler;
    IncomingcallAdapter incomingcallAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incomingcalls);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mRecycler = (RecyclerView)findViewById(R.id.incomingrecycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(IncomingcallsActivity.this, Manifest.permission.READ_CALL_LOG)) {

                ActivityCompat.requestPermissions(IncomingcallsActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);

            } else {
                ActivityCompat.requestPermissions(IncomingcallsActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);

            }
        } else {

                //Toast.makeText(context, "nooo", Toast.LENGTH_SHORT).show();
           /* TextView textView = findViewById(R.id.textincoming);

            textView.setText(getCallDetails(context));*/

           incomingcallAdapter = new IncomingcallAdapter(getApplicationContext(),getCallDetails(context));
           mRecycler.setAdapter(incomingcallAdapter);

        }
    }

        @Override
        public void onRequestPermissionsResult ( int requestCode, String[] permissions, int[] grantResults){

            switch (requestCode) {
                case 1: {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        if (ContextCompat.checkSelfPermission(IncomingcallsActivity.this,
                                Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {


                            Toast.makeText(this, "Permission granted...", Toast.LENGTH_SHORT).show();
                          /*  TextView textView = findViewById(R.id.textincoming);

                           textView.setText(getCallDetails(context));*/

                            incomingcallAdapter = new IncomingcallAdapter(getApplicationContext(),getCallDetails(context));
                            mRecycler.setAdapter(incomingcallAdapter);
                        }
                    } else {

                        Toast.makeText(this, "No Permission Granted...", Toast.LENGTH_SHORT).show();


                    }
                    return;
                }
            }
        }


    private List<IncomingResponse> getCallDetails(Context context) {


        List<IncomingResponse> list = new ArrayList<>();

        StringBuffer sb = new StringBuffer();
        String strOrder = android.provider.CallLog.Calls.DATE + " DESC";

        Cursor managecursor = getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, strOrder);
        int number = managecursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = managecursor.getColumnIndex(CallLog.Calls.DATE);
        int type = managecursor.getColumnIndex(CallLog.Calls.TYPE);

       // sb.append("Call Details...\n\n");
        //managecursor.moveToFirst();
        while (managecursor.moveToNext()) {

            String phnumber = managecursor.getString(number);
            String phDate = managecursor.getString(date);
            String calltype = managecursor.getString(type);

            Date callDay = new Date(Long.valueOf(phDate));
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy HH:MM");
            String calldate = format.format(callDay);


            String dir = null;
            int dircode = Integer.parseInt(calltype);

            switch (dircode) {

                case CallLog.Calls.INCOMING_TYPE:
                    dir = "INCOMING";
                    break;
            }
            /*sb.append("\n Phone Number :" + phnumber + "\n Call Type :" + dir + "\n Date :" + calldate);
            sb.append("\n---------------------\n");
*/
           list.add(new IncomingResponse(phnumber,calldate,calltype,dir));


        }

        managecursor.close();
       /* return sb.toString();*/
        return list;

    }







/*
    private List<IncomingResponse> getCalls() {

        List<IncomingResponse> list = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_LOGS},1);

        }


        Cursor cursor = getApplicationContext().getContentResolver()
                .query(CallLog.Calls.CONTENT_URI,null,null,null,CallLog.Calls.DATE+"ASC");

        int number = cursor.getColumnIndex(CallLog.Calls.NUMBER);
        int date = cursor.getColumnIndex(CallLog.Calls.DATE);

        cursor.moveToFirst();

        while (cursor.moveToNext()){

            Date date1 = new Date(Long.valueOf(cursor.getString(date)));

            list.add(new IncomingResponse(cursor.getString(number),date1.toString()));
        }


        return  list;
    }


    private  void askPermissions(){

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_CONTACTS},1);
            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.READ_LOGS},1);

        }
    }*/




    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

}
