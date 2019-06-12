package com.example.mahesh.wisdomoverseas;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.activities.NewInfoActivity;
import com.example.mahesh.wisdomoverseas.activities.TodayCalls;
import com.example.mahesh.wisdomoverseas.fragments.FamilyInfoFragment;
import com.example.mahesh.wisdomoverseas.models.responses.FamilyInfoResponse;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.PREFS_NAMESTDINFO;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_Course;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_address;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_area;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_attendedanyentrance;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_city;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_country;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_exam_qualified;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_inter_passyr;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_inter_percentage;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_pass_yr;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_preferedlocation;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_rank;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_rank_attempts;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_ssc_percentage;
import static com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment.Std_state;


public class ReminderFamilyInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {




    TextView mFathername, mFatheroccupation, mAltnumber, mFathercontact, mSector, mIncomeinlakhs, mIncomestatus, mCaste, mChildren;
    EditText mEtfathername, mEtfatherContact, mEtAltnumber, mEtincomeinlakhs;
    Spinner mFamilySpinner;
    RadioGroup mRadiogrp4, mRadiogrp5, mRadiogrp6, mRadiogrp7, mRadiogrp8;
    RadioButton job, bussiness, farmer, pvt, govt, own, mid, low, high, sc, st, bc, oc, a, b, c, d;
    Button mBtnsubmit, mBtncancel;

    String Occupation, Sector, IncomeStatus, Caste, SubCaste;
    String stdpassyr, stdcourse, preflocation, area, city, state, country, address, qualifiedtest, attentedtests;
    String stdsscper;
    String interper;
    String rank;
    String rankattempts;
    String interpassyr;
    int id = 0;
    int StudentLeadId = 0;
    String studentName = "";
    String fatherName ="";
    SharedPref sharedPref;
    SharedPreferences sharedpreferences;
    String userName;


    public ReminderFamilyInfoFragment() {
        // Required empty public constructor
    }

