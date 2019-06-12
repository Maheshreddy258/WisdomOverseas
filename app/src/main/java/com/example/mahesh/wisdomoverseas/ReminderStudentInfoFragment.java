package com.example.mahesh.wisdomoverseas;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment;
import com.example.mahesh.wisdomoverseas.network.SharedPref;

import java.util.ArrayList;
import java.util.List;


public class ReminderStudentInfoFragment extends Fragment implements AdapterView.OnItemSelectedListener {




    TextView mEntrance, mPassyr, mGaps, mRank, mAttended, mLocation, mQualified, mSSCper, mInteper, mInteryr, mArea, mCountry, mCity, mState, mAddress, mStudentsrankattempts;
    EditText mEtstdpassyr, mEtrank, mEtSSC, mEtInter, mEtQualified, mEtInterpassyr, mEtarea, mEtcity, mEtstate, mEtAddress, mEtCountry, mEtrankattempts;
    RadioGroup mRadiogrp1, mRadiogrp2, mRadiogrp3;
    RadioButton shortterm, longterm, yes, no, local, abroad;
    Spinner mSpinner;
    String status = "";
    int int_status = 0;
    String course;
    String attendedanyentrance;
    SharedPreferences sharedpreferences;
    String preferedlocation;
    String sibilingsabroad;
    String studentName = "";
    SharedPref sharedPref;
    public static final String PREFS_NAMESTDINFO = "Stdinfo_Pref";
    public static final String Std_pass_yr = "std_pass_tr";
    public static final String Std_rank = "std_rank";
    public static final String Std_rank_attempts = "std_rank_attempts";
    public static final String Std_ssc_percentage = "std_ssc_per";
    public static final String Std_inter_percentage = "std_inter_per";
    public static final String Std_exam_qualified = "std_exam_qualified";
    public static final String Std_inter_passyr = "std_inter_passyr";
    public static final String Std_area = "std_area";
    public static final String Std_city = "std_city";
    public static final String Std_state = "std_state";
    public static final String Std_country = "std_country";
    public static final String Std_address = "std_address";
    public static final String Std_Course = "std_course";
    public static final String Std_attendedanyentrance = "std_attendedanyentrance";
    public static final String Std_preferedlocation = "std_location";
    public static final String Std_Gaps = "std_gaps";
    String userName;
    Button mBtnsave, mBtnnext;
    ViewPager viewPager;

    public ReminderStudentInfoFragment() {
        // Required empty public constructor
    }


