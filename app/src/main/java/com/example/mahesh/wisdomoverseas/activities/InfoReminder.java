package com.example.mahesh.wisdomoverseas.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.responses.UpdateInfoResponse;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoReminder extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

        TextView mStdname, mStatus, mQualification,mremtext, mPname, mRemainderdate, mPocupation, mQuestion, mPreviousdate, mpreviousdateset,
           mPreviousremarks, mPreviousremarksset,mPreviouscallstatus,mPreviouscallstatusset;
        Button mBtnsubmit;
        RadioGroup mRadiogrp1, mRadiogrp2, mRadiogrp3, mRadiogrp4, mRadiogrp5, mRadiogrp6;
        RadioButton mSelected1, mSelected2, mSelected3, mSelected4, mSelected5, mSelected6, mSelected7, rbintersted,
                rbnotinterested, rbcallback, rbnottresponding, rbswitchoff, rboutofcoverage, rbothers, shortterm, longterm, yes, no, yes1, yes2, no1, no2;
        Spinner mSpinnerCountry, mSpinnerRem;
        EditText mEtstdname, mEtOthers,mEtremtext, mEtPname, mEtPocupation, mEtcounsul, mEtabroad, mEtSpecifydate, mEtremainderdate, mEtpphno,mEtremarks;
        String status = "";
        int int_status=0;
        String qulification;
        String Knowledgeonabroad;
        String officevisit;
        String sibilingsabroad;
        String studentName="";
        SharedPref sharedPref;
        String userName;
        String Callfrom = "Reminder";



        private int mYear, mMonth, mDay, mHour, mMinute;
        DatePickerDialog datePickerDialog;
        Toolbar toolbar;
        Context context;
        DatePicker datePicker;
        int id=0;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_inforeminder);
            toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            id=getIntent().getIntExtra("Id",0);
            studentName= getIntent().getStringExtra("studentName");


            mRadiogrp1 = findViewById(R.id.radioGroup);
            mRadiogrp2 = findViewById(R.id.radioGroup2);
            mRadiogrp3 = findViewById(R.id.radioGroup3);
            mRadiogrp4 = findViewById(R.id.radioGroup4);
            mRadiogrp5 = findViewById(R.id.radioGroup5);
            mRadiogrp6 = findViewById(R.id.radioGroup6);

            //radio buttons
            rbintersted = findViewById(R.id.rbintersted);
            rbcallback = findViewById(R.id.rbcallback);
            rbnottresponding = findViewById(R.id.rbnottresponding);
            rbnotinterested = findViewById(R.id.rbnotinterested);
            rbswitchoff = findViewById(R.id.rbswitchoff);
            rboutofcoverage = findViewById(R.id.rboutofcoverage);
            rbothers = findViewById(R.id.rbothers);
       /*     shortterm = findViewById(R.id.shortterm);
            longterm = findViewById(R.id.longterm);
            yes = findViewById(R.id.yesrb);
            no = findViewById(R.id.norb);
            yes1 =findViewById(R.id.yesrb2);
            no1 = findViewById(R.id.norb2);
            yes2= findViewById(R.id.yesrb2);
            no2= findViewById(R.id.norb2);*/
            rbothers.setOnClickListener(this);
            rboutofcoverage.setOnClickListener(this);
            rbswitchoff.setOnClickListener(this);
            rbnotinterested.setOnClickListener(this);
            rbnottresponding.setOnClickListener(this);
            rbcallback.setOnClickListener(this);
            rbintersted.setOnClickListener(this);



            mStdname = findViewById(R.id.tvinfosname);
            mStatus = findViewById(R.id.tvinfostatus);
            mQualification = findViewById(R.id.tvinfoqual);
            mPname = findViewById(R.id.tvinfopname);
           // mPocupation = findViewById(R.id.tvinfopoccupation);
            mQuestion = findViewById(R.id.tvinfoque);
            mPreviousdate = findViewById(R.id.tvinforempreviousdate);
            mpreviousdateset = findViewById(R.id.tvinforempreviousdateset);
            mPreviousremarks = findViewById(R.id.tvinforempreviousremarks);
            mPreviousremarksset = findViewById(R.id.tvinforempreviousremaksset);
            mPreviouscallstatus = findViewById(R.id.tvinforempreviouscallstatus);
            mPreviouscallstatusset = findViewById(R.id.tvrempreviouscallstatusset);
            mremtext= findViewById(R.id.tvinforemtext);
            //mRemainderdate = findViewById(R.id.tvinforemdate);

            mEtstdname = findViewById(R.id.etinfosname);
            if(studentName!=null && !studentName.isEmpty()){
                mEtstdname.setText(studentName);
            }
            mEtOthers = findViewById(R.id.etinfootherspecify);
            mEtPname = findViewById(R.id.etinfopname);

          //  mEtPocupation = findViewById(R.id.etinfopoccupation);
            mEtcounsul = findViewById(R.id.etinfoconsulname);
            mEtabroad =findViewById(R.id.etinfoabroad);

            mEtSpecifydate = findViewById(R.id.etinfotime);
            mEtremainderdate = findViewById(R.id.etinforemdate);
            mEtremtext = findViewById(R.id.etinforemtext);
            mEtpphno = findViewById(R.id.etinfoparentphno);
            mEtremarks = findViewById(R.id.etinfoRemarks);


           /* mSpinnerCountry = findViewById(R.id.infospinner);
            //mSpinnerRem = findViewById(R.id.infospinner2);
            mSpinnerCountry.setOnItemSelectedListener(this);

            final List<String> categories = new ArrayList<String>();
            categories.add("ARMENIA");
            categories.add("CHINA");
            categories.add("GEORGIA");
            categories.add("PHILIPPINES");
            categories.add("RUSSIA");
            categories.add("UKRAINE");
            categories.add("OTHERS");

            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerCountry.setAdapter(dataAdapter);*/

            mEtremainderdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.mahesh.wisdomoverseas.activities.InfoReminder.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    @SuppressLint("SimpleDateFormat")
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    c.set(year, monthOfYear, dayOfMonth);
                                    String dateString = sdf.format(c.getTime());
                                    mEtremainderdate.setText(dateString);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
            });

          /*  mEtSpecifydate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.mahesh.wisdomoverseas.activities.InfoReminder.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    @SuppressLint("SimpleDateFormat")
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    c.set(year, monthOfYear, dayOfMonth);
                                    String dateString = sdf.format(c.getTime());
                                    mEtSpecifydate.setText(dateString);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
            });
*/
/*

            mEtOthers.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog datePickerDialog = new DatePickerDialog(com.example.mahesh.wisdomoverseas.activities.InfoReminder.this,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {

                                    @SuppressLint("SimpleDateFormat")
                                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                    c.set(year, monthOfYear, dayOfMonth);
                                    String dateString = sdf.format(c.getTime());
                                    mEtOthers.setText(dateString);

                                }
                            }, mYear, mMonth, mDay);
                    datePickerDialog.show();
                    datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                }
            });
*/

            mBtnsubmit = findViewById(R.id.btninfosubmit);
            mBtnsubmit.setOnClickListener(this);