    public static FamilyInfoFragment newInstance(String param1, String param2) {
        FamilyInfoFragment fragment = new FamilyInfoFragment();
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

        View v = inflater.inflate(R.layout.fragment_reminder_family_info, container, false);
        studentName = ReminderNewInfoActivity.studentName;
        fatherName  = ReminderNewInfoActivity.fatherName;
        Log.e("father name", "" + fatherName);

        mFathername = v.findViewById(R.id.tvfamilyinfopname);

        mFathercontact = v.findViewById(R.id.tvfamilyinfoparentphno);
        mAltnumber = v.findViewById(R.id.tvfamilyinfoparentaltphno);
        mFatheroccupation = v.findViewById(R.id.tvfamilyinfocupation);
        mSector = v.findViewById(R.id.tvfamilyinfosector);
        mIncomestatus = v.findViewById(R.id.tvfamilyinfoincomestatus);
        mCaste = v.findViewById(R.id.tvfamilyinfocaste);
        mIncomeinlakhs = v.findViewById(R.id.tvfamilyinfoincomeinlakhs);
        mChildren = v.findViewById(R.id.tvfamilyinfochilds);

        mFamilySpinner = v.findViewById(R.id.familyinfospinner);

        mEtfathername = v.findViewById(R.id.etfamilyinfopname);
        if (fatherName != null && !fatherName.isEmpty()) {
            mEtfathername.setText(fatherName);
        }
        mEtfatherContact = v.findViewById(R.id.etfamilyinfoparentphno);
        mEtAltnumber = v.findViewById(R.id.etfamilyinfoparentaltphno);
        mEtincomeinlakhs = v.findViewById(R.id.etfamilyinfoincomeinlakhs);

        mRadiogrp4 = v.findViewById(R.id.familyradioGroup4);
        mRadiogrp5 = v.findViewById(R.id.familyradioGroup5);
        mRadiogrp6 = v.findViewById(R.id.familyradioGroup6);
        mRadiogrp7 = v.findViewById(R.id.familyradioGroup7);
        mRadiogrp8 = v.findViewById(R.id.familyradioGroup8);
        mRadiogrp8.setVisibility(View.GONE);

        job = v.findViewById(R.id.job);
        bussiness = v.findViewById(R.id.bussiness);
        farmer = v.findViewById(R.id.farmer);
        pvt = v.findViewById(R.id.pvt);
        govt = v.findViewById(R.id.govt);
        own = v.findViewById(R.id.own);
        mid = v.findViewById(R.id.mid);
        low = v.findViewById(R.id.low);
        high = v.findViewById(R.id.high);
        oc = v.findViewById(R.id.oc);
        bc = v.findViewById(R.id.bc);
        sc = v.findViewById(R.id.sc);
        st = v.findViewById(R.id.st);
        a = v.findViewById(R.id.A);
        b = v.findViewById(R.id.B);
        c = v.findViewById(R.id.c);
        d = v.findViewById(R.id.D);

        mFamilySpinner = v.findViewById(R.id.familyinfospinner);
        //mSpinnerRem = findViewById(R.id.infospinner2);
        mFamilySpinner.setOnItemSelectedListener(this);


        final List<String> categories = new ArrayList<String>();
        categories.add("0");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mFamilySpinner.setAdapter(dataAdapter);

        mRadiogrp4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.job) {
                    Occupation = job.getText().toString();
                } else if (i == R.id.bussiness) {
                    Occupation = bussiness.getText().toString();
                } else if (i == R.id.farmer) {
                    Occupation = farmer.getText().toString();
                } else {
                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.pvt) {
                    Sector = pvt.getText().toString();


                } else if (checkedId == R.id.govt) {

                    Sector = govt.getText().toString();
                } else if (checkedId == R.id.own) {

                    Sector = own.getText().toString();
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.low) {
                    IncomeStatus = low.getText().toString();


                } else if (checkedId == R.id.mid) {

                    IncomeStatus = mid.getText().toString();
                } else if (checkedId == R.id.high) {

                    IncomeStatus = high.getText().toString();
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRadiogrp7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.oc) {
                    mRadiogrp8.setVisibility(View.GONE);

                    Caste = oc.getText().toString();


                } else if (checkedId == R.id.bc) {
                    mRadiogrp8.setVisibility(View.VISIBLE);

                    Caste = bc.getText().toString();
                } else if (checkedId == R.id.sc) {
                    mRadiogrp8.setVisibility(View.GONE);

                    Caste = sc.getText().toString();
                } else if (checkedId == R.id.st) {
                    mRadiogrp8.setVisibility(View.GONE);

                    Caste = st.getText().toString();
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.A) {
                    SubCaste = a.getText().toString();


                } else if (checkedId == R.id.B) {

                    SubCaste = b.getText().toString();
                } else if (checkedId == R.id.c) {

                    SubCaste = c.getText().toString();
                } else if (checkedId == R.id.D) {

                    SubCaste = d.getText().toString();
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mBtnsubmit = v.findViewById(R.id.btnfamilyinfosubmit);
        mBtnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtfathername.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Father name", Toast.LENGTH_SHORT).show();
                }else {
                    postFamilyData(mEtfathername.getText().toString(), mEtfatherContact.getText().toString(), mEtAltnumber.getText().toString()
                            , mEtincomeinlakhs.getText().toString(), Occupation, Sector, IncomeStatus, Caste, SubCaste, mFamilySpinner.getSelectedItem().toString(),
                            stdpassyr, stdcourse, stdsscper, interper, interpassyr, rank, rankattempts, qualifiedtest, attentedtests, preflocation,
                            area, city, country, state, address);
                }
            }
        });

        mBtncancel = v.findViewById(R.id.btnfamilyinfocancel);
        mBtncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), TodayCalls.class));
                // getActivity().finish();

            }
        });

        return v;
    }

    private void postFamilyData(String fathername, String fathercontact, String fatheraltcontact, String mincomeinlakhs,
                                String occupation, String sector, String incomeStatus, String caste, String subcaste, String children, String stdpassyr,
                                String stdcourse, String stdsscper, String interper, String interpassyr, String rank, String rankattempts,
                                String qualifiedtest, String attentedtests, String preflocation, String area,
                                String city, String country, String state, String address) {


        sharedPref = new SharedPref(getActivity());

        userName = sharedPref.getUserName();
        sharedpreferences = getActivity().getSharedPreferences(PREFS_NAMESTDINFO, Context.MODE_PRIVATE);
        stdpassyr = sharedpreferences.getString(Std_pass_yr, stdpassyr);
        stdcourse = sharedpreferences.getString(Std_Course, stdcourse);
        Log.e("course", "" + stdcourse);

        stdsscper = sharedpreferences.getString(Std_ssc_percentage, "");
        interper = sharedpreferences.getString(Std_inter_percentage, "");
        rank = sharedpreferences.getString(Std_rank,rank);
        rankattempts = sharedpreferences.getString(Std_rank_attempts, "");
        qualifiedtest = sharedpreferences.getString(Std_exam_qualified, qualifiedtest);
        attentedtests = sharedpreferences.getString(Std_attendedanyentrance, attentedtests);
        interpassyr = sharedpreferences.getString(Std_inter_passyr, "");
        preflocation = sharedpreferences.getString(Std_preferedlocation, preflocation);
        area = sharedpreferences.getString(Std_area, area);
        city = sharedpreferences.getString(Std_city, city);
        country = sharedpreferences.getString(Std_country, country);
        state = sharedpreferences.getString(Std_state, state);
        address = sharedpreferences.getString(Std_address, address);


        FamilyInfoResponse familyInfoResponse = new FamilyInfoResponse();
        familyInfoResponse.iD = id;
        familyInfoResponse.studentLeadId = StudentLeadId;
        familyInfoResponse.studentName = studentName;
        familyInfoResponse.fatherName = fathername;
        familyInfoResponse.contactNumber = fathercontact;
        familyInfoResponse.altenative = fatheraltcontact;
        familyInfoResponse.occupation = occupation;
        familyInfoResponse.sector = sector;
        familyInfoResponse.incomeStatus = incomeStatus;
        familyInfoResponse.incomeinLacks = mincomeinlakhs;
        familyInfoResponse.cast = caste + "-" + subcaste;
        familyInfoResponse.children = Integer.valueOf(children);
        familyInfoResponse.entranceCourse = stdcourse;
        familyInfoResponse.qualifiedEanmName = qualifiedtest;
        familyInfoResponse.qulaifiedTest = Boolean.valueOf(attentedtests);
        familyInfoResponse.rank = Integer.parseInt(rank);
        familyInfoResponse.rankAttempts = Integer.parseInt(rankattempts);
        familyInfoResponse.prefLocatin = preflocation;
        familyInfoResponse.countryName = country;
        Log.e("stdsscper",stdsscper);
        Log.e("interper",interper);
        familyInfoResponse.ssc = Double.parseDouble(stdsscper);
        familyInfoResponse.inter = Double.parseDouble(interper);
        familyInfoResponse.interPassout = Integer.parseInt(interpassyr);
        familyInfoResponse.city = city;
        familyInfoResponse.state = state;
        familyInfoResponse.address = address;
        familyInfoResponse.area = area;
        familyInfoResponse.leadContact = userName;
        familyInfoResponse.recordStatus = true;

        Log.e("family InfoResponse", "" + familyInfoResponse.toString());

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<FamilyInfoResponse> familyInfoResponseCall = apiInterface.getFamilyInfoResponse(familyInfoResponse);
        familyInfoResponseCall.enqueue(new Callback<FamilyInfoResponse>() {
            @Override
            public void onResponse(Call<FamilyInfoResponse> call, Response<FamilyInfoResponse> response) {

                if (response != null && response.isSuccessful()) {
                    Log.e("upload data", "upload successfullyyy......");
                    if (response.body().recordStatus) {
                        Toast.makeText(getActivity(), "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("upload data","upload successfullyyy......");
                        startActivity(new Intent(getActivity(), TodayCalls.class));
                        getActivity().finish();

                    } else {
                        Toast.makeText(getActivity(), "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<FamilyInfoResponse> call, Throwable t) {
                Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {


    }


    public void onButtonPressed(Uri uri) {

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