    public static StudentInfoFragment newInstance(String param1, String param2) {
        StudentInfoFragment fragment = new StudentInfoFragment();
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

        View v = inflater.inflate(R.layout.fragment_reminder_student_info, container, false);
        viewPager = v.findViewById(R.id.viewpager);
        mEntrance = v.findViewById(R.id.tvstudentinfoexamentrance);
        mPassyr = v.findViewById(R.id.tvstdinfopassoutyr);
        mGaps = v.findViewById(R.id.tvstdinfoyrgap);
        mRank = v.findViewById(R.id.tvstdinforank);
        mAttended = v.findViewById(R.id.tvstudentinfoattempts);
        mLocation = v.findViewById(R.id.tvstudentinfoloc);
        mSSCper = v.findViewById(R.id.tvstdinfossc);
        mInteper = v.findViewById(R.id.tvstdinfointer);
        mInteryr = v.findViewById(R.id.tvstdinfointeryr);
        mCity = v.findViewById(R.id.tvstdinfocity);
        mCountry = v.findViewById(R.id.tvstdinfocountry);
        mArea = v.findViewById(R.id.tvstdinfoarea);
        mAddress = v.findViewById(R.id.tvstdinfoaddress);
        mStudentsrankattempts = v.findViewById(R.id.tvstdinforankattempts);
        mState = v.findViewById(R.id.tvstdinfostate);
        mQualified = v.findViewById(R.id.tvstdinfoqualified);

        mEtstdpassyr = v.findViewById(R.id.etstdinfopassyr);
        mEtQualified = v.findViewById(R.id.etstdinfoqualified);
        mEtSSC = v.findViewById(R.id.etstdinfossc);
        mEtInter = v.findViewById(R.id.etstdinfointer);
        mEtInterpassyr = v.findViewById(R.id.etstdinfointeryr);
        mEtcity = v.findViewById(R.id.etstdinfocity);
        mEtCountry = v.findViewById(R.id.etstdinfocountry);
        mEtarea = v.findViewById(R.id.etstdinfoarea);
        mEtrank = v.findViewById(R.id.etstdinforank);
        mEtrankattempts = v.findViewById(R.id.etstdinforankattempts);
        mEtAddress = v.findViewById(R.id.etstdinfoaddress);
        mEtstate = v.findViewById(R.id.etstdinfostate);

        mBtnsave = v.findViewById(R.id.btnstdinfosave);
        mBtnnext = v.findViewById(R.id.btnstdinfnext);


        mRadiogrp1 = v.findViewById(R.id.stdinforadioGroup1);
        mRadiogrp2 = v.findViewById(R.id.stdinforadioGroup2);
        mRadiogrp3 = v.findViewById(R.id.stdinforadioGroup3);

        shortterm = v.findViewById(R.id.shortterm);
        longterm = v.findViewById(R.id.longterm);
        yes = v.findViewById(R.id.stdinfoyes);
        no = v.findViewById(R.id.stdinfono);
        local = v.findViewById(R.id.stdinfolocal);
        abroad = v.findViewById(R.id.stdinfoabroad);

        mBtnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData(v);
            }
        });
        mBtnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mEtstdpassyr.getText().toString().isEmpty()) {

                    Toast.makeText(getActivity(), "Please Enter Student Pass year", Toast.LENGTH_SHORT).show();
                } else if (mEtcity.getText().toString().isEmpty()) {

                    Toast.makeText(getActivity(), "Please Enter Student Pass year", Toast.LENGTH_SHORT).show();
                } else if (mEtstate.getText().toString().isEmpty()) {

                    Toast.makeText(getActivity(), "Please Enter Student Pass year", Toast.LENGTH_SHORT).show();
                } else if (mEtCountry.getText().toString().isEmpty()) {

                    Toast.makeText(getActivity(), "Please Enter Student Pass year", Toast.LENGTH_SHORT).show();
                } else{
                    TabLayout tabs = (TabLayout) (getActivity()).findViewById(R.id.tabs);
                    tabs.getTabAt(2).select();
                }
            }
        });

        mSpinner = v.findViewById(R.id.stdinfospinner);
        //mSpinnerRem = findViewById(R.id.infospinner2);
        mSpinner.setOnItemSelectedListener(this);

        final List<String> categories = new ArrayList<String>();
        categories.add("0");
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");


        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(dataAdapter);

        mRadiogrp1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.shortterm) {
                    course = shortterm.getText().toString();
                } else if (i == R.id.longterm) {
                    course = longterm.getText().toString();
                } else {
                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.stdinfoyes) {
                    attendedanyentrance = yes.getText().toString();


                } else if (checkedId == R.id.stdinfono) {

                    attendedanyentrance = no.getText().toString();
                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.stdinfolocal) {
                    preferedlocation = yes.getText().toString();


                } else if (checkedId == R.id.stdinfoabroad) {

                    preferedlocation = no.getText().toString();


                } else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        return v;
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
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    private void saveData(View v) {

        if (mEtstdpassyr.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "Please Enter Student pass year", Toast.LENGTH_SHORT).show();

        } else if (mEtrank.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "Please Enter Rank", Toast.LENGTH_SHORT).show();

        } else {
            sharedpreferences = getActivity().getSharedPreferences(PREFS_NAMESTDINFO, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String stdPassyr = mEtstdpassyr.getText().toString();
            String stdcourse = course;
            String stdgaps = mSpinner.getSelectedItem().toString();
            String stdrank = mEtrank.getText().toString();
            String stdrankattempts = mEtrankattempts.getText().toString();
            String stdattented = attendedanyentrance;
            String stdPrefloc = preferedlocation;
            String stdqualifiedtest = mEtQualified.getText().toString();
            String stdsscper = mEtSSC.getText().toString();
            String stdinterper = mEtInter.getText().toString();
            String stdinterpassyr = mEtInterpassyr.getText().toString();
            String stdarea = mEtarea.getText().toString();
            String stdcity = mEtcity.getText().toString();
            String stdcountry = mEtCountry.getText().toString();
            String stdstate = mEtstate.getText().toString();
            String stdaddress = mEtAddress.getText().toString();

  /*      Log.e("stdPassyr",stdPassyr);
        Log.e("stdcourse",stdcourse);
        Log.e("stdsscper",stdsscper);
        Log.e("stdinterper",stdinterper);
        Log.e("stdgaps",stdgaps);Log.e("stdrank",stdrank);
        Log.e("stdrankattempts",stdrankattempts);
        Log.e("stdqualifiedtest",stdqualifiedtest);
        Log.e("stdattented",stdattented);

        Log.e("stdrankattempts",stdrankattempts);
        Log.e("stdqualifiedtest",stdqualifiedtest);
        Log.e("stdattented",stdattented);*/

            editor.putString(Std_pass_yr, stdPassyr);
            editor.putString(Std_Course, stdcourse);
            editor.putString(Std_ssc_percentage, stdsscper);
            editor.putString(Std_inter_percentage, stdinterper);
            editor.putString(Std_Gaps, stdgaps);
            editor.putString(Std_rank, stdrank);
            editor.putString(Std_rank_attempts, stdrankattempts);
            editor.putString(Std_exam_qualified, stdqualifiedtest);
            editor.putString(Std_attendedanyentrance, stdattented);
            editor.putString(Std_inter_passyr, stdinterpassyr);
            editor.putString(Std_preferedlocation, stdPrefloc);
            editor.putString(Std_area, stdarea);
            editor.putString(Std_city, stdcity);
            editor.putString(Std_country, stdcountry);
            editor.putString(Std_state, stdstate);
            editor.putString(Std_address, stdaddress);


            editor.commit();

            Toast.makeText(getActivity(), "Data insert successfullyyyy...", Toast.LENGTH_SHORT).show();

        }
    }

}