/*
            mRadiogrp3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    if (i == R.id.shortterm) {
                        qulification = shortterm.getText().toString();
                    } else if (i == R.id.longterm) {
                        qulification = longterm.getText().toString();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

          /*  mRadiogrp4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == R.id.yesrb) {
                        Knowledgeonabroad = yes.getText().toString();


                    } else if (checkedId == R.id.norb) {

                        Knowledgeonabroad = no.getText().toString();
                    } else {

                        Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

           /* mRadiogrp5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == R.id.yesrb2) {
                        sibilingsabroad = yes.getText().toString();
                        mEtabroad.setVisibility(View.VISIBLE);


                    } else if (checkedId == R.id.norb2) {

                        sibilingsabroad = no.getText().toString();
                        mEtabroad.setVisibility(View.INVISIBLE);

                    } else {

                        Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/


       /*     mRadiogrp6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == R.id.yesrb3) {
                        officevisit = yes.getText().toString();
                        mEtSpecifydate.setVisibility(View.VISIBLE);

                    } else if (checkedId == R.id.norb3) {

                        officevisit = no.getText().toString();
                        mEtSpecifydate.setVisibility(View.INVISIBLE);
                    } else {

                        Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/

        }


        public boolean validate(EditText editText) {
            boolean valid = true;
            editText.setEnabled(true);
            editText.setError(null);
            String textInputEditText123 = editText.getText().toString();
            if (textInputEditText123.length() == 0) {
                editText.setError("Required");
                editText.requestFocus();
                valid = false;
            } else {
                editText.setError(null);
            }

            return valid;
        }



        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rbothers:
                    status = rbothers.getText().toString();
                    rbothers.setChecked(true);
                    rbintersted.setChecked(false);
                    mEtOthers.setVisibility(View.INVISIBLE);
                    rbcallback.setChecked(false);
                    rbnottresponding.setChecked(false);
                    rbnotinterested.setChecked(false);
                    rbswitchoff.setChecked(false);
                    rboutofcoverage.setChecked(false);
                    break;
                case R.id.rbintersted:
                    status = rbintersted.getText().toString();
                    rbothers.setChecked(false);
                    rbintersted.setChecked(true);
                    rbcallback.setChecked(false);
                    rbnottresponding.setChecked(false);
                    rbnotinterested.setChecked(false);
                    rbswitchoff.setChecked(false);
                    rboutofcoverage.setChecked(false);
                    mEtOthers.setVisibility(View.INVISIBLE);
                    break;
                case R.id.rbcallback:
                    status = rbcallback.getText().toString();
                    rbothers.setChecked(false);
                    rbintersted.setChecked(false);
                    rbcallback.setChecked(true);
                    rbnottresponding.setChecked(false);
                    rbnotinterested.setChecked(false);
                    rbswitchoff.setChecked(false);
                    rboutofcoverage.setChecked(false);
                    mEtOthers.setVisibility(View.VISIBLE);
                    break;
                case R.id.rbnottresponding:
                    status = rbnottresponding.getText().toString();
                    rbothers.setChecked(false);
                    rbintersted.setChecked(false);
                    rbcallback.setChecked(false);
                    rbnottresponding.setChecked(true);
                    rbnotinterested.setChecked(false);
                    rbswitchoff.setChecked(false);
                    rboutofcoverage.setChecked(false);
                    mEtOthers.setVisibility(View.INVISIBLE);
                    break;
                case R.id.rbnotinterested:
                    int_status=3;
                    status = rbnotinterested.getText().toString();
                    rbothers.setChecked(false);
                    rbintersted.setChecked(false);
                    rbcallback.setChecked(false);
                    rbnottresponding.setChecked(false);
                    rbnotinterested.setChecked(true);
                    rbswitchoff.setChecked(false);
                    rboutofcoverage.setChecked(false);
                    mEtOthers.setVisibility(View.INVISIBLE);
                    break;
                case R.id.rbswitchoff:
                    status = rbswitchoff.getText().toString();
                    rbothers.setChecked(false);
                    rbintersted.setChecked(false);
                    rbcallback.setChecked(false);
                    rbnottresponding.setChecked(false);
                    rbnotinterested.setChecked(false);
                    rbswitchoff.setChecked(true);
                    rboutofcoverage.setChecked(false);
                    mEtOthers.setVisibility(View.INVISIBLE);
                    break;
                case R.id.rboutofcoverage:
                    status = rboutofcoverage.getText().toString();
                    rbothers.setChecked(false);
                    rbintersted.setChecked(false);
                    rbcallback.setChecked(false);
                    rbnottresponding.setChecked(false);
                    rbnotinterested.setChecked(false);
                    rbswitchoff.setChecked(false);
                    rboutofcoverage.setChecked(true);
                    mEtOthers.setVisibility(View.INVISIBLE);
                    break;
                case R.id.btninfosubmit:


                    loadInfoData(mEtstdname.getText().toString(), status, userName, mEtOthers.getText().toString(),
                            qulification, mEtPname.getText().toString(), mEtpphno.getText().toString(),
                            mEtPocupation.getText().toString(), Knowledgeonabroad, mEtremainderdate.getText().toString(),
                            mEtremtext.getText().toString(),mEtcounsul.getText().toString(), sibilingsabroad, mEtabroad.getText().toString(),

                            officevisit, mEtSpecifydate.getText().toString(), mEtremarks.getText().toString(),
                            mSpinnerCountry.getSelectedItem().toString(),int_status);



                default:
                    break;
            }

            if (mEtremtext.length() == 0){
                mEtremtext.setError("Please Enter Parent name");
                mEtremtext.requestFocus();
            }/*else if((mEtpphno.length() <10)){
                mEtpphno.setError("Please Enter Parent Valid Phone number");
                mEtpphno.requestFocus();

            }else if (mEtPocupation.length()==0){
                mEtPocupation.setError("Please Enter Parent Occupation");
                mEtPocupation.requestFocus();
            }else if (mEtcounsul.length()==0){
                mEtcounsul.setError("Please Enter Consultancy Name");
                mEtcounsul.requestFocus();

            }else if (mEtabroad.length()==0){
                mEtabroad.setError("Please Enter Any Sibilings in abroad");*/

             else {

                loadInfoData(mEtstdname.getText().toString(),
                        status,userName,
                        mEtOthers.getText().toString(),
                        qulification,
                        mEtPname.getText().toString(),
                        mEtpphno.getText().toString(),
                        mEtPocupation.getText().toString(),
                        Knowledgeonabroad,
                        mEtremainderdate.getText().toString(),
                        mEtremtext.getText().toString(),
                        mEtcounsul.getText().toString(),
                        sibilingsabroad,
                        mEtabroad.getText().toString(),
                        officevisit,
                        mEtSpecifydate.getText().toString(),
                        mEtremarks.getText().toString(),
                        mSpinnerCountry.getSelectedItem().toString(),int_status);


            }
        }

        private void loadInfoData(String mStdname,   String status,String userName,String mEtOthers, String qulification, String mEtPname,
                                  String mEtpphno,   String mEtPocupation,  String knowledgeonabroad, String mEtremainderdate,String mEtremtext,
                                  String mEtcounsul, String sibilingsabroad,String mEtabroad,String officevisit,       String mEtSpecifydate,
                                  String mEtremarks, String mSpinnerCountry,int int_status) {

            sharedPref = new SharedPref(getApplicationContext());
            userName = sharedPref.getUserName();

            UpdateInfoResponse updateInfoResponse=new UpdateInfoResponse();
            updateInfoResponse.iD=id;
            updateInfoResponse.student=mStdname;
            updateInfoResponse.status=int_status;
            updateInfoResponse.callStatus=status;
            updateInfoResponse.convertedBy=userName;
            updateInfoResponse.calledBy=userName;
            updateInfoResponse.callBackDate= mEtOthers;
            updateInfoResponse.parentsName=mEtPname+"";
            updateInfoResponse.alterntiveNumber= mEtpphno+"";
       /* if(knowledgeonabroad.equalsIgnoreCase("yes")){
            updateInfoResponse.isIntrest= true;
        }else{
            updateInfoResponse.isIntrest= false;
        }*/
            updateInfoResponse.isIntrest= Boolean.valueOf(knowledgeonabroad);
            updateInfoResponse.intrestedCountry = mSpinnerCountry;
            updateInfoResponse.reminderDate= mEtremainderdate;
            updateInfoResponse.remninderStatus=true;
            // updateInfoResponse.createddate=Calendar.getInstance().getTime();
            updateInfoResponse.reminderText= mEtremtext;
            updateInfoResponse.recordStatus=true;
            updateInfoResponse.siblingsInabroad=sibilingsabroad;
            updateInfoResponse.siblingCountry=mEtabroad;
     /*   if(officevisit.equalsIgnoreCase("yes")){
            updateInfoResponse.visitedIntrest=true;
        }else{
            updateInfoResponse.visitedIntrest=false;
        }*/
            updateInfoResponse.visitedIntrest= Boolean.valueOf(officevisit);
            updateInfoResponse.visitingDate=mEtSpecifydate;
            updateInfoResponse.fInalFeedBack=mEtremarks;



            //set all remaining fields
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UpdateInfoResponse> updateInfoResponseCall=apiInterface.getUpdateInfoResponse(updateInfoResponse);
            updateInfoResponseCall.enqueue(new Callback<UpdateInfoResponse>() {
                @Override
                public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {
                   // Log.e("data insert","not responding.......");
                     Log.e("respose",""+response.message());
                    if(response!=null && response.isSuccessful()){
                        if(response.body().recordStatus){


                            Toast.makeText(InfoReminder.this, ""+response.body().errorMessage, Toast.LENGTH_SHORT).show();
                            Log.e("upload data","upload successfullyyy......");
                            startActivity(new Intent(InfoReminder.this, RemainderActivity.class));
                            finish();

                        }else{
                            Toast.makeText(InfoReminder.this, ""+response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(InfoReminder.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                    Toast.makeText(InfoReminder.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });




       /*
        //Call<UpdateInfoResponse> updateInfoResponseCall = apiInterface.getUpdateInfoResponse();
        //*/
        }


        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }

        public void onNothingSelected(AdapterView<?> arg0) {


        }


        @Override
        public boolean onSupportNavigateUp() {

            onBackPressed();
            return true;
        }
    }







