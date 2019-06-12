package com.example.mahesh.wisdomoverseas.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.mahesh.wisdomoverseas.activities.InfoActivity;
import com.example.mahesh.wisdomoverseas.activities.NewInfoActivity;
import com.example.mahesh.wisdomoverseas.activities.TodayCalls;
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


public class BasicInfofragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    TextView mStdname, mStatus, mQualification, mremtext, mPname, mRemainderdate, mPocupation, mQuestion, mInterestCountry, mRemainder, mCounsultancy, mAbroad, mVisit;
    Button mBtnsubmit;
    RadioGroup mRadiogrp1, mRadiogrp2, mRadiogrp3, mRadiogrp4, mRadiogrp5, mRadiogrp6;
    RadioButton mSelected1, mSelected2, mSelected3, mSelected4, mSelected5, mSelected6, mSelected7, rbintersted,
            rbnotinterested, rbcallback, rbnottresponding, rbswitchoff, rboutofcoverage, rbothers, shortterm, longterm, yes, no, yes1, yes2, no1, no2;
    Spinner mSpinnerCountry, mSpinnerRem;
    EditText mEtstdname, mEtOthers, mEtremtext, mEtPname, mEtPocupation, mEtcounsul, mEtabroad, mEtSpecifydate, mEtremainderdate, mEtpphno, mEtremarks,mEtSpinnerCountry;
    String status = "";
    int int_status = 0;
    String qulification;
    String Knowledgeonabroad;
    String officevisit;
    String sibilingsabroad;
    String studentName = "";
    SharedPref sharedPref;
    TabLayout tabLayout;
    String userName;
    String Callfrom = "reminder", from = "";


    private int mYear, mMonth, mDay, mHour, mMinute;
    DatePickerDialog datePickerDialog;
    Toolbar toolbar;
    Context context;
    DatePicker datePicker;
    int id = 0;
    String Id ;
    Button mBtnnext,mBtnback;

    public BasicInfofragment() {

    }

    public static BasicInfofragment newInstance(String param1, String param2) {
        BasicInfofragment fragment = new BasicInfofragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_basic_infofragment, container, false);


        Log.e("id", "" + id);

        Id=NewInfoActivity.Id;
        studentName = NewInfoActivity.studentName;
        Log.e("Idbasic",""+Id);
        Log.e("studemt name", "" + studentName);
        //from = getArguments().getString("from");

        //id= Integer.parseInt(getArguments().getString("id", String.valueOf(0)));
        //studentName = this.getArguments().getString("studentName");
        //from = getArguments().getString("from");


        mRadiogrp1 = v.findViewById(R.id.radioGroup);
        mRadiogrp2 = v.findViewById(R.id.radioGroup2);
        mRadiogrp3 = v.findViewById(R.id.radioGroup3);
        mRadiogrp4 = v.findViewById(R.id.radioGroup4);
        mRadiogrp5 = v.findViewById(R.id.radioGroup5);
        mRadiogrp6 = v.findViewById(R.id.radioGroup6);

        //radio buttons
        rbintersted = v.findViewById(R.id.rbintersted);
        rbcallback = v.findViewById(R.id.rbcallback);
        rbnottresponding = v.findViewById(R.id.rbnottresponding);
        rbnotinterested = v.findViewById(R.id.rbnotinterested);
        rbswitchoff = v.findViewById(R.id.rbswitchoff);
        rboutofcoverage = v.findViewById(R.id.rboutofcoverage);
        rbothers = v.findViewById(R.id.rbothers);
        shortterm = v.findViewById(R.id.shortterm);
        longterm = v.findViewById(R.id.longterm);
        yes = v.findViewById(R.id.yesrb);
        no = v.findViewById(R.id.norb);
        yes1 = v.findViewById(R.id.yesrb2);
        no1 = v.findViewById(R.id.norb2);
        yes2 = v.findViewById(R.id.yesrb2);
        no2 = v.findViewById(R.id.norb2);
        rbothers.setOnClickListener(this);
        rboutofcoverage.setOnClickListener(this);
        rbswitchoff.setOnClickListener(this);
        rbnotinterested.setOnClickListener(this);
        rbnottresponding.setOnClickListener(this);
        rbcallback.setOnClickListener(this);
        rbintersted.setOnClickListener(this);
        shortterm.setOnClickListener(this);


        mStdname = v.findViewById(R.id.tvinfosname);
        mStatus = v.findViewById(R.id.tvinfostatus);
        mQualification = v.findViewById(R.id.tvinfoqual);
        //mPname = v.findViewById(R.id.tvinfopname);
        //mPocupation = v.findViewById(R.id.tvinfopoccupation);
        mQuestion = v.findViewById(R.id.tvinfoque);
        mInterestCountry = v.findViewById(R.id.tvinfocountry);
        mRemainder = v.findViewById(R.id.tvinforem);
        mCounsultancy = v.findViewById(R.id.tvinfocounsul);
        mAbroad = v.findViewById(R.id.tvinfoabroad);
        mVisit = v.findViewById(R.id.tvinfoofficevisit);
        mremtext = v.findViewById(R.id.tvinforemtext);
        //mRemainderdate = v.findViewById(R.id.tvinforemdate);

        mEtstdname = v.findViewById(R.id.etinfosname);
        if (studentName != null && !studentName.isEmpty()) {
            mEtstdname.setText(studentName);
        }
        mEtOthers = v.findViewById(R.id.etinfootherspecify);
        //mEtPname = v.findViewById(R.id.etinfopname);

        //mEtPocupation = v.findViewById(R.id.etinfopoccupation);
        mEtcounsul = v.findViewById(R.id.etinfoconsulname);
        mEtabroad = v.findViewById(R.id.etinfoabroad);

        mEtSpecifydate = v.findViewById(R.id.etinfotime);
        mEtremainderdate = v.findViewById(R.id.etinforemdate);
        mEtremtext = v.findViewById(R.id.etinforemtext);
        //mEtpphno = v.findViewById(R.id.etinfoparentphno);
        mEtremarks = v.findViewById(R.id.etinfoRemarks);
        mEtSpinnerCountry = v. findViewById(R.id.etinfospinner);
        mEtSpinnerCountry.setVisibility(View.GONE);

        mSpinnerCountry = v.findViewById(R.id.infospinner);
        //mSpinnerRem = v.findViewById(R.id.infospinner2);
        mSpinnerCountry.setOnItemSelectedListener(this);

        final List<String> categories = new ArrayList<String>();
        categories.add("ARMENIA");
        categories.add("CHINA");
        categories.add("GEORGIA");
        categories.add("PHILIPPINES");
        categories.add("RUSSIA");
        categories.add("UKRAINE");
        categories.add("OTHERS");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCountry.setAdapter(dataAdapter);

        mEtremainderdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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

        mEtSpecifydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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


        mEtOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
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



        mRadiogrp3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.shortterm) {
                    qulification = shortterm.getText().toString();
                } else if (i == R.id.longterm) {
                    qulification = longterm.getText().toString();
                } else {
                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.yesrb) {
                    Knowledgeonabroad = yes.getText().toString();


                } else if (checkedId == R.id.norb) {

                    Knowledgeonabroad = no.getText().toString();
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.yesrb2) {
                    sibilingsabroad = yes.getText().toString();
                    mEtabroad.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.norb2) {

                    sibilingsabroad = no.getText().toString();
                    mEtabroad.setVisibility(View.INVISIBLE);

                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRadiogrp6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.yesrb3) {
                    officevisit = yes.getText().toString();
                    mEtSpecifydate.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.norb3) {

                    officevisit = no.getText().toString();
                    mEtSpecifydate.setVisibility(View.INVISIBLE);
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mBtnsubmit = v.findViewById(R.id.btninfosubmit);
        mBtnnext =v.findViewById(R.id.btnbasicinfonext);
        mBtnback = v.findViewById(R.id.btnstdinfoback);

        mBtnsubmit.setOnClickListener(this);
      mBtnback.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              startActivity(new Intent(getActivity(), TodayCalls.class));
              getActivity().finish();

          }
      });


        mBtnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    TabLayout tabs = (TabLayout) (getActivity()).findViewById(R.id.tabs);
                    tabs.getTabAt(1).select();

            }
        });

        return v;
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
                int_status = 3;
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

               /* if (mEtcounsul.length() == 0) {

                    Toast.makeText(getActivity(), "Please Enter Consultancy name", Toast.LENGTH_SHORT).show();

                } else if (mEtremtext.length() == 0) {
                    Toast.makeText(getActivity(), "Please Enter Reminder text", Toast.LENGTH_SHORT).show();


                } else {*/

                    loadInfoData(mEtstdname.getText().toString(), status, userName, mEtOthers.getText().toString(),
                            qulification, /*mEtPname.getText().toString(), mEtpphno.getText().toString(),
                        mEtPocupation.getText().toString(),*/ Knowledgeonabroad, mEtremainderdate.getText().toString(),
                            mEtremtext.getText().toString(), mEtcounsul.getText().toString(), sibilingsabroad, mEtabroad.getText().toString(),

                            officevisit, mEtSpecifydate.getText().toString(), mEtremarks.getText().toString(),
                            mSpinnerCountry.getSelectedItem().toString(),mEtSpinnerCountry.getText().toString(), int_status);

           /*     }*/
            default:
                break;
        }

      /*  if (mEtPname.length() == 0) {
            mEtPname.setError("Please Enter Parent name");
            mEtPname.requestFocus();
        } else if ((mEtpphno.length() < 10)) {
            mEtpphno.setError("Please Enter Parent Valid Phone number");
            mEtpphno.requestFocus();

        } else if (mEtPocupation.length() == 0) {
            mEtPocupation.setError("Please Enter Parent Occupation");
            mEtPocupation.requestFocus();
        } else*/ /*if (mEtcounsul.length() == 0) {



        } else if (mEtabroad.length() == 0) {
            mEtabroad.setError("Please Enter Any Sibilings in abroad");

        } else {

            loadInfoData(mEtstdname.getText().toString(),
                    status, userName,
                    mEtOthers.getText().toString(),
                    qulification,
                  *//*  mEtPname.getText().toString(),
                    mEtpphno.getText().toString(),
                    mEtPocupation.getText().toString(),*//*
                    Knowledgeonabroad,
                    mEtremainderdate.getText().toString(),
                    mEtremtext.getText().toString(),
                    mEtcounsul.getText().toString(),
                    sibilingsabroad,
                    mEtabroad.getText().toString(),
                    officevisit,
                    mEtSpecifydate.getText().toString(),
                    mEtremarks.getText().toString(),
                    mSpinnerCountry.getSelectedItem().toString(), int_status);


        }*/
    }

    private void loadInfoData(String mStdname, String status, String userName, String mEtOthers, String qulification,/* String mEtPname,
                              String mEtpphno, String mEtPocupation, */String knowledgeonabroad, String mEtremainderdate, String mEtremtext,
                              String mEtcounsul, String sibilingsabroad, String mEtabroad, String officevisit, String mEtSpecifydate,
                              String mEtremarks, String mSpinnerCountry,String mESpinnercountry, int int_status) {

        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();


        UpdateInfoResponse updateInfoResponse = new UpdateInfoResponse();

        updateInfoResponse.iD = Integer.parseInt(Id);
        Log.e("id number", "" + updateInfoResponse.iD);
        updateInfoResponse.student = mStdname;
        updateInfoResponse.status = int_status;
        updateInfoResponse.callStatus = status;
        updateInfoResponse.convertedBy = userName;
        updateInfoResponse.calledBy = userName;
        updateInfoResponse.callBackDate = mEtOthers;
        updateInfoResponse.parentsName =  "";
        updateInfoResponse.alterntiveNumber =  "";
       /* if(knowledgeonabroad.equalsIgnoreCase("yes")){
            updateInfoResponse.isIntrest= true;
        }else{
            updateInfoResponse.isIntrest= false;
        }*/
        updateInfoResponse.isIntrest = Boolean.valueOf(knowledgeonabroad);
        updateInfoResponse.intrestedCountry = mSpinnerCountry;
        updateInfoResponse.intrestedCountry = mESpinnercountry;
        updateInfoResponse.reminderDate = mEtremainderdate;
        updateInfoResponse.remninderStatus = true;
        // updateInfoResponse.createddate=Calendar.getInstance().getTime();
        updateInfoResponse.reminderText = mEtremtext;
        updateInfoResponse.recordStatus = true;
        updateInfoResponse.siblingsInabroad = sibilingsabroad;
        updateInfoResponse.siblingCountry = mEtabroad;
        updateInfoResponse.callfrom = Callfrom;
     /*   if(officevisit.equalsIgnoreCase("yes")){
            updateInfoResponse.visitedIntrest=true;
        }else{
            updateInfoResponse.visitedIntrest=false;
        }*/
        updateInfoResponse.visitedIntrest = Boolean.valueOf(officevisit);
        updateInfoResponse.visitingDate = mEtSpecifydate;
        updateInfoResponse.fInalFeedBack = mEtremarks;

        Log.e("updateInfoResponse", "" + updateInfoResponse.toString());

        //set all remaining fields
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<UpdateInfoResponse> updateInfoResponseCall = apiInterface.getUpdateInfoResponse(updateInfoResponse);
        updateInfoResponseCall.enqueue(new Callback<UpdateInfoResponse>() {
            @Override
            public void onResponse(Call<UpdateInfoResponse> call, Response<UpdateInfoResponse> response) {

                if (response != null && response.isSuccessful()) {
                   // Log.e("upload data", "upload successfullyyy......");
                    if (response.body().recordStatus) {


                        Toast.makeText(getActivity(), "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("upload data","upload successfullyyy......");
                      /*  startActivity(new Intent(getActivity(), TodayCalls.class));
                        getActivity().finish();*/

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateInfoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




       /*
        //Call<UpdateInfoResponse> updateInfoResponseCall = apiInterface.getUpdateInfoResponse();
        //*/
    }





    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
         if (item.equalsIgnoreCase("Others")){
             mEtSpinnerCountry.setVisibility(View.VISIBLE);
         }else{
             mEtSpinnerCountry.setVisibility(View.GONE);
         }


        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {


    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }
}
