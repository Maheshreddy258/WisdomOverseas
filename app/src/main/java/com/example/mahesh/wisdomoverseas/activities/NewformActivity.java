package com.example.mahesh.wisdomoverseas.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.IncomingcallAdapter;
import com.example.mahesh.wisdomoverseas.models.Newresponses.CheckResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.IncomingResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewAllCallsResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewCallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewInterestedCallResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPendingRemindersResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPostDataResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewRemindersInfoResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodaycallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewUpdateInfoResponse;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewformActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


    TextView mStdname, mStatus, mQualification, mremtext, mPname, mRemainderdate, mPocupation, mQuestion, mInterestCountry, mRemainder,
            mCounsultancy, mAbroad, mVisit, mTenthmarks, mTenthintrestedcourse, mTenthIntrestedbranch, mTenthEntranceEzam,
            mTenthEntraceQualification, mIntermarks, mInterCourse, mInterPassyear, mInterInterestedCourse, mInterAttemptedEntrance, mGraduniversity,
            mGradDegree, mGradDegreemarks, mGradpassyr, mGradscore, mGradInterestedCourse, mCaste, mSubCaste, mGender, mLocation, mStateofInterest, mState,
            mRegistrationTest, mLocalBudget, mParentContact, mAltNumber, mSector, mIncomeInlakhs, mAbroadInterestCountry, mAbroadBudget, mMotherOccupation,
            mVisittype, mMotherjobsector, mFollowup, mCity, mAreaname, mStdAltcontact, mMothername, mChildren, mRemtext2, mEntrancetestmarks, mMotherIncome,
            mIntrestedareas, mRemindersetting, mTvcontactnumber, mTvSource;
    Button mBtnsubmit, mAdd, mBtnCancel, mBtnUpload, mBtnCheck;
    RadioGroup mRadiogrp1, mRadiogrp2, mRadiogrp3, mRadiogrp4, mRadiogrp5, mRadiogrp6, mRadiogrptenth, mRadioGrpInterAttempted,
            mRadiogrpCaste, mRadiogrpSubcaste, mRadioGrpGender, mRadioGrpStateofInterest, mFatherOcupation, mRadioGrpSector, mRadioGrpApproached,
            mRadioGrpmotheroccupation, mRadiogrpvisittype, mRadioGrpmotherjobsector, mRadiogrpfollowup, mRadioGrpintrestedarea, mRadioGrpreminderset;
    RadioButton mSelected1, mSelected2, mSelected3, mSelected4, mSelected5, mSelected6, mSelected7, rbintersted,
            rbnotinterested, rbcallback, rbnottresponding, rbswitchoff, rboutofcoverage, rbothers, shortterm,
            longterm, yes, no, yes1, yes2, no1, no2, ssc, inter, graduation, yes4, no4, rbsc, rbst, rbbc, rboc, a, b, c, d, male, female,
            local, abroad, fatherjob, bussiness, farmer, pvt, govt, own, mjob, housewife, house, rbofficevisit, personal, motherpvt, mothergovt, motherown,
            followupyes, followupno, intlongterm, intshortterm, reminderset, rbstateofintrestlongterm, appparent, appcounsultancy, yesrb3, norb3, yesknow, noknow;
    Spinner mSpinnerCountry, mSpinnerRem, mSpinnerInterInterestedCourse, mSpinnerinterentranceAttempts, mSpinnerGradInterstedCourse,
            mSpinnerInterCourse, mSpinnerStates, mSpinnerChildren;
    EditText mEtstdname, mEtOthers, mEtremtext, mEtPname, mEtPocupation, mEtcounsul, mEtabroad, mEtSpecifydate, mEtremainderdate, mEtpphno,
            mEtremarks, mEttenthmarks, mEttenthInterestdCourse, mEttenthinterestedbranch, mEttenthEntranceExam, mEttenthEntranceQual, mEtintermarks,
            mEtinterCourse, mEtinterpassyr, mEtinterinterestCourseother, mEtattempeExpectedmarks, mEtPreviousmarks, mEtmarksobtained, mEtGraduniversity,
            mEtGradDegree, mEtGradDegreemarks, mEtGradpassyr, mEtGradScore, mEtGradInterestedCourse, mEtsubcaste, mEtLocation, mEtRegistrationTest,
            mEtLocalBudget, mEtParentAlt, mEtIncomeinLakhs, mEtCountryOthers, mEtAbroadBudget, mEtSibilingsfee, mEtSibilingsDuration, mEtAreaname, mEtCity,
            mEtStdaltcontact, mEtmothername, mEtvisittext, mEtremindertext, mEtEntrancetestmarks, mEtfeedback, mEtvisitmessage, mEtGovtjobtitle,
            mEtmotherincome, mEtotherstatus, mEtfatherbussiness, mEtmotherdepartment, mEtcontactnumber, mEtSource;
    String status = "";
    int int_status = 0;
    String qulification;
    String Knowledgeonabroad;
    String officevisit, officevisittype;
    String SSC, Inter, Graduation, caste, subcaste, StudentQualification, gender, FatherOcupation, MotherOcupation, JobSector, motherjobsector, Followup,
            stateinterest, intrestedareas, remindersetting;
    String sibilingsabroad;
    String studentName = "", leadfrom = "", assignedrm = "",
            city = "", country = "", intercourse = "", interintrestedcourse = "", Spinnerstates = "", children = "", Attempts = "", phonenumber = "";
    SharedPref sharedPref;
    String userName, contactNumber;
    String Callfrom = "Reminder", from = "";
    private LinearLayout parentLayout;
    private int hint = 0;

    private CheckBox chkyes;
    List<String> categories;

    List<String> states;
    List<String> categoriesInterCourse;
    List<String> categoriesCourse;


    private int mYear, mMonth, mDay, mHour, mMinute, mSec;
    DatePickerDialog datePickerDialog;
    Toolbar toolbar;
    Context context;
    DatePicker datePicker;
    int id = 0, rmassignedstatus = 0, leadstatus = 0, callbacks = 0, interpassyr = 0, gradpassyr = 0;
    Double sscmarks = Double.valueOf(0);
    Double previousmarks = Double.valueOf(0);
    Double intermarks = Double.valueOf(0);
    Double gradmarks = Double.valueOf(0);
    Double entraancemarks = Double.valueOf(0);
    Double expectedmarks = Double.valueOf(0);
    int mlocalbudget = 0, mabroadbudget = 0, motherincomeinlakhs = 0;


    NewAllCallsResponse newTodayCallResponse = null;
    NewInterestedCallResponse newInterestedCallResponse = null;
    NewRemindersInfoResponse newremindercallresponse = null;
    NewCallBacksResponse newCallBacksResponse = null;
    NewPendingRemindersResponse newPendingRemindersResponse = null;
    NewUpdateInfoResponse newUpdateInfoResponse = null;
    NewTodaycallBacksResponse newTodaycallBacksResponse = null;
    List<IncomingResponse> incomingResponseList;

    int spinnerstatesPosition;
    int spinnerentrancePosition;
    int spinnerintercoursePosition;
    int spinnerchildrenPosition;
    int spinnerPosition;
    int spinnerinterintrestedcoursePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newform);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        phonenumber = getIntent().getStringExtra("Phonenumber");
        Log.e("phonenumberrr", "" + phonenumber);

        id = getIntent().getIntExtra("Id", 0);
        studentName = getIntent().getStringExtra("studentName");
        city = getIntent().getStringExtra("city");
        from = getIntent().getStringExtra("from");
        country = getIntent().getStringExtra("IntrestedCountry");
        Spinnerstates = getIntent().getStringExtra("states");
        children = getIntent().getStringExtra("children");
        Attempts = getIntent().getStringExtra("Attempts");

        intercourse = getIntent().getStringExtra("InterCourse");
        interintrestedcourse = getIntent().getStringExtra("intrestedcourese");
        Log.e("id", "" + id);
        Log.e("studentName", "" + studentName);


        parentLayout = (LinearLayout) findViewById(R.id.linearadd);
        mRadiogrp1 = findViewById(R.id.radioGroup);
        mRadiogrp2 = findViewById(R.id.radioGroup2);
        mRadiogrp3 = findViewById(R.id.radioGroup3);
        mRadiogrp4 = findViewById(R.id.radioGroup4);
        mRadiogrp5 = findViewById(R.id.radioGroup5);
        mRadiogrp6 = findViewById(R.id.radioGroup6);
        mRadiogrptenth = findViewById(R.id.radioGroupqual);
        mRadioGrpInterAttempted = findViewById(R.id.radioGroupanyentrance);
        mRadioGrpInterAttempted.setVisibility(View.GONE);
        mRadiogrpCaste = findViewById(R.id.radioGroupreservation);
        mRadiogrpSubcaste = findViewById(R.id.radioGroupsubreservation);
        mRadiogrpSubcaste.setVisibility(View.GONE);
        mRadioGrpGender = findViewById(R.id.radioGroupgen);
        mRadioGrpStateofInterest = findViewById(R.id.radioGroupstate);
        mFatherOcupation = findViewById(R.id.radioGroupfatheroccupation);
        mRadioGrpSector = findViewById(R.id.radioGroupsector);
        mRadioGrpSector.setVisibility(View.GONE);
        mRadioGrpApproached = findViewById(R.id.radioGroupconsultancy);
        mRadioGrpmotheroccupation = findViewById(R.id.radioGroupmotheroccupation);
        mRadiogrpvisittype = findViewById(R.id.radioGroupvisittype);
        mRadioGrpmotherjobsector = findViewById(R.id.radioGroupmothersector);
        mRadioGrpmotherjobsector.setVisibility(View.GONE);
        mRadiogrpfollowup = findViewById(R.id.radioGroupfollowup);
        mRadioGrpintrestedarea = findViewById(R.id.radioGroupintrestedarea);
        //mRadioGrpreminderset = findViewById(R.id.radioGroupreminder);


        //radio buttons
        rbintersted = findViewById(R.id.rbintersted);
        rbcallback = findViewById(R.id.rbcallback);
        rbnottresponding = findViewById(R.id.rbnottresponding);
        rbnotinterested = findViewById(R.id.rbnotinterested);
        rbswitchoff = findViewById(R.id.rbswitchoff);
        rboutofcoverage = findViewById(R.id.rboutofcoverage);
        rbothers = findViewById(R.id.rbothers);
        shortterm = findViewById(R.id.shortterm);
        longterm = findViewById(R.id.longterm);
        yes = findViewById(R.id.yesrb);
        no = findViewById(R.id.norb);
        yes1 = findViewById(R.id.yesrb2);
        no1 = findViewById(R.id.norb2);
        yes2 = findViewById(R.id.yesrb2);
        no2 = findViewById(R.id.norb2);
        yesrb3 = findViewById(R.id.yesrb3);
        norb3 = findViewById(R.id.norb3);
        ssc = findViewById(R.id.tenth);
        inter = findViewById(R.id.Inter);
        graduation = findViewById(R.id.graduation);
        yes4 = findViewById(R.id.yesrb4);
        no4 = findViewById(R.id.norb4);
        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        rboc = findViewById(R.id.rboc);
        rbbc = findViewById(R.id.rbbc);
        rbsc = findViewById(R.id.rbsc);
        rbst = findViewById(R.id.rbst);
        a = findViewById(R.id.A);
        b = findViewById(R.id.B);
        c = findViewById(R.id.c);
        d = findViewById(R.id.D);
        local = findViewById(R.id.local);
        abroad = findViewById(R.id.Abroad);
        fatherjob = findViewById(R.id.fatherjob);
        bussiness = findViewById(R.id.fatherbussiness);
        farmer = findViewById(R.id.ffarmer);
        pvt = findViewById(R.id.infopvt);
        govt = findViewById(R.id.infogovt);
        //  own = findViewById(R.id.infoown);
        mjob = findViewById(R.id.motherjob);
        housewife = findViewById(R.id.housewife);
        house = findViewById(R.id.house);
        rbofficevisit = findViewById(R.id.Officevisit);
        //personal = findViewById(R.id.personal);
        motherpvt = findViewById(R.id.motherinfopvt);
        mothergovt = findViewById(R.id.motherinfogovt);
        //  motherown = findViewById(R.id.motherinfoown);
        followupyes = findViewById(R.id.followupyes);
        followupno = findViewById(R.id.followupno);
        intlongterm = findViewById(R.id.infointlongterm);
        intshortterm = findViewById(R.id.infoshortterm);
        // reminderset = findViewById(R.id.remyes);
        rbstateofintrestlongterm = findViewById(R.id.statelongterm);
        appparent = findViewById(R.id.parent);
        appcounsultancy = findViewById(R.id.consultancy);

        rbothers.setOnClickListener(this);
        rboutofcoverage.setOnClickListener(this);
        rbswitchoff.setOnClickListener(this);
        rbnotinterested.setOnClickListener(this);
        rbnottresponding.setOnClickListener(this);
        rbcallback.setOnClickListener(this);
        rbintersted.setOnClickListener(this);
        shortterm.setOnClickListener(this);

        mStdname = findViewById(R.id.tvinfosname);
        mStatus = findViewById(R.id.tvinfostatus);
        mQualification = findViewById(R.id.tvinfoqual);
        mPname = findViewById(R.id.tvinfopname);
        mPocupation = findViewById(R.id.tvinfocupation);
        mQuestion = findViewById(R.id.tvinfoque);
        mInterestCountry = findViewById(R.id.tvinfocountry);
        mRemainder = findViewById(R.id.tvinforem);
        mCounsultancy = findViewById(R.id.tvinfocounsul);
        mAbroad = findViewById(R.id.tvinfoabroad);
        mVisit = findViewById(R.id.tvinfoofficevisit);
        mremtext = findViewById(R.id.tvinforemtext);
        mTenthmarks = findViewById(R.id.tvinfotenthpercentage);
        mTenthmarks.setVisibility(View.GONE);
        mTenthintrestedcourse = findViewById(R.id.tvinfointerestcourses);
        mTenthintrestedcourse.setVisibility(View.GONE);
        mTenthIntrestedbranch = findViewById(R.id.tvinfointerestedbranch);
        mTenthIntrestedbranch.setVisibility(View.GONE);
        mTenthEntranceEzam = findViewById(R.id.tvinfoentranceexam);
        mTenthEntranceEzam.setVisibility(View.GONE);
        mTenthEntraceQualification = findViewById(R.id.tvinfoentrancequal);
        mTenthEntraceQualification.setVisibility(View.GONE);
        mIntermarks = findViewById(R.id.tvinfointerpercentage);
        mIntermarks.setVisibility(View.GONE);
        mInterCourse = findViewById(R.id.tvinfointercourse);
        mInterCourse.setVisibility(View.GONE);
        mInterPassyear = findViewById(R.id.tvinfointerpassoutyear);
        mInterPassyear.setVisibility(View.GONE);
        mInterInterestedCourse = findViewById(R.id.tvinfointerinterestedcourse);
        mInterInterestedCourse.setVisibility(View.GONE);
        mInterAttemptedEntrance = findViewById(R.id.tvinfoanyentrance);
        mInterAttemptedEntrance.setVisibility(View.GONE);
        mGraduniversity = findViewById(R.id.tvinfouniversity);
        mGraduniversity.setVisibility(View.GONE);
        mGradDegree = findViewById(R.id.tvinfodegree);
        mGradDegree.setVisibility(View.GONE);
        mGradDegreemarks = findViewById(R.id.tvinfodegreemarks);
        mGradDegreemarks.setVisibility(View.GONE);
        mGradpassyr = findViewById(R.id.tvinfograduationpassyr);
        mGradpassyr.setVisibility(View.GONE);
        mGradscore = findViewById(R.id.tvinfograduationscore);
        mGradscore.setVisibility(View.GONE);
        mGradInterestedCourse = findViewById(R.id.tvinfograduationInterestedcoourse);
        mGradInterestedCourse.setVisibility(View.GONE);
        mCaste = findViewById(R.id.tvinfocaste);
        mSubCaste = findViewById(R.id.tvinfosubcaste);
        mGender = findViewById(R.id.tvinfogender);
        mLocation = findViewById(R.id.tvinfoloc);
        mStateofInterest = findViewById(R.id.tvinfostatesofinterest);
        mState = findViewById(R.id.tvinfolocalstate);
        mState.setVisibility(View.GONE);
        mRegistrationTest = findViewById(R.id.tvinforegistrationtest);
        mRegistrationTest.setVisibility(View.GONE);
        mLocalBudget = findViewById(R.id.tvinfobudget);
        mLocalBudget.setVisibility(View.GONE);
        mParentContact = findViewById(R.id.tvinfoparentphno);
        mAltNumber = findViewById(R.id.tvinfoparentaltphno);
        mSector = findViewById(R.id.tvinfosector);
        mSector.setVisibility(View.GONE);
        mIncomeInlakhs = findViewById(R.id.tvinfoincomeinlakhs);
        mAbroadInterestCountry = findViewById(R.id.tvinfocountry);
        mAbroadBudget = findViewById(R.id.tvinfoabroadbudget);
        mMotherOccupation = findViewById(R.id.tvinfomotherocupation);
        mVisittype = findViewById(R.id.tvinfoofficevisittype);
        mMotherjobsector = findViewById(R.id.tvinfomothersector);
        mMotherjobsector.setVisibility(View.GONE);
        mFollowup = findViewById(R.id.tvinfofollowup);
        mAreaname = findViewById(R.id.tvinfoarea);
        mCity = findViewById(R.id.tvinfocity);
        mStdAltcontact = findViewById(R.id.tvinfostdaltcontact);
        mMothername = findViewById(R.id.tvinfomname);
        mRemainderdate = findViewById(R.id.tvinforeminderdate);
        mRemainderdate.setVisibility(View.GONE);
        mRemtext2 = findViewById(R.id.tvinforemindertext);
        mRemtext2.setVisibility(View.GONE);
        mChildren = findViewById(R.id.tvfamilyinfochilds);
        mEntrancetestmarks = findViewById(R.id.tvinforegistrationtestmarks);
        mEntrancetestmarks.setVisibility(View.GONE);
        mMotherIncome = findViewById(R.id.tvinfomotherincome);
        mIntrestedareas = findViewById(R.id.tvinfointrestedareas);
        mRemindersetting = findViewById(R.id.tvinforeminderconfirmation);
        mTvcontactnumber = findViewById(R.id.tvinfocontactnumber);
        mTvSource = findViewById(R.id.tvinfosource);

        //mRemainderdate = findViewById(R.id.tvinforemdate);

        // mEtstdname = (EditText)findViewById(R.id.etinfosname);

      /*  if (studentName != null && !studentName.isEmpty()) {
            mEtstdname.setText(studentName);
        }*/

        mEtstdname = findViewById(R.id.etinfosname);
        mEtcontactnumber = findViewById(R.id.etinfocontactnumber);

        mEtOthers = findViewById(R.id.etinfootherspecify);
      /*  if (mEtOthers.getText().toString()!= null){

            newUpdateInfoResponse.callBackStatus = true;
        } else{
            newUpdateInfoResponse.callBackStatus = false;
        }*/

        mEtOthers.setVisibility(View.GONE);
        mEtPname = findViewById(R.id.etinfopname);

        //mEtPocupation = findViewById(R.id.etinfopoccupation);
        mEtcounsul = findViewById(R.id.etinfoconsulname);
        mEtabroad = findViewById(R.id.etinfoabroad);

        //mEtSpecifydate = findViewById(R.id.etinfotime);

//      Log.e("Reminder.....",  mEtremainderdate.getText().toString());

        mEtvisittext = findViewById(R.id.etinfovisitdate);
        mEtvisittext.setVisibility(View.GONE);
        mEtpphno = findViewById(R.id.etinfoparentphno);
        mEtremarks = findViewById(R.id.etinfoRemarks);
        mEttenthmarks = findViewById(R.id.etinfotenthpercentage);
        mEttenthmarks.setVisibility(View.GONE);
        mEttenthInterestdCourse = findViewById(R.id.etinfointerestcourses);
        mEttenthInterestdCourse.setVisibility(View.GONE);
        mEttenthinterestedbranch = findViewById(R.id.etinfointerestedbranch);
        mEttenthinterestedbranch.setVisibility(View.GONE);
        mEttenthEntranceExam = findViewById(R.id.etinfoentranceexam);
        mEttenthEntranceExam.setVisibility(View.GONE);
        mEttenthEntranceQual = findViewById(R.id.etinfoentrancequal);
        mEttenthEntranceQual.setVisibility(View.GONE);
        mEtintermarks = findViewById(R.id.etinfointerpercentage);
        mEtintermarks.setVisibility(View.GONE);
        mEtinterCourse = findViewById(R.id.etinfointercourseothers);
        mEtinterCourse.setVisibility(View.GONE);
        mEtinterpassyr = findViewById(R.id.etinfointerpassoutyr);
        mEtinterpassyr.setVisibility(View.GONE);
        mEtinterinterestCourseother = findViewById(R.id.etinfospinnerinterestedcourseothers);
        mEtinterinterestCourseother.setVisibility(View.GONE);
        mEtattempeExpectedmarks = findViewById(R.id.etinfoanyentranceexpectedmarks);
        mEtattempeExpectedmarks.setVisibility(View.GONE);
        mEtPreviousmarks = findViewById(R.id.etinfoanyentrancepreviousmarks);
        mEtPreviousmarks.setVisibility(View.GONE);
        mEtmarksobtained = findViewById(R.id.etinfoanyentrancemarksobtained);
        mEtmarksobtained.setVisibility(View.GONE);
        mEtGraduniversity = findViewById(R.id.etinfouniversity);
        mEtGraduniversity.setVisibility(View.GONE);
        mEtGradDegree = findViewById(R.id.etinfodegree);
        mEtGradDegree.setVisibility(View.GONE);
        mEtGradDegreemarks = findViewById(R.id.etinfodegreemarks);
        mEtGradDegreemarks.setVisibility(View.GONE);
        mEtGradpassyr = findViewById(R.id.etinfograduationpassyr);
        mEtGradpassyr.setVisibility(View.GONE);
        mEtGradScore = findViewById(R.id.etinfograduationscore);
        mEtGradScore.setVisibility(View.GONE);
        mEtGradInterestedCourse = findViewById(R.id.etinfograduationInterestedcoourseothers);
        mEtGradInterestedCourse.setVisibility(View.GONE);
        mEtsubcaste = findViewById(R.id.etinfosubcaste);
        mEtLocation = findViewById(R.id.etinfoloc);
        mEtRegistrationTest = findViewById(R.id.etinforegistrationtest);
        mEtRegistrationTest.setVisibility(View.GONE);
        mEtLocalBudget = findViewById(R.id.etinfobudget);
        mEtLocalBudget.setVisibility(View.GONE);
        mEtParentAlt = findViewById(R.id.etinfomotherphno);
        mEtIncomeinLakhs = findViewById(R.id.etinfoincomeinlakhs);
        mEtCountryOthers = findViewById(R.id.etinfocountryother);
        mEtCountryOthers.setVisibility(View.GONE);
        mEtAbroadBudget = findViewById(R.id.etinfoabroadbudget);
        mEtSibilingsfee = findViewById(R.id.etinfosibilingfee);
        mEtSibilingsfee.setVisibility(View.GONE);
        mEtSibilingsDuration = findViewById(R.id.etinfosibilingCourseduration);
        mEtSibilingsDuration.setVisibility(View.GONE);
        mEtAreaname = findViewById(R.id.etinfoarea);
        mEtCity = findViewById(R.id.etinfocity);
        if (city != null && !city.isEmpty()) {
            mEtCity.setText(city);
        }
        mEtStdaltcontact = findViewById(R.id.etinfoaltcontactnumber);
        mEtmothername = findViewById(R.id.etinfomname);
        mEtremainderdate = findViewById(R.id.etinforeminderdate);
        mEtremainderdate.setVisibility(View.GONE);
        mEtremtext = findViewById(R.id.etinforemindertext);
        mEtremtext.setVisibility(View.GONE);
        mEtEntrancetestmarks = findViewById(R.id.etinforegistrationtestmarks);
        mEtEntrancetestmarks.setVisibility(View.GONE);
        mEtfeedback = findViewById(R.id.etinfofeedbacktext);
        mEtvisitmessage = findViewById(R.id.etinfovisitmsg);
        mEtvisitmessage.setVisibility(View.GONE);
        mEtGovtjobtitle = findViewById(R.id.etinfowhichgovt);
        mEtGovtjobtitle.setVisibility(View.GONE);
        mEtmotherincome = findViewById(R.id.etinfomotherincome);
        mEtotherstatus = findViewById(R.id.etinfootherstatus);
        mEtotherstatus.setVisibility(View.GONE);
        mEtfatherbussiness = findViewById(R.id.etinfowhichbussiness);
        mEtfatherbussiness.setVisibility(View.GONE);
        mEtmotherdepartment = findViewById(R.id.etinfomotherwhichgovt);
        mEtmotherdepartment.setVisibility(View.GONE);
        mEtSource = findViewById(R.id.etinfosource);

        chkyes = findViewById(R.id.remyes);


        mBtnsubmit = findViewById(R.id.btninfosubmit);
        mBtnsubmit.setOnClickListener(this);
        mAdd = findViewById(R.id.btninfoadd);
        mBtnCancel = findViewById(R.id.btninfocancel);
        mBtnCheck = findViewById(R.id.btninfocheck);


        mBtnCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                CheckNumber();
            }
        });


        mBtnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(NewformActivity.this);
                builder.setMessage("Are you sure you want to exit?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                NewformActivity.this.finish();
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddField(v);
            }
        });


        mBtnUpload = findViewById(R.id.btninfoupload);
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sharedPref = new SharedPref(getApplicationContext());
                userName = sharedPref.getUserName();
                Uri uri = Uri.parse("https://wo.brandwar.in/Employee/UploadFilesData/" + id + "?sales=" + userName); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        mSpinnerChildren = findViewById(R.id.infospinnerCildren);
        mSpinnerChildren.setOnItemSelectedListener(this);
        final List<String> categoriesChild = new ArrayList<String>();

        categoriesChild.add("0");
        categoriesChild.add("1");
        categoriesChild.add("2");
        categoriesChild.add("3");
        categoriesChild.add("4");
        categoriesChild.add("5");


        ArrayAdapter<String> dataAdapterchildren = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesChild);
        dataAdapterchildren.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerChildren.setAdapter(dataAdapterchildren);
        int spinnerchildrenPosition = dataAdapterchildren.getPosition(children);


        mSpinnerStates = findViewById(R.id.infospinnerstate);
        mSpinnerStates.setVisibility(View.GONE);
        mSpinnerCountry = findViewById(R.id.infospinner);
        //mSpinnerRem = findViewById(R.id.infospinner2);
        mSpinnerInterCourse = findViewById(R.id.infospinnerintercourse);
        mSpinnerInterCourse.setVisibility(View.GONE);
        mSpinnerInterInterestedCourse = findViewById(R.id.infospinnerinterintrestedcourses);
        mSpinnerInterInterestedCourse.setVisibility(View.GONE);
        mSpinnerinterentranceAttempts = findViewById(R.id.infospinnerattempts);
        mSpinnerinterentranceAttempts.setVisibility(View.GONE);
        //mSpinnerGradInterstedCourse = findViewById(R.id.infospinnergraduationinterestedcourse);
        //mSpinnerGradInterstedCourse.setVisibility(View.GONE);


        mSpinnerStates.setOnItemSelectedListener(this);
        final List<String> states = new ArrayList<String>();

        states.add("---select---");
        states.add("Andhra Pradesh");
        states.add("Arunachal Pradesh");
        states.add("Assam");
        states.add("Bihar");
        states.add("Chhattisgarh");
        states.add("Goa");
        states.add("Gujarat");
        states.add("Haryana");
        states.add("Himachal Pradesh");
        states.add("Jammu & Kashmir");
        states.add("Jharkhand");
        states.add("Karnataka");
        states.add("Kerala");
        states.add("Madhya Pradesh");
        states.add("Maharashtra");
        states.add("Manipur");
        states.add("Meghalaya");
        states.add("Mizoram");
        states.add("Nagaland");
        states.add("Odisha");
        states.add("Punjab");
        states.add("Sikkim");
        states.add("Rajasthan");
        states.add("Tamil Nadu");
        states.add("Telangana");
        states.add("Tripura");
        states.add("Uttar Pradesh");
        states.add("Uttarakhand");
        states.add("West Bengal");
        states.add("Andaman and Nicobar Islands");
        states.add("Chandigarh");
        states.add("Dadra and Nagar Haveli");
        states.add("Daman & Diu");
        states.add("Lakshadweep");
        states.add("Puducherry");


        final ArrayAdapter<String> dataAdapterStates = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);
        dataAdapterStates.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerStates.setAdapter(dataAdapterStates);
        final int spinnerstatesPosition = dataAdapterStates.getPosition(Spinnerstates);


        mSpinnerInterCourse.setOnItemSelectedListener(this);
        final List<String> categoriesInterCourse = new ArrayList<String>();

        categoriesInterCourse.add("---select---");
        categoriesInterCourse.add("BiPC");
        categoriesInterCourse.add("MBiPC");
        categoriesInterCourse.add("MEC");
        categoriesInterCourse.add("OTHERS");

        ArrayAdapter<String> dataAdapterInterCourse = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesInterCourse);
        dataAdapterInterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerInterCourse.setAdapter(dataAdapterInterCourse);
        int spinnerintercoursePosition = dataAdapterInterCourse.getPosition(intercourse);


        mSpinnerInterInterestedCourse.setOnItemSelectedListener(this);
        final List<String> categoriesCourse = new ArrayList<String>();

        categoriesCourse.add("---select---");
        categoriesCourse.add("MBBS");
        categoriesCourse.add("BDS");
        categoriesCourse.add("NURSING");
        categoriesCourse.add("B.PHARM");
        categoriesCourse.add("PHARM D");
        categoriesCourse.add("BAMS");
        categoriesCourse.add("BHMS");
        categoriesCourse.add("BUMS");
        categoriesCourse.add("BPT ");
        categoriesCourse.add("B.V.SC.& A.H");
        categoriesCourse.add("BOT ");
        categoriesCourse.add("BASLP ");

        categoriesCourse.add("OTHERS");

        ArrayAdapter<String> dataAdapterCourse = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesCourse);
        dataAdapterCourse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerInterInterestedCourse.setAdapter(dataAdapterCourse);

        final int spinnerinterintrestedcoursePosition = dataAdapterCourse.getPosition(interintrestedcourse);

        mSpinnerinterentranceAttempts.setOnItemSelectedListener(this);
        final List<String> categoriesentranceAttempts = new ArrayList<String>();

        categoriesentranceAttempts.add("0");
        categoriesentranceAttempts.add("1");
        categoriesentranceAttempts.add("2");
        categoriesentranceAttempts.add("3");
        categoriesentranceAttempts.add("4");


        ArrayAdapter<String> dataAdapterentranceAttempts = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categoriesentranceAttempts);
        dataAdapterentranceAttempts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerinterentranceAttempts.setAdapter(dataAdapterentranceAttempts);
        int spinnerentrancePosition = dataAdapterentranceAttempts.getPosition(Attempts);

        // mSpinnerGradInterstedCourse.setOnItemSelectedListener(this);


        mSpinnerCountry.setOnItemSelectedListener(this);

        categories = new ArrayList<String>();

        categories.add("---select---");
        categories.add("CHINA");
        categories.add("RUSSIA");
        categories.add("UKRAINE");
        categories.add("PHILIPPINES");
        categories.add("NEPAL");
        categories.add("KYRGYZSTAN");
        categories.add("USA");
        categories.add("GERMANY");
        categories.add("BANGLADESH");
        categories.add(" SOUTH AMERICA");
        categories.add("NORTH AMERICA");
        categories.add(" CENTRAL AMERICA");
        categories.add("AUSTRALIA");
        categories.add("BARBADOS");
        categories.add("GEORGIA");
        categories.add("OTHERS");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCountry.setAdapter(dataAdapter);

        int spinnerPosition = dataAdapter.getPosition(country);
        Log.e("country", "" + spinnerPosition);

        mEtremainderdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewformActivity.this,
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

        //Log.e("Reminderdate",""+mEtremainderdate);

        mEtvisittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewformActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                c.set(year, monthOfYear, dayOfMonth);
                                String dateString = sdf.format(c.getTime());
                                mEtvisittext.setText(dateString);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            }
        });

        mEtfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewformActivity.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                                c.set(year, monthOfYear, dayOfMonth);
                                String dateString = sdf.format(c.getTime());
                                mEtfeedback.setText(dateString);

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
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);
                mSec = c.get(Calendar.SECOND);
                DatePickerDialog datePickerDialog = new DatePickerDialog(NewformActivity.this,
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

/*
        mRadioGrpreminderset.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.remyes) {
                    remindersetting = reminderset.getText().toString();
                    mRemainderdate.setVisibility(View.VISIBLE);
                    mRemtext2.setVisibility(View.VISIBLE);
                    mEtremainderdate.setVisibility(View.VISIBLE);
                    mEtremtext.setVisibility(View.VISIBLE);
                }
            }
        });*/


        chkyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked()) {
                    mRemainderdate.setVisibility(View.VISIBLE);
                    mRemtext2.setVisibility(View.VISIBLE);
                    mEtremainderdate.setVisibility(View.VISIBLE);
                    mEtremtext.setVisibility(View.VISIBLE);
                } else {
                    mRemainderdate.setVisibility(View.GONE);
                    mRemtext2.setVisibility(View.GONE);
                    mEtremainderdate.setVisibility(View.GONE);
                    mEtremtext.setVisibility(View.GONE);
                }

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
                    Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRadioGrpGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.male) {
                    gender = male.getText().toString();
                } else if (i == R.id.female) {
                    gender = female.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mRadioGrpStateofInterest.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.local) {
                    stateinterest = local.getText().toString();
                    mEtEntrancetestmarks.setVisibility(View.VISIBLE);
                    mEtLocalBudget.setVisibility(View.VISIBLE);
                    mEtRegistrationTest.setVisibility(View.VISIBLE);
                    mSpinnerStates.setVisibility(View.VISIBLE);
                    mEntrancetestmarks.setVisibility(View.VISIBLE);
                    mState.setVisibility(View.VISIBLE);
                    mRegistrationTest.setVisibility(View.VISIBLE);
                    mLocalBudget.setVisibility(View.VISIBLE);

                } else if (i == R.id.Abroad) {
                    stateinterest = abroad.getText().toString();
                    mEtEntrancetestmarks.setVisibility(View.GONE);
                    mEtLocalBudget.setVisibility(View.GONE);
                    mEtRegistrationTest.setVisibility(View.GONE);
                    mSpinnerStates.setVisibility(View.GONE);
                    mEntrancetestmarks.setVisibility(View.GONE);
                    mState.setVisibility(View.GONE);
                    mRegistrationTest.setVisibility(View.GONE);
                    mLocalBudget.setVisibility(View.GONE);


                } else if (i == R.id.statelongterm) {
                    stateinterest = rbstateofintrestlongterm.getText().toString();
                    mEtEntrancetestmarks.setVisibility(View.GONE);
                    mEtLocalBudget.setVisibility(View.GONE);
                    mEtRegistrationTest.setVisibility(View.GONE);
                    mSpinnerStates.setVisibility(View.GONE);
                    mEntrancetestmarks.setVisibility(View.GONE);
                    mState.setVisibility(View.GONE);
                    mRegistrationTest.setVisibility(View.GONE);
                    mLocalBudget.setVisibility(View.GONE);


                } else {
                    Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
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

                    Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mRadiogrp5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.yesrb2) {
                    sibilingsabroad = yes.getText().toString();
                    mEtabroad.setVisibility(View.VISIBLE);
                    mEtSibilingsfee.setVisibility(View.VISIBLE);
                    mEtSibilingsDuration.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.norb2) {

                    sibilingsabroad = no.getText().toString();
                    mEtabroad.setVisibility(View.GONE);
                    mEtSibilingsfee.setVisibility(View.GONE);
                    mEtSibilingsDuration.setVisibility(View.GONE);

                } else {

                    Toast.makeText(getApplicationContext(), "Please select one", Toast.LENGTH_SHORT).show();
                }
            }
        });


        mRadiogrpfollowup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.followupyes) {
                    Followup = "" + true;


                } else if (checkedId == R.id.followupno) {

                    Followup = "" + false;


                }
            }
        });


        mFatherOcupation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.fatherjob) {
                    FatherOcupation = fatherjob.getText().toString();
                    mEtfatherbussiness.setVisibility(View.GONE);
                    mSector.setVisibility(View.VISIBLE);
                    mRadioGrpSector.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.fatherbussiness) {

                    FatherOcupation = bussiness.getText().toString();
                    mEtfatherbussiness.setVisibility(View.VISIBLE);
                    mSector.setVisibility(View.GONE);
                    mRadioGrpSector.setVisibility(View.GONE);


                } else if (checkedId == R.id.ffarmer) {

                    FatherOcupation = farmer.getText().toString();
                    mEtfatherbussiness.setVisibility(View.GONE);
                    mSector.setVisibility(View.GONE);
                    mRadioGrpSector.setVisibility(View.GONE);

                }
            }
        });
        mRadioGrpmotheroccupation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.motherjob) {
                    MotherOcupation = mjob.getText().toString();
                    mMotherjobsector.setVisibility(View.VISIBLE);
                    mRadioGrpmotherjobsector.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.housewife) {

                    MotherOcupation = housewife.getText().toString();
                    mMotherjobsector.setVisibility(View.GONE);
                    mRadioGrpmotherjobsector.setVisibility(View.GONE);
                    mEtmotherdepartment.setVisibility(View.GONE);

                }
            }
        });

        mRadioGrpSector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.infopvt) {
                    JobSector = pvt.getText().toString();
                    mEtGovtjobtitle.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.infogovt) {

                    JobSector = govt.getText().toString();
                    mEtGovtjobtitle.setVisibility(View.VISIBLE);


                }/* else if (checkedId == R.id.infoown) {

                    JobSector = own.getText().toString();

                }*/
            }
        });


        mRadioGrpmotherjobsector.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.motherinfopvt) {
                    motherjobsector = pvt.getText().toString();
                    mEtmotherdepartment.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.motherinfogovt) {

                    motherjobsector = govt.getText().toString();
                    mEtmotherdepartment.setVisibility(View.VISIBLE);


                }
            }
        });


        mRadioGrpApproached.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.parent) {
                    intrestedareas = appparent.getText().toString();


                } else if (checkedId == R.id.consultancy) {

                    intrestedareas = appcounsultancy.getText().toString();


                }
            }
        });


        mRadiogrpCaste.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rboc) {
                    mRadiogrpSubcaste.setVisibility(View.GONE);

                    caste = rboc.getText().toString();


                } else if (checkedId == R.id.rbbc) {
                    mRadiogrpSubcaste.setVisibility(View.VISIBLE);

                    caste = rbbc.getText().toString();
                } else if (checkedId == R.id.rbsc) {
                    mRadiogrpSubcaste.setVisibility(View.GONE);

                    caste = rbsc.getText().toString();
                } else if (checkedId == R.id.rbst) {
                    mRadiogrpSubcaste.setVisibility(View.GONE);

                    caste = rbst.getText().toString();
                } /*else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
        mRadiogrpSubcaste.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.A) {
                    subcaste = a.getText().toString();


                } else if (checkedId == R.id.B) {

                    subcaste = b.getText().toString();
                } else if (checkedId == R.id.c) {

                    subcaste = c.getText().toString();
                } else if (checkedId == R.id.D) {

                    subcaste = d.getText().toString();
                }/* else {

                    Toast.makeText(getActivity(), "Please select one", Toast.LENGTH_SHORT).show();
                }*/
            }
        });

        mRadiogrptenth.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.tenth) {
                    StudentQualification = ssc.getText().toString();
                    mTenthmarks.setVisibility(View.VISIBLE);
                    mTenthintrestedcourse.setVisibility(View.VISIBLE);
                    mTenthIntrestedbranch.setVisibility(View.VISIBLE);
                    mTenthEntranceEzam.setVisibility(View.VISIBLE);
                    mTenthEntraceQualification.setVisibility(View.VISIBLE);
                    mEttenthmarks.setVisibility(View.VISIBLE);
                    mEttenthInterestdCourse.setVisibility(View.VISIBLE);
                    mEttenthinterestedbranch.setVisibility(View.VISIBLE);
                    mEttenthEntranceExam.setVisibility(View.VISIBLE);
                    mEttenthEntranceQual.setVisibility(View.VISIBLE);


                    mIntermarks.setVisibility(View.GONE);
                    mInterCourse.setVisibility(View.GONE);
                    mInterPassyear.setVisibility(View.GONE);
                    mInterInterestedCourse.setVisibility(View.GONE);
                    mInterAttemptedEntrance.setVisibility(View.GONE);
                    mEtintermarks.setVisibility(View.GONE);
                    mEtinterCourse.setVisibility(View.GONE);
                    mEtinterpassyr.setVisibility(View.GONE);
                    mEtinterinterestCourseother.setVisibility(View.GONE);
                    mEtattempeExpectedmarks.setVisibility(View.GONE);
                    mEtPreviousmarks.setVisibility(View.GONE);
                    mEtmarksobtained.setVisibility(View.GONE);
                    mSpinnerInterInterestedCourse.setVisibility(View.GONE);
                    mSpinnerInterCourse.setVisibility(View.GONE);
                    mSpinnerinterentranceAttempts.setVisibility(View.GONE);


                    mGraduniversity.setVisibility(View.GONE);
                    mGradDegree.setVisibility(View.GONE);
                    mGradDegreemarks.setVisibility(View.GONE);
                    mGradpassyr.setVisibility(View.GONE);
                    mGradscore.setVisibility(View.GONE);
                    mGradInterestedCourse.setVisibility(View.GONE);
                    //  mSpinnerGradInterstedCourse.setVisibility(View.GONE);
                    mEtGraduniversity.setVisibility(View.GONE);
                    mEtGradDegree.setVisibility(View.GONE);
                    mEtGradDegreemarks.setVisibility(View.GONE);
                    mEtGradpassyr.setVisibility(View.GONE);
                    mEtGradScore.setVisibility(View.GONE);
                    mEtGradInterestedCourse.setVisibility(View.GONE);


                } else if (checkedId == R.id.Inter) {

                    StudentQualification = inter.getText().toString();
                    mTenthmarks.setVisibility(View.GONE);
                    mTenthintrestedcourse.setVisibility(View.GONE);
                    mTenthIntrestedbranch.setVisibility(View.GONE);
                    mTenthEntranceEzam.setVisibility(View.GONE);
                    mTenthEntraceQualification.setVisibility(View.GONE);
                    mEttenthmarks.setVisibility(View.GONE);
                    mEttenthInterestdCourse.setVisibility(View.GONE);
                    mEttenthinterestedbranch.setVisibility(View.GONE);
                    mEttenthEntranceExam.setVisibility(View.GONE);
                    mEttenthEntranceQual.setVisibility(View.GONE);


                    mIntermarks.setVisibility(View.VISIBLE);
                    mInterCourse.setVisibility(View.VISIBLE);
                    mInterPassyear.setVisibility(View.VISIBLE);
                    mInterInterestedCourse.setVisibility(View.VISIBLE);
                    mInterAttemptedEntrance.setVisibility(View.VISIBLE);
                    mEtintermarks.setVisibility(View.VISIBLE);
                    mEtinterCourse.setVisibility(View.GONE);
                    mEtinterpassyr.setVisibility(View.VISIBLE);
                    mEtinterinterestCourseother.setVisibility(View.GONE);
                    mEtattempeExpectedmarks.setVisibility(View.GONE);
                    mEtPreviousmarks.setVisibility(View.GONE);
                    mEtmarksobtained.setVisibility(View.GONE);
                    mSpinnerInterInterestedCourse.setVisibility(View.VISIBLE);
                    mSpinnerInterCourse.setVisibility(View.VISIBLE);
                    mSpinnerinterentranceAttempts.setVisibility(View.VISIBLE);


                    mGraduniversity.setVisibility(View.GONE);
                    mGradDegree.setVisibility(View.GONE);
                    mGradDegreemarks.setVisibility(View.GONE);
                    mGradpassyr.setVisibility(View.GONE);
                    mGradscore.setVisibility(View.GONE);
                    mGradInterestedCourse.setVisibility(View.GONE);
                    //   mSpinnerGradInterstedCourse.setVisibility(View.GONE);
                    mEtGraduniversity.setVisibility(View.GONE);
                    mEtGradDegree.setVisibility(View.GONE);
                    mEtGradDegreemarks.setVisibility(View.GONE);
                    mEtGradpassyr.setVisibility(View.GONE);
                    mEtGradScore.setVisibility(View.GONE);
                    mEtGradInterestedCourse.setVisibility(View.GONE);


                } else if (checkedId == R.id.graduation) {
                    StudentQualification = graduation.getText().toString();
                    mTenthmarks.setVisibility(View.GONE);
                    mTenthintrestedcourse.setVisibility(View.GONE);
                    mTenthIntrestedbranch.setVisibility(View.GONE);
                    mTenthEntranceEzam.setVisibility(View.GONE);
                    mTenthEntraceQualification.setVisibility(View.GONE);
                    mEttenthmarks.setVisibility(View.GONE);
                    mEttenthInterestdCourse.setVisibility(View.GONE);
                    mEttenthinterestedbranch.setVisibility(View.GONE);
                    mEttenthEntranceExam.setVisibility(View.GONE);
                    mEttenthEntranceQual.setVisibility(View.GONE);

                    mIntermarks.setVisibility(View.GONE);
                    mInterCourse.setVisibility(View.GONE);
                    mInterPassyear.setVisibility(View.GONE);
                    mInterInterestedCourse.setVisibility(View.GONE);
                    mInterAttemptedEntrance.setVisibility(View.GONE);
                    mEtintermarks.setVisibility(View.GONE);
                    mEtinterCourse.setVisibility(View.GONE);
                    mEtinterpassyr.setVisibility(View.GONE);
                    mEtinterinterestCourseother.setVisibility(View.GONE);
                    mEtattempeExpectedmarks.setVisibility(View.GONE);
                    mEtPreviousmarks.setVisibility(View.GONE);
                    mEtmarksobtained.setVisibility(View.GONE);
                    mSpinnerInterInterestedCourse.setVisibility(View.GONE);
                    mSpinnerInterCourse.setVisibility(View.GONE);
                    mSpinnerinterentranceAttempts.setVisibility(View.GONE);


                    mGraduniversity.setVisibility(View.VISIBLE);
                    mGradDegree.setVisibility(View.VISIBLE);
                    mGradDegreemarks.setVisibility(View.VISIBLE);
                    mGradpassyr.setVisibility(View.VISIBLE);
                    mGradscore.setVisibility(View.VISIBLE);
                    mGradInterestedCourse.setVisibility(View.VISIBLE);
                    //mSpinnerGradInterstedCourse.setVisibility(View.VISIBLE);
                    mEtGraduniversity.setVisibility(View.VISIBLE);
                    mEtGradDegree.setVisibility(View.VISIBLE);
                    mEtGradDegreemarks.setVisibility(View.VISIBLE);
                    mEtGradpassyr.setVisibility(View.VISIBLE);
                    mEtGradScore.setVisibility(View.VISIBLE);
                    mEtGradInterestedCourse.setVisibility(View.VISIBLE);


                }

            }
        });


        mRadiogrp6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.yesrb3) {
                    officevisit = yes.getText().toString();
                    mEtvisittext.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.norb3) {

                    officevisit = no.getText().toString();
                    mEtvisittext.setVisibility(View.INVISIBLE);
                }
            }
        });

        mRadiogrpvisittype.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.house) {
                    officevisittype = house.getText().toString();
                    mEtvisitmessage.setVisibility(View.VISIBLE);


                } else if (checkedId == R.id.Officevisit) {

                    officevisittype = rbofficevisit.getText().toString();
                    mEtvisitmessage.setVisibility(View.VISIBLE);


                } /*else if (checkedId == R.id.personal) {

                    officevisittype= personal.getText().toString();
                }*/
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(NewformActivity.this, Manifest.permission.READ_CALL_LOG)) {

                ActivityCompat.requestPermissions(NewformActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);

            } else {
                ActivityCompat.requestPermissions(NewformActivity.this, new String[]{Manifest.permission.READ_CALL_LOG}, 1);

            }
        } else {


            mEtcontactnumber.setText(getCallDetails(context).get(0).getNumber());

            //Toast.makeText(context, "nooo", Toast.LENGTH_SHORT).show();
           /* TextView textView = findViewById(R.id.textincoming);

            textView.setText(getCallDetails(context));*/

           /* incomingcallAdapter = new IncomingcallAdapter(getApplicationContext(),getCallDetails(context));
            mRecycler.setAdapter(incomingcallAdapter);*/

        }


        if (getIntent().getStringExtra("case") != null && !getIntent().getStringExtra("case").isEmpty() &&
                getIntent().getStringExtra("case").equalsIgnoreCase("2")) {
            newTodayCallResponse = (NewAllCallsResponse) getIntent().getSerializableExtra("data");
        }
        Log.e("newTodayCallResponse", "" + newTodayCallResponse);
        if (newTodayCallResponse != null) {
            if (newTodayCallResponse.gender != null && !newTodayCallResponse.gender.isEmpty()) {
                if (newTodayCallResponse.gender.equalsIgnoreCase("male")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }
            }
            if (newTodayCallResponse.country != null && !newTodayCallResponse.country.isEmpty()) {
                mEtLocation.setText(newTodayCallResponse.country);
            }
            if (newTodayCallResponse.areaName != null && !newTodayCallResponse.areaName.isEmpty()) {
                mEtAreaname.setText(newTodayCallResponse.areaName);
            }
            if (newTodayCallResponse.city != null && !newTodayCallResponse.city.isEmpty()) {
                mEtCity.setText(newTodayCallResponse.city);
            }
            if (newTodayCallResponse.alternativecontact != null && !newTodayCallResponse.alternativecontact.isEmpty()) {
                mEtStdaltcontact.setText(newTodayCallResponse.alternativecontact);
            }
            if (newTodayCallResponse.country != null && !newTodayCallResponse.country.isEmpty()) {
                mEtLocation.setText(newTodayCallResponse.country);
            }

            if (newTodayCallResponse.qalification != null && !newTodayCallResponse.qalification.isEmpty()) {
                if (newTodayCallResponse.qalification.equalsIgnoreCase("SSC ")) {
                    ssc.setChecked(true);
                } else if (newTodayCallResponse.qalification.equalsIgnoreCase("Inter")) {
                    inter.setChecked(true);
                } else if (newTodayCallResponse.qalification.equalsIgnoreCase("graduation")) {
                    graduation.setChecked(true);
                }
            }
            if (newTodayCallResponse.sSCmarks != null) {
                //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newTodayCallResponse.sSCmarks)));
                mEttenthmarks.setText("" + newTodayCallResponse.sSCmarks);
            }
            if (newTodayCallResponse.sscfuturecourse != null && !newTodayCallResponse.sscfuturecourse.isEmpty()) {
                mEttenthInterestdCourse.setText(newTodayCallResponse.sscfuturecourse);
            }
            if (newTodayCallResponse.sSCintrestedBranch != null && !newTodayCallResponse.sSCintrestedBranch.isEmpty()) {
                mEttenthinterestedbranch.setText(newTodayCallResponse.sSCintrestedBranch);
            }
            if (newTodayCallResponse.entrenceTestName != null && !newTodayCallResponse.entrenceTestName.isEmpty()) {
                mEttenthEntranceExam.setText(newTodayCallResponse.entrenceTestName);
            }


            if (newTodayCallResponse.interbranch != null && !newTodayCallResponse.interbranch.isEmpty()) {
                mEtinterCourse.setText(newTodayCallResponse.interbranch);
            }

            if (newTodayCallResponse.interbranch != null && !newTodayCallResponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelected(true);
            }

            if (newTodayCallResponse.interPassyear != null) {
                mEtinterpassyr.setText("" + newTodayCallResponse.interPassyear);
            }
            if (newTodayCallResponse.expectedMarks != null) {
                mEtattempeExpectedmarks.setText("" + newTodayCallResponse.expectedMarks);
            }
            if (newTodayCallResponse.previousMarks != null) {
                mEtPreviousmarks.setText("" + newTodayCallResponse.previousMarks);
            }
            if (newTodayCallResponse.marks != null) {
                mEtmarksobtained.setText("" + newTodayCallResponse.marks);
            }
            if (newTodayCallResponse.graduationUniversity != null && !newTodayCallResponse.graduationUniversity.isEmpty()) {
                mEtGraduniversity.setText(newTodayCallResponse.graduationUniversity);
            }

            if (newTodayCallResponse.graduationbranch != null && !newTodayCallResponse.graduationbranch.isEmpty()) {
                mEtGradDegree.setText(newTodayCallResponse.graduationbranch);
            }

            if (newTodayCallResponse.graduationMarks != null) {
                mEtGradDegreemarks.setText("" + newTodayCallResponse.graduationMarks);
            }
            if (newTodayCallResponse.graduationpassyear != null) {
                mEtGradpassyr.setText("" + newTodayCallResponse.graduationpassyear);
            }
            if (newTodayCallResponse.cast != null && !newTodayCallResponse.cast.isEmpty()) {
                if (newTodayCallResponse.cast.equalsIgnoreCase("oc ")) {
                    rboc.setChecked(true);
                } else if (newTodayCallResponse.cast.equalsIgnoreCase("bc")) {
                    rbbc.setChecked(true);
                } else if (newTodayCallResponse.cast.equalsIgnoreCase("sc")) {
                    rbsc.setChecked(true);
                } else if (newTodayCallResponse.cast.equalsIgnoreCase("st")) {
                    rbst.setChecked(true);
                }
            }
            if (newTodayCallResponse.subCast != null && !newTodayCallResponse.subCast.isEmpty()) {
                mEtsubcaste.setText(newTodayCallResponse.subCast);
            }
            if (newTodayCallResponse.stateOfintrest != null && !newTodayCallResponse.stateOfintrest.isEmpty()) {
                if (newTodayCallResponse.stateOfintrest.equalsIgnoreCase("local")) {
                    local.setChecked(true);
                } else if (newTodayCallResponse.stateOfintrest.equalsIgnoreCase("abroad")) {
                    abroad.setChecked(true);
                }
            }

            if (newTodayCallResponse.courseType != null && !newTodayCallResponse.courseType.isEmpty()) {
                if (newTodayCallResponse.courseType.equalsIgnoreCase("Short Term")) {
                    shortterm.setChecked(true);
                } else if (newTodayCallResponse.courseType.equalsIgnoreCase("Long Term")) {
                    longterm.setChecked(true);
                }
            }
            if (newTodayCallResponse.entrenceTestName != null && !newTodayCallResponse.entrenceTestName.isEmpty()) {
                mEtRegistrationTest.setText(newTodayCallResponse.entrenceTestName);
            }
            if (newTodayCallResponse.entrancetestmarks != null) {
                mEtEntrancetestmarks.setText("" + newTodayCallResponse.entrancetestmarks);
            }
            if (newTodayCallResponse.fatherName != null && !newTodayCallResponse.fatherName.isEmpty()) {
                mEtPname.setText(newTodayCallResponse.fatherName);
            }
            if (newTodayCallResponse.matherName != null && !newTodayCallResponse.matherName.isEmpty()) {
                mEtmothername.setText(newTodayCallResponse.matherName);
            }
            if (newTodayCallResponse.fathercontact != null && !newTodayCallResponse.fathercontact.isEmpty()) {
                mEtpphno.setText(newTodayCallResponse.fathercontact);
            }
            if (newTodayCallResponse.mathercon != null && !newTodayCallResponse.mathercon.isEmpty()) {
                mEtParentAlt.setText(newTodayCallResponse.mathercon);
            }
            if (newTodayCallResponse.income != null && !newTodayCallResponse.income.isEmpty()) {
                mEtIncomeinLakhs.setText(newTodayCallResponse.income);
            }

            if (newTodayCallResponse.country != null && !newTodayCallResponse.country.isEmpty()) {
                mEtCountryOthers.setText(newTodayCallResponse.country);
            }
            if (newTodayCallResponse.localBudget != null) {
                mEtLocalBudget.setText("" + newTodayCallResponse.localBudget);
            }
            if (newTodayCallResponse.abroadBudget != null) {
                mEtAbroadBudget.setText("" + newTodayCallResponse.abroadBudget);
            }

            if (newTodayCallResponse.visitType != null && !newTodayCallResponse.visitType.isEmpty()) {
                if (newTodayCallResponse.visitType.equalsIgnoreCase("House visit")) {
                    house.setChecked(true);
                } else if (newTodayCallResponse.visitType.equalsIgnoreCase("Office visit")) {
                    rbofficevisit.setChecked(true);
                } /*else {
                    personal.setChecked(true);
                }*/

            }
            if (newTodayCallResponse.finalFeedBack != null && !newTodayCallResponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newTodayCallResponse.finalFeedBack);
            }
            if (newTodayCallResponse.callbackDate != null && !newTodayCallResponse.callbackDate.isEmpty()) {
                mEtOthers.setText(newTodayCallResponse.callbackDate);
            }
            /*if (newTodayCallResponse. != null && !newTodayCallResponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newTodayCallResponse.finalFeedBack);
            }
*/

            if (newTodayCallResponse.callStatus != null && !newTodayCallResponse.callStatus.isEmpty()) {
                if (newTodayCallResponse.callStatus.equalsIgnoreCase("int")) {
                    rbintersted.setChecked(true);
                } else if (newTodayCallResponse.callStatus.equalsIgnoreCase("notint")) {
                    rbnotinterested.setChecked(true);
                } else if (newTodayCallResponse.callStatus.equalsIgnoreCase("Call Back")) {
                    rbcallback.setChecked(true);
                } else if (newTodayCallResponse.callStatus.equalsIgnoreCase("nr")) {
                    rbnottresponding.setChecked(true);
                } else if (newTodayCallResponse.callStatus.equalsIgnoreCase("so")) {
                    rbswitchoff.setChecked(true);
                } else if (newTodayCallResponse.callStatus.equalsIgnoreCase("oc")) {
                    rboutofcoverage.setChecked(true);
                } else if (newTodayCallResponse.callStatus.equalsIgnoreCase("others")) {
                    rbothers.setChecked(true);
                }

            }

            /*if(newTodayCallResponse.reminderdateTime!=null && !newTodayCallResponse.reminderdateTime.isEmpty()){
                mEtremainderdate.setText(newTodayCallResponse.reminderdateTime);
            }*/
            if (newTodayCallResponse.reminderMessage != null && !newTodayCallResponse.reminderMessage.isEmpty()) {
                mEtremindertext.setText(newTodayCallResponse.reminderMessage);
            }

            if (newTodayCallResponse.visitMsg != null && !newTodayCallResponse.visitMsg.isEmpty()) {
                mEtvisitmessage.setText(newTodayCallResponse.visitMsg);
            }

            if (newTodayCallResponse.canditintrests != null && !newTodayCallResponse.canditintrests.isEmpty()) {
                if (newTodayCallResponse.canditintrests.equalsIgnoreCase("Parent")) {
                    appparent.setChecked(true);
                } else if (newTodayCallResponse.canditintrests.equalsIgnoreCase("Counsultancy")) {
                    appcounsultancy.setChecked(true);
                }
            }
            if (newTodayCallResponse.entranceQualification != null && !newTodayCallResponse.entranceQualification.isEmpty()) {
                mEttenthEntranceQual.setText(newTodayCallResponse.entranceQualification);
            }

            if (newTodayCallResponse.motherOccupationDesc != null && !newTodayCallResponse.motherOccupationDesc.isEmpty()) {
                if (newTodayCallResponse.motherOccupationDesc.equalsIgnoreCase("Job ")) {
                    mjob.setChecked(true);
                } else if (newTodayCallResponse.motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                    housewife.setChecked(true);
                }

            }

            if (newTodayCallResponse.motherJobdesc != null && !newTodayCallResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newTodayCallResponse.motherJobdesc);
            }


            if (newTodayCallResponse.consutancyDescr != null && !newTodayCallResponse.consutancyDescr.isEmpty()) {
                mEtcounsul.setText(newTodayCallResponse.consutancyDescr);
            }
            if (newTodayCallResponse.studyinAbroadDesc != null && !newTodayCallResponse.studyinAbroadDesc.isEmpty()) {
                if (newTodayCallResponse.studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                    yes1.setChecked(true);
                } else if (newTodayCallResponse.studyinAbroadDesc.equalsIgnoreCase("No")) {
                    no1.setChecked(true);
                }

            }
            if (newTodayCallResponse.siblingCountry != null && !newTodayCallResponse.siblingCountry.isEmpty()) {
                mEtabroad.setText(newTodayCallResponse.siblingCountry);
            }
            if (newTodayCallResponse.siblingFee != null && !newTodayCallResponse.siblingFee.isEmpty()) {
                mEtSibilingsfee.setText(newTodayCallResponse.siblingFee);
            }
            if (newTodayCallResponse.siblingCourse != null && !newTodayCallResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newTodayCallResponse.siblingCourse);
            }

            if (newTodayCallResponse.visitDescriptiopn != null && !newTodayCallResponse.visitDescriptiopn.isEmpty()) {
                if (newTodayCallResponse.visitDescriptiopn.equalsIgnoreCase(" yes")) {
                    yesrb3.setChecked(true);
                } else if (newTodayCallResponse.visitDescriptiopn.equalsIgnoreCase("No ")) {
                    norb3.setChecked(true);
                }

            }
            if (newTodayCallResponse.feedBackDescription != null && !newTodayCallResponse.feedBackDescription.isEmpty()) {
                mEtfeedback.setText(newTodayCallResponse.feedBackDescription);
            }

            if (newTodayCallResponse.abroadKnowledgeDesc != null && !newTodayCallResponse.abroadKnowledgeDesc.isEmpty()) {
                if (newTodayCallResponse.abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                    yes.setChecked(true);
                } else if (newTodayCallResponse.abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                    no.setChecked(true);
                }

            }

            if (newTodayCallResponse.fatherJobdescription != null && !newTodayCallResponse.fatherJobdescription.isEmpty()) {
                mEtfatherbussiness.setText(newTodayCallResponse.fatherJobdescription);
            }

            if (newTodayCallResponse.fatherJob != null && !newTodayCallResponse.fatherJob.isEmpty()) {
                mEtGovtjobtitle.setText(newTodayCallResponse.fatherJob);
            }
            if (newTodayCallResponse.visitDate != null && !newTodayCallResponse.visitDate.isEmpty()) {
                mEtvisittext.setText(newTodayCallResponse.visitDate);
            }
            if (newTodayCallResponse.callOtheStatus != null && !newTodayCallResponse.callOtheStatus.isEmpty()) {
                mEtotherstatus.setText(newTodayCallResponse.callOtheStatus);
            }
            if (newTodayCallResponse.motherOccupationSector != null && !newTodayCallResponse.motherOccupationSector.isEmpty()) {
                if (newTodayCallResponse.motherOccupationSector.equalsIgnoreCase("Private ")) {
                    motherpvt.setChecked(true);
                } else if (newTodayCallResponse.motherOccupationSector.equalsIgnoreCase("Govt")) {
                    mothergovt.setChecked(true);
                }
            }
            if (newTodayCallResponse.motherJobdesc != null && !newTodayCallResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newTodayCallResponse.motherJobdesc);
            }
            if (newTodayCallResponse.intrestedCountry != null && !newTodayCallResponse.intrestedCountry.isEmpty()) {

            }
            if (newTodayCallResponse.intrestedCountry != null && !newTodayCallResponse.intrestedCountry.isEmpty()) {
                Log.e("spinnerPosition", "" + spinnerPosition);
                mSpinnerCountry.setSelection(spinnerPosition);
            }

            if (newTodayCallResponse.interbranch != null && !newTodayCallResponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelection(spinnerintercoursePosition);
            }
            if (newTodayCallResponse.interIntrestedCourse != null && !newTodayCallResponse.interIntrestedCourse.isEmpty()) {
                mSpinnerInterInterestedCourse.setSelection(spinnerinterintrestedcoursePosition);
            }
            if (newTodayCallResponse.siblings != null) {
                mSpinnerChildren.setSelection(newTodayCallResponse.siblings);

            }

            if (newTodayCallResponse.entranceTestAttemts != null) {
                mSpinnerinterentranceAttempts.setSelection(newTodayCallResponse.entranceTestAttemts);
            }
            if (newTodayCallResponse.state != null && !newTodayCallResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }

            if (newTodayCallResponse.leadfrom != null && !newTodayCallResponse.leadfrom.isEmpty()) {
                mEtSource.setText(newTodayCallResponse.leadfrom);
            }
           /* if (newTodayCallResponse.state!= null && !newTodayCallResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }*/

            //set all data required
        }

        if (getIntent().getStringExtra("case") != null && !getIntent().getStringExtra("case").isEmpty() &&
                getIntent().getStringExtra("case").equalsIgnoreCase("3")) {
            newInterestedCallResponse = (NewInterestedCallResponse) getIntent().getSerializableExtra("dataa");
        }
        Log.e("newintCallResponse", "" + newInterestedCallResponse);
        if (newInterestedCallResponse != null) {
            if (newInterestedCallResponse.gender != null && !newInterestedCallResponse.gender.isEmpty()) {
                if (newInterestedCallResponse.gender.equalsIgnoreCase("male")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }
            }
            if (newInterestedCallResponse.country != null && !newInterestedCallResponse.country.isEmpty()) {
                mEtLocation.setText(newInterestedCallResponse.country);
            }
            if (newInterestedCallResponse.areaName != null && !newInterestedCallResponse.areaName.isEmpty()) {
                mEtAreaname.setText(newInterestedCallResponse.areaName);
            }

            if (newInterestedCallResponse.entranceQualification != null && !newInterestedCallResponse.entranceQualification.isEmpty()) {
                mEttenthEntranceQual.setText(newInterestedCallResponse.entranceQualification);
            }
            if (newInterestedCallResponse.city != null && !newInterestedCallResponse.city.isEmpty()) {
                mEtCity.setText(newInterestedCallResponse.city);
            }
            if (newInterestedCallResponse.alternativecontact != null && !newInterestedCallResponse.alternativecontact.isEmpty()) {
                mEtStdaltcontact.setText(newInterestedCallResponse.alternativecontact);
            }
            if (newInterestedCallResponse.country != null && !newInterestedCallResponse.country.isEmpty()) {
                mEtLocation.setText(newInterestedCallResponse.country);
            }

            if (newInterestedCallResponse.qalification != null && !newInterestedCallResponse.qalification.isEmpty()) {
                if (newInterestedCallResponse.qalification.equalsIgnoreCase("SSC ")) {
                    ssc.setChecked(true);
                } else if (newInterestedCallResponse.qalification.equalsIgnoreCase("Inter")) {
                    inter.setChecked(true);
                } else if (newInterestedCallResponse.qalification.equalsIgnoreCase("graduation")) {
                    graduation.setChecked(true);
                }
            }
            if (newInterestedCallResponse.sSCmarks != null) {
                //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newInterestedCallResponse.sSCmarks)));
                mEttenthmarks.setText("" + newInterestedCallResponse.sSCmarks);
            }
            if (newInterestedCallResponse.sscfuturecourse != null && !newInterestedCallResponse.sscfuturecourse.isEmpty()) {
                mEttenthInterestdCourse.setText(newInterestedCallResponse.sscfuturecourse);
            }
            if (newInterestedCallResponse.sSCintrestedBranch != null && !newInterestedCallResponse.sSCintrestedBranch.isEmpty()) {
                mEttenthinterestedbranch.setText(newInterestedCallResponse.sSCintrestedBranch);
            }

            if (newInterestedCallResponse.entrenceTestName != null && !newInterestedCallResponse.entrenceTestName.isEmpty()) {
                mEttenthEntranceExam.setText(newInterestedCallResponse.entrenceTestName);
            }

            if (newInterestedCallResponse.fatherJob != null && !newInterestedCallResponse.fatherJob.isEmpty()) {
                mEtGovtjobtitle.setText(newInterestedCallResponse.fatherJob);
            }
            if (newInterestedCallResponse.marks != null) {
                mEtintermarks.setText("" + newInterestedCallResponse.marks);
            }
            if (newInterestedCallResponse.interbranch != null && !newInterestedCallResponse.interbranch.isEmpty()) {
                mEtinterCourse.setText(newInterestedCallResponse.interbranch);
            }
            if (newInterestedCallResponse.interPassyear != null) {
                mEtinterpassyr.setText("" + newInterestedCallResponse.interPassyear);
            }
            if (newInterestedCallResponse.expectedMarks != null) {
                mEtattempeExpectedmarks.setText("" + newInterestedCallResponse.expectedMarks);
            }
            if (newInterestedCallResponse.previousMarks != null) {
                mEtPreviousmarks.setText("" + newInterestedCallResponse.previousMarks);
            }
          /*  if (newInterestedCallResponse.marks != null) {
                mEtmarksobtained.setText("" + newInterestedCallResponse.marks);
            }*/
            if (newInterestedCallResponse.graduationUniversity != null && !newInterestedCallResponse.graduationUniversity.isEmpty()) {
                mEtGraduniversity.setText(newInterestedCallResponse.graduationUniversity);
            }

            if (newInterestedCallResponse.graduationbranch != null && !newInterestedCallResponse.graduationbranch.isEmpty()) {
                mEtGradDegree.setText(newInterestedCallResponse.graduationbranch);
            }

            if (newInterestedCallResponse.graduationMarks != null) {
                mEtGradDegreemarks.setText("" + newInterestedCallResponse.graduationMarks);
            }
            if (newInterestedCallResponse.graduationpassyear != null) {
                mEtGradpassyr.setText("" + newInterestedCallResponse.graduationpassyear);
            }
            if (newInterestedCallResponse.cast != null && !newInterestedCallResponse.cast.isEmpty()) {
                if (newInterestedCallResponse.cast.equalsIgnoreCase("oc ")) {
                    rboc.setChecked(true);
                } else if (newInterestedCallResponse.cast.equalsIgnoreCase("bc")) {
                    rbbc.setChecked(true);
                } else if (newInterestedCallResponse.cast.equalsIgnoreCase("sc")) {
                    rbsc.setChecked(true);
                } else if (newInterestedCallResponse.cast.equalsIgnoreCase("st")) {
                    rbst.setChecked(true);
                }
            }
            if (newInterestedCallResponse.subCast != null && !newInterestedCallResponse.subCast.isEmpty()) {
                mEtsubcaste.setText(newInterestedCallResponse.subCast);
            }
            if (newInterestedCallResponse.stateOfintrest != null && !newInterestedCallResponse.stateOfintrest.isEmpty()) {
                if (newInterestedCallResponse.stateOfintrest.equalsIgnoreCase("Local")) {
                    local.setChecked(true);
                } else if (newInterestedCallResponse.stateOfintrest.equalsIgnoreCase("Abroad")) {
                    abroad.setChecked(true);
                } else if (newInterestedCallResponse.stateOfintrest.equalsIgnoreCase("Long Term")) {
                    rbstateofintrestlongterm.setChecked(true);
                }
            }

            if (newInterestedCallResponse.courseType != null && !newInterestedCallResponse.courseType.isEmpty()) {
                if (newInterestedCallResponse.courseType.equalsIgnoreCase("Short Term ")) {
                    shortterm.setChecked(true);
                } else if (newInterestedCallResponse.courseType.equalsIgnoreCase("Long Term")) {
                    longterm.setChecked(true);
                }
            }
            if (newInterestedCallResponse.entrenceTestName != null && !newInterestedCallResponse.entrenceTestName.isEmpty()) {
                mEtRegistrationTest.setText(newInterestedCallResponse.entrenceTestName);
            }
            if (newInterestedCallResponse.entrancetestmarks != null) {
                mEtEntrancetestmarks.setText("" + newInterestedCallResponse.entrancetestmarks);
            }
            if (newInterestedCallResponse.fatherName != null && !newInterestedCallResponse.fatherName.isEmpty()) {
                mEtPname.setText(newInterestedCallResponse.fatherName);
            }
            if (newInterestedCallResponse.matherName != null && !newInterestedCallResponse.matherName.isEmpty()) {
                mEtmothername.setText(newInterestedCallResponse.matherName);
            }
            if (newInterestedCallResponse.fathercontact != null && !newInterestedCallResponse.fathercontact.isEmpty()) {
                mEtpphno.setText(newInterestedCallResponse.fathercontact);
            }
            if (newInterestedCallResponse.mathercon != null && !newInterestedCallResponse.mathercon.isEmpty()) {
                mEtParentAlt.setText(newInterestedCallResponse.mathercon);
            }
            if (newInterestedCallResponse.income != null && !newInterestedCallResponse.income.isEmpty()) {
                mEtIncomeinLakhs.setText(newInterestedCallResponse.income);
            }

            if (newInterestedCallResponse.country != null && !newInterestedCallResponse.country.isEmpty()) {
                mEtCountryOthers.setText(newInterestedCallResponse.country);
            }
            if (newInterestedCallResponse.localBudget != null) {
                mEtLocalBudget.setText("" + newInterestedCallResponse.localBudget);
            }
            if (newInterestedCallResponse.abroadBudget != null) {
                mEtAbroadBudget.setText("" + newInterestedCallResponse.abroadBudget);
            }

            if (newInterestedCallResponse.visitType != null && !newInterestedCallResponse.visitType.isEmpty()) {
                if (newInterestedCallResponse.visitType.equalsIgnoreCase("House Visit")) {
                    house.setChecked(true);
                } else if (newInterestedCallResponse.visitType.equalsIgnoreCase("Office visit")) {
                    rbofficevisit.setChecked(true);
                } /*else {
                    personal.setChecked(true);
                }*/

            }
            if (newInterestedCallResponse.finalFeedBack != null && !newInterestedCallResponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newInterestedCallResponse.finalFeedBack);
            }

            if (newInterestedCallResponse.callStatus != null && !newInterestedCallResponse.callStatus.isEmpty()) {
                if (newInterestedCallResponse.callStatus.equalsIgnoreCase("int")) {
                    rbintersted.setChecked(true);
                } else if (newInterestedCallResponse.callStatus.equalsIgnoreCase("notint")) {
                    rbnotinterested.setChecked(true);
                } else if (newInterestedCallResponse.callStatus.equalsIgnoreCase("Call Back")) {
                    rbcallback.setChecked(true);
                } else if (newInterestedCallResponse.callStatus.equalsIgnoreCase("nr")) {
                    rbnottresponding.setChecked(true);
                } else if (newInterestedCallResponse.callStatus.equalsIgnoreCase("so")) {
                    rbswitchoff.setChecked(true);
                } else if (newInterestedCallResponse.callStatus.equalsIgnoreCase("oc")) {
                    rboutofcoverage.setChecked(true);
                } else if (newInterestedCallResponse.callStatus.equalsIgnoreCase("others")) {
                    rbothers.setChecked(true);
                }

            }


            if (newInterestedCallResponse.fatherOcupat != null && !newInterestedCallResponse.fatherOcupat.isEmpty()) {
                if (newInterestedCallResponse.fatherOcupat.equalsIgnoreCase("Job ")) {
                    fatherjob.setChecked(true);
                } else if (newInterestedCallResponse.fatherOcupat.equalsIgnoreCase("Bussiness")) {
                    bussiness.setChecked(true);
                } else if (newInterestedCallResponse.fatherOcupat.equalsIgnoreCase("Farmer")) {
                    farmer.setChecked(true);
                }
            }
            if (newInterestedCallResponse.occSector != null && !newInterestedCallResponse.occSector.isEmpty()) {
                if (newInterestedCallResponse.occSector.equalsIgnoreCase("Private ")) {
                    pvt.setChecked(true);
                } else if (newInterestedCallResponse.occSector.equalsIgnoreCase("Govt")) {
                    govt.setChecked(true);
                }
            }

           /* if(newInterestedCallResponse.reminderdateTime!=null && !newInterestedCallResponse.reminderdateTime.isEmpty()){
                mEtremainderdate.setText(newInterestedCallResponse.reminderdateTime);
            }*/
            if (newInterestedCallResponse.reminderMessage != null && !newInterestedCallResponse.reminderMessage.isEmpty()) {
                mEtremindertext.setText(newInterestedCallResponse.reminderMessage);
            }

            if (newInterestedCallResponse.mIncomeinLaks != null) {
                mEtmotherincome.setText("" + newInterestedCallResponse.mIncomeinLaks);
            }

            if (newInterestedCallResponse.fatherJobdescription != null && !newInterestedCallResponse.fatherJobdescription.isEmpty()) {
                mEtfatherbussiness.setText(newInterestedCallResponse.fatherJobdescription);
            }

            if (newInterestedCallResponse.siblingCourse != null && !newInterestedCallResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newInterestedCallResponse.siblingCourse);
            }
            if (newInterestedCallResponse.visitMsg != null && !newInterestedCallResponse.visitMsg.isEmpty()) {
                mEtvisitmessage.setText(newInterestedCallResponse.visitMsg);
            }
            if (newInterestedCallResponse.visitStatus != null) {
                if (newInterestedCallResponse.visitStatus.equals(true)) {
                    yesrb3.setChecked(true);
                } else if (newInterestedCallResponse.visitStatus.equals(false)) {
                    norb3.setChecked(true);
                }
            }

            if (newInterestedCallResponse.canditintrests != null && !newInterestedCallResponse.canditintrests.isEmpty()) {
                if (newInterestedCallResponse.canditintrests.equalsIgnoreCase("Parent")) {
                    appparent.setChecked(true);
                } else if (newInterestedCallResponse.canditintrests.equalsIgnoreCase("Counsultancy")) {
                    appcounsultancy.setChecked(true);
                }
            }


            if (newInterestedCallResponse.motherOccupationDesc != null && !newInterestedCallResponse.motherOccupationDesc.isEmpty()) {
                if (newInterestedCallResponse.motherOccupationDesc.equalsIgnoreCase("Job ")) {
                    mjob.setChecked(true);
                } else if (newInterestedCallResponse.motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                    housewife.setChecked(true);
                }

            }
            if (newInterestedCallResponse.motherJobdesc != null && !newInterestedCallResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newInterestedCallResponse.motherJobdesc);
            }
            if (newInterestedCallResponse.consutancyDescr != null && !newInterestedCallResponse.consutancyDescr.isEmpty()) {
                mEtcounsul.setText(newInterestedCallResponse.consutancyDescr);
            }

            if (newInterestedCallResponse.studyinAbroadDesc != null && !newInterestedCallResponse.studyinAbroadDesc.isEmpty()) {
                if (newInterestedCallResponse.studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                    yes1.setChecked(true);
                } else if (newInterestedCallResponse.studyinAbroadDesc.equalsIgnoreCase("No ")) {
                    no1.setChecked(true);
                }

            }
            if (newInterestedCallResponse.siblingCountry != null && !newInterestedCallResponse.siblingCountry.isEmpty()) {
                mEtabroad.setText(newInterestedCallResponse.siblingCountry);
            }
            if (newInterestedCallResponse.siblingFee != null && !newInterestedCallResponse.siblingFee.isEmpty()) {
                mEtSibilingsfee.setText(newInterestedCallResponse.siblingFee);
            }
            if (newInterestedCallResponse.siblingCourse != null && !newInterestedCallResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newInterestedCallResponse.siblingCourse);
            }
            if (newInterestedCallResponse.visitDescriptiopn != null && !newInterestedCallResponse.visitDescriptiopn.isEmpty()) {
                if (newInterestedCallResponse.visitDescriptiopn.equalsIgnoreCase(" yes")) {
                    yesrb3.setChecked(true);
                } else if (newInterestedCallResponse.visitDescriptiopn.equalsIgnoreCase("No")) {
                    norb3.setChecked(true);
                }

            }

            if (newInterestedCallResponse.feedBackDescription != null && !newInterestedCallResponse.feedBackDescription.isEmpty()) {
                mEtfeedback.setText(newInterestedCallResponse.feedBackDescription);
            }

            if (newInterestedCallResponse.abroadKnowledgeDesc != null && !newInterestedCallResponse.abroadKnowledgeDesc.isEmpty()) {
                if (newInterestedCallResponse.abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                    yes.setChecked(true);
                } else if (newInterestedCallResponse.abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                    no.setChecked(true);
                }

            }
            if (newInterestedCallResponse.callbackDate != null && !newInterestedCallResponse.callbackDate.isEmpty()) {
                mEtOthers.setText(newInterestedCallResponse.callbackDate);
            }
            if (newInterestedCallResponse.visitDate != null && !newInterestedCallResponse.visitDate.isEmpty()) {
                mEtvisittext.setText(newInterestedCallResponse.visitDate);
            }

            if (newInterestedCallResponse.callOtheStatus != null && !newInterestedCallResponse.callOtheStatus.isEmpty()) {
                mEtotherstatus.setText(newInterestedCallResponse.callOtheStatus);
            }

            if (newInterestedCallResponse.motherOccupationSector != null && !newInterestedCallResponse.motherOccupationSector.isEmpty()) {
                if (newInterestedCallResponse.motherOccupationSector.equalsIgnoreCase("Private ")) {
                    motherpvt.setChecked(true);
                } else if (newInterestedCallResponse.motherOccupationSector.equalsIgnoreCase("Govt")) {
                    mothergovt.setChecked(true);
                }
            }
            if (newInterestedCallResponse.motherJobdesc != null && !newInterestedCallResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newInterestedCallResponse.motherJobdesc);
            }
            if (newInterestedCallResponse.intrestedCountry != null && !newInterestedCallResponse.intrestedCountry.isEmpty()) {
                mSpinnerCountry.setSelection(spinnerPosition);
            }

            if (newInterestedCallResponse.interbranch != null && !newInterestedCallResponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelection(spinnerintercoursePosition);
            }
            if (newInterestedCallResponse.interIntrestedCourse != null && !newInterestedCallResponse.interIntrestedCourse.isEmpty()) {
                mSpinnerInterInterestedCourse.setSelection(spinnerinterintrestedcoursePosition);
            }


            if (newInterestedCallResponse.siblings != null) {
                mSpinnerChildren.setSelection(newInterestedCallResponse.siblings);
            }

            if (newInterestedCallResponse.entranceTestAttemts != null) {
                mSpinnerinterentranceAttempts.setSelection(newInterestedCallResponse.entranceTestAttemts);
            }

            if (newInterestedCallResponse.state != null && !newInterestedCallResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }

            if (newInterestedCallResponse.leadfrom != null && !newInterestedCallResponse.leadfrom.isEmpty()) {
                mEtSource.setText(newInterestedCallResponse.leadfrom);
            }
        /*    if (newInterestedCallResponse.state!= null && !newInterestedCallResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }*/
            //set all data required
        }

        if (getIntent().getStringExtra("case") != null && !getIntent().getStringExtra("case").isEmpty() &&
                getIntent().getStringExtra("case").equalsIgnoreCase("4")) {
            newremindercallresponse = (NewRemindersInfoResponse) getIntent().getSerializableExtra("dataaa");
        }
        Log.e("newreminderCallResponse", "" + newremindercallresponse);
        if (newremindercallresponse != null) {
            if (newremindercallresponse.gender != null && !newremindercallresponse.gender.isEmpty()) {
                if (newremindercallresponse.gender.equalsIgnoreCase("male")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }
            }
            if (newremindercallresponse.country != null && !newremindercallresponse.country.isEmpty()) {
                mEtLocation.setText(newremindercallresponse.country);
            }
            if (newremindercallresponse.areaName != null && !newremindercallresponse.areaName.isEmpty()) {
                mEtAreaname.setText(newremindercallresponse.areaName);
            }
            if (newremindercallresponse.city != null && !newremindercallresponse.city.isEmpty()) {
                mEtCity.setText(newremindercallresponse.city);
            }
            if (newremindercallresponse.alternativecontact != null && !newremindercallresponse.alternativecontact.isEmpty()) {
                mEtStdaltcontact.setText(newremindercallresponse.alternativecontact);
            }
            if (newremindercallresponse.country != null && !newremindercallresponse.country.isEmpty()) {
                mEtLocation.setText(newremindercallresponse.country);
            }

            if (newremindercallresponse.qalification != null && !newremindercallresponse.qalification.isEmpty()) {
                if (newremindercallresponse.qalification.equalsIgnoreCase("SSC ")) {
                    ssc.setChecked(true);
                } else if (newremindercallresponse.qalification.equalsIgnoreCase("Inter")) {
                    inter.setChecked(true);
                } else if (newremindercallresponse.qalification.equalsIgnoreCase("graduation")) {
                    graduation.setChecked(true);
                }
            }
            if (newremindercallresponse.sSCmarks != null) {
                //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newremindercallresponse.sSCmarks)));
                mEttenthmarks.setText("" + newremindercallresponse.sSCmarks);
            }

            if (newremindercallresponse.entrenceTestName != null && !newremindercallresponse.entrenceTestName.isEmpty()) {
                mEttenthEntranceExam.setText(newremindercallresponse.entrenceTestName);
            }
            if (newremindercallresponse.sscfuturecourse != null && !newremindercallresponse.sscfuturecourse.isEmpty()) {
                mEttenthInterestdCourse.setText(newremindercallresponse.sscfuturecourse);
            }
            if (newremindercallresponse.sSCintrestedBranch != null && !newremindercallresponse.sSCintrestedBranch.isEmpty()) {
                mEttenthinterestedbranch.setText(newremindercallresponse.sSCintrestedBranch);
            }
            if (newremindercallresponse.marks != null) {
                mEtintermarks.setText("" + newremindercallresponse.marks);
            }
            if (newremindercallresponse.interbranch != null && !newremindercallresponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelected(true);
            }
            if (newremindercallresponse.interbranch != null && !newremindercallresponse.interbranch.isEmpty()) {
                mEtinterCourse.setText(newremindercallresponse.interbranch);
            }
            if (newremindercallresponse.interPassyear != null) {
                mEtinterpassyr.setText("" + newremindercallresponse.interPassyear);
            }
            if (newremindercallresponse.expectedMarks != null) {
                mEtattempeExpectedmarks.setText("" + newremindercallresponse.expectedMarks);
            }
            if (newremindercallresponse.previousMarks != null) {
                mEtPreviousmarks.setText("" + newremindercallresponse.previousMarks);
            }
            /*if (newremindercallresponse.marks != null) {
                mEtmarksobtained.setText("" + newremindercallresponse.marks);
            }*/
            if (newremindercallresponse.graduationUniversity != null && !newremindercallresponse.graduationUniversity.isEmpty()) {
                mEtGraduniversity.setText(newremindercallresponse.graduationUniversity);
            }

            if (newremindercallresponse.graduationbranch != null && !newremindercallresponse.graduationbranch.isEmpty()) {
                mEtGradDegree.setText(newremindercallresponse.graduationbranch);
            }

            if (newremindercallresponse.graduationMarks != null) {
                mEtGradDegreemarks.setText("" + newremindercallresponse.graduationMarks);
            }
            if (newremindercallresponse.graduationpassyear != null) {
                mEtGradpassyr.setText("" + newremindercallresponse.graduationpassyear);
            }
            if (newremindercallresponse.cast != null && !newremindercallresponse.cast.isEmpty()) {
                if (newremindercallresponse.cast.equalsIgnoreCase("oc ")) {
                    rboc.setChecked(true);
                } else if (newremindercallresponse.cast.equalsIgnoreCase("bc")) {
                    rbbc.setChecked(true);
                } else if (newremindercallresponse.cast.equalsIgnoreCase("sc")) {
                    rbsc.setChecked(true);
                } else if (newremindercallresponse.cast.equalsIgnoreCase("St")) {
                    rbst.setChecked(true);
                }
            }
            if (newremindercallresponse.subCast != null && !newremindercallresponse.subCast.isEmpty()) {
                mEtsubcaste.setText(newremindercallresponse.subCast);
            }
            if (newremindercallresponse.stateOfintrest != null && !newremindercallresponse.stateOfintrest.isEmpty()) {
                if (newremindercallresponse.stateOfintrest.equalsIgnoreCase("Local")) {
                    local.setChecked(true);
                } else if (newremindercallresponse.stateOfintrest.equalsIgnoreCase("Abroad")) {
                    abroad.setChecked(true);
                } else if (newremindercallresponse.stateOfintrest.equalsIgnoreCase("Long Term")) {
                    rbstateofintrestlongterm.setChecked(true);
                }
            }
            if (newremindercallresponse.courseType != null && !newremindercallresponse.courseType.isEmpty()) {
                if (newremindercallresponse.courseType.equalsIgnoreCase("Short Term ")) {
                    shortterm.setChecked(true);
                } else if (newremindercallresponse.courseType.equalsIgnoreCase("Long Term")) {
                    longterm.setChecked(true);
                }
            }

            if (newremindercallresponse.fatherJob != null && !newremindercallresponse.fatherJob.isEmpty()) {
                mEtGovtjobtitle.setText(newremindercallresponse.fatherJob);
            }

            if (newremindercallresponse.fatherOcupat != null && !newremindercallresponse.fatherOcupat.isEmpty()) {
                if (newremindercallresponse.fatherOcupat.equalsIgnoreCase("Job ")) {
                    fatherjob.setChecked(true);
                } else if (newremindercallresponse.fatherOcupat.equalsIgnoreCase("Bussiness")) {
                    bussiness.setChecked(true);
                } else if (newremindercallresponse.fatherOcupat.equalsIgnoreCase("Farmer")) {
                    farmer.setChecked(true);
                }
            }
            if (newremindercallresponse.occSector != null && !newremindercallresponse.occSector.isEmpty()) {
                if (newremindercallresponse.occSector.equalsIgnoreCase("Private ")) {
                    pvt.setChecked(true);
                } else if (newremindercallresponse.occSector.equalsIgnoreCase("Govt")) {
                    govt.setChecked(true);
                }
            }
          /*  if (newremindercallresponse. != null && !newremindercallresponse.fathercontact.isEmpty()) {
                if (newremindercallresponse.fatherOcupat.equalsIgnoreCase("Job")) {
                    job.setChecked(true);
                } else if (newremindercallresponse.stateOfintrest.equalsIgnoreCase("Bussiness")){
                    bussiness.setChecked(true);
                }else if (newremindercallresponse.stateOfintrest.equalsIgnoreCase("Farmer")){
                    farmer.setChecked(true);
                }
            }*/


            if (newremindercallresponse.entrenceTestName != null && !newremindercallresponse.entrenceTestName.isEmpty()) {
                mEtRegistrationTest.setText(newremindercallresponse.entrenceTestName);
            }
            if (newremindercallresponse.entrancetestmarks != null) {
                mEtEntrancetestmarks.setText("" + newremindercallresponse.entrancetestmarks);
            }
            if (newremindercallresponse.fatherName != null && !newremindercallresponse.fatherName.isEmpty()) {
                mEtPname.setText(newremindercallresponse.fatherName);
            }
            if (newremindercallresponse.matherName != null && !newremindercallresponse.matherName.isEmpty()) {
                mEtmothername.setText(newremindercallresponse.matherName);
            }
            if (newremindercallresponse.fathercontact != null && !newremindercallresponse.fathercontact.isEmpty()) {
                mEtpphno.setText(newremindercallresponse.fathercontact);
            }
            if (newremindercallresponse.mathercon != null && !newremindercallresponse.mathercon.isEmpty()) {
                mEtParentAlt.setText(newremindercallresponse.mathercon);
            }
            if (newremindercallresponse.income != null && !newremindercallresponse.income.isEmpty()) {
                mEtIncomeinLakhs.setText(newremindercallresponse.income);
            }

            if (newremindercallresponse.country != null && !newremindercallresponse.country.isEmpty()) {
                mEtCountryOthers.setText(newremindercallresponse.country);
            }
            if (newremindercallresponse.localBudget != null) {
                mEtLocalBudget.setText("" + newremindercallresponse.localBudget);
            }
            if (newremindercallresponse.abroadBudget != null) {
                mEtAbroadBudget.setText("" + newremindercallresponse.abroadBudget);
            }

            if (newremindercallresponse.visitType != null && !newremindercallresponse.visitType.isEmpty()) {
                if (newremindercallresponse.visitType.equalsIgnoreCase("House visit")) {
                    house.setChecked(true);
                } else if (newremindercallresponse.visitType.equalsIgnoreCase("Office visit")) {
                    rbofficevisit.setChecked(true);
                } /*else {
                    personal.setChecked(true);
                }*/

            }
            if (newremindercallresponse.finalFeedBack != null && !newremindercallresponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newremindercallresponse.finalFeedBack);
            }

            if (newremindercallresponse.callStatus != null && !newremindercallresponse.callStatus.isEmpty()) {
                if (newremindercallresponse.callStatus.equalsIgnoreCase("int")) {
                    rbintersted.setChecked(true);
                } else if (newremindercallresponse.callStatus.equalsIgnoreCase("notint")) {
                    rbnotinterested.setChecked(true);
                } else if (newremindercallresponse.callStatus.equalsIgnoreCase("Call Back")) {
                    rbcallback.setChecked(true);
                } else if (newremindercallresponse.callStatus.equalsIgnoreCase("nr")) {
                    rbnottresponding.setChecked(true);
                } else if (newremindercallresponse.callStatus.equalsIgnoreCase("so")) {
                    rbswitchoff.setChecked(true);
                } else if (newremindercallresponse.callStatus.equalsIgnoreCase("oc")) {
                    rboutofcoverage.setChecked(true);
                } else if (newremindercallresponse.callStatus.equalsIgnoreCase("others")) {
                    rbothers.setChecked(true);
                }

            }



           /* if (newremindercallresponse.reminderdateTime != null && !newremindercallresponse.reminderdateTime.isEmpty()) {
                mEtremainderdate.setText(newremindercallresponse.reminderdateTime);
            }*/
            if (newremindercallresponse.reminderMessage != null && !newremindercallresponse.reminderMessage.isEmpty()) {
                mEtremtext.setText(newremindercallresponse.reminderMessage);
            }

            if (newremindercallresponse.mIncomeinLaks != null) {
                mEtmotherincome.setText("" + newremindercallresponse.mIncomeinLaks);
            }

            if (newremindercallresponse.fatherJobdescription != null && !newremindercallresponse.fatherJobdescription.isEmpty()) {
                mEtfatherbussiness.setText(newremindercallresponse.fatherJobdescription);
            }

            if (newremindercallresponse.siblingCourse != null && !newremindercallresponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newremindercallresponse.siblingCourse);
            }

            if (newremindercallresponse.visitMsg != null && !newremindercallresponse.visitMsg.isEmpty()) {
                mEtvisitmessage.setText(newremindercallresponse.visitMsg);
            }
            if (newremindercallresponse.visitStatus != null) {
                if (newremindercallresponse.visitStatus.equals(true)) {
                    yesrb3.setChecked(true);
                } else if (newremindercallresponse.visitStatus.equals(false)) {
                    norb3.setChecked(true);
                }
            }

            if (newremindercallresponse.canditintrests != null && !newremindercallresponse.canditintrests.isEmpty()) {
                if (newremindercallresponse.canditintrests.equalsIgnoreCase("Parent")) {
                    appparent.setChecked(true);
                } else if (newremindercallresponse.canditintrests.equalsIgnoreCase("Counsultancy")) {
                    appcounsultancy.setChecked(true);
                }
            }
            if (newremindercallresponse.entranceQualification != null && !newremindercallresponse.entranceQualification.isEmpty()) {
                mEttenthEntranceQual.setText(newremindercallresponse.entranceQualification);
            }

            if (newremindercallresponse.motherOccupationDesc != null && !newremindercallresponse.motherOccupationDesc.isEmpty()) {
                if (newremindercallresponse.motherOccupationDesc.equalsIgnoreCase("Job ")) {
                    mjob.setChecked(true);
                } else if (newremindercallresponse.motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                    housewife.setChecked(true);
                }

            }
            if (newremindercallresponse.motherJobdesc != null && !newremindercallresponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newremindercallresponse.motherJobdesc);
            }

            if (newremindercallresponse.consutancyDescr != null && !newremindercallresponse.consutancyDescr.isEmpty()) {
                mEtcounsul.setText(newremindercallresponse.consutancyDescr);
            }


            if (newremindercallresponse.studyinAbroadDesc != null && !newremindercallresponse.studyinAbroadDesc.isEmpty()) {
                if (newremindercallresponse.studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                    yes1.setChecked(true);
                } else if (newremindercallresponse.studyinAbroadDesc.equalsIgnoreCase("No ")) {
                    no1.setChecked(true);
                }

            }

            if (newremindercallresponse.siblingCountry != null && !newremindercallresponse.siblingCountry.isEmpty()) {
                mEtabroad.setText(newremindercallresponse.siblingCountry);
            }
            if (newremindercallresponse.siblingFee != null && !newremindercallresponse.siblingFee.isEmpty()) {
                mEtSibilingsfee.setText(newremindercallresponse.siblingFee);
            }
            if (newremindercallresponse.siblingCourse != null && !newremindercallresponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newremindercallresponse.siblingCourse);
            }

            if (newremindercallresponse.visitDescriptiopn != null && !newremindercallresponse.visitDescriptiopn.isEmpty()) {
                if (newremindercallresponse.visitDescriptiopn.equalsIgnoreCase(" yes")) {
                    yesrb3.setChecked(true);
                } else if (newremindercallresponse.visitDescriptiopn.equalsIgnoreCase("No ")) {
                    norb3.setChecked(true);
                }

            }
            if (newremindercallresponse.feedBackDescription != null && !newremindercallresponse.feedBackDescription.isEmpty()) {
                mEtfeedback.setText(newremindercallresponse.feedBackDescription);
            }

            if (newremindercallresponse.abroadKnowledgeDesc != null && !newremindercallresponse.abroadKnowledgeDesc.isEmpty()) {
                if (newremindercallresponse.abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                    yes.setChecked(true);
                } else if (newremindercallresponse.abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                    no.setChecked(true);
                }

            }
            if (newremindercallresponse.callbackDate != null && !newremindercallresponse.callbackDate.isEmpty()) {
                mEtOthers.setText(newremindercallresponse.callbackDate);
            }
            if (newremindercallresponse.visitDate != null && !newremindercallresponse.visitDate.isEmpty()) {
                mEtvisittext.setText(newremindercallresponse.visitDate);
            }
            if (newremindercallresponse.callOtheStatus != null && !newremindercallresponse.callOtheStatus.isEmpty()) {
                mEtotherstatus.setText(newremindercallresponse.callOtheStatus);
            }

            if (newremindercallresponse.motherOccupationSector != null && !newremindercallresponse.motherOccupationSector.isEmpty()) {
                if (newremindercallresponse.motherOccupationSector.equalsIgnoreCase("Private ")) {
                    motherpvt.setChecked(true);
                } else if (newremindercallresponse.motherOccupationSector.equalsIgnoreCase("Govt")) {
                    mothergovt.setChecked(true);
                }
            }
            if (newremindercallresponse.motherJobdesc != null && !newremindercallresponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newremindercallresponse.motherJobdesc);
            }

            if (newremindercallresponse.intrestedCountry != null && !newremindercallresponse.intrestedCountry.isEmpty()) {
                mSpinnerCountry.setSelection(spinnerPosition);
            }


            if (newremindercallresponse.interbranch != null && !newremindercallresponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelection(spinnerintercoursePosition);
            }
            if (newremindercallresponse.interIntrestedCourse != null && !newremindercallresponse.interIntrestedCourse.isEmpty()) {
                mSpinnerInterInterestedCourse.setSelection(spinnerinterintrestedcoursePosition);
            }


            if (newremindercallresponse.siblings != null) {
                mSpinnerChildren.setSelection(newremindercallresponse.siblings);

            }

            if (newremindercallresponse.entranceTestAttemts != null) {
                mSpinnerinterentranceAttempts.setSelection(newremindercallresponse.entranceTestAttemts);
            }

            if (newremindercallresponse.state != null && !newremindercallresponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }
            if (newremindercallresponse.leadfrom != null && !newremindercallresponse.leadfrom.isEmpty()) {
                mEtSource.setText(newremindercallresponse.leadfrom);
            }
           /* if (newremindercallresponse.state!= null && !newremindercallresponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }*/

            //set all data required
        }
        if (getIntent().getStringExtra("case") != null && !getIntent().getStringExtra("case").isEmpty() &&
                getIntent().getStringExtra("case").equalsIgnoreCase("5")) {
            newCallBacksResponse = (NewCallBacksResponse) getIntent().getSerializableExtra("ddata");
        }
        Log.e("newCallBacksResponse", "" + newCallBacksResponse);
        if (newCallBacksResponse != null) {
            if (newCallBacksResponse.gender != null && !newCallBacksResponse.gender.isEmpty()) {
                if (newCallBacksResponse.gender.equalsIgnoreCase("male")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }
            }
            if (newCallBacksResponse.country != null && !newCallBacksResponse.country.isEmpty()) {
                mEtLocation.setText(newCallBacksResponse.country);
            }
            if (newCallBacksResponse.areaName != null && !newCallBacksResponse.areaName.isEmpty()) {
                mEtAreaname.setText(newCallBacksResponse.areaName);
            }
            if (newCallBacksResponse.city != null && !newCallBacksResponse.city.isEmpty()) {
                mEtCity.setText(newCallBacksResponse.city);
            }
            if (newCallBacksResponse.alternativecontact != null && !newCallBacksResponse.alternativecontact.isEmpty()) {
                mEtStdaltcontact.setText(newCallBacksResponse.alternativecontact);
            }

            if (newCallBacksResponse.fatherJob != null && !newCallBacksResponse.fatherJob.isEmpty()) {
                mEtGovtjobtitle.setText(newCallBacksResponse.fatherJob);
            }

            if (newCallBacksResponse.qalification != null && !newCallBacksResponse.qalification.isEmpty()) {
                if (newCallBacksResponse.qalification.equalsIgnoreCase("SSC ")) {
                    ssc.setChecked(true);
                } else if (newCallBacksResponse.qalification.equalsIgnoreCase("Inter")) {
                    inter.setChecked(true);
                } else if (newCallBacksResponse.qalification.equalsIgnoreCase("graduation")) {
                    graduation.setChecked(true);
                }
            }
            if (newCallBacksResponse.sSCmarks != null) {
                //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newCallBacksResponse.sSCmarks)));
                mEttenthmarks.setText("" + newCallBacksResponse.sSCmarks);
            }
            if (newCallBacksResponse.sscfuturecourse != null && !newCallBacksResponse.sscfuturecourse.isEmpty()) {
                mEttenthInterestdCourse.setText(newCallBacksResponse.sscfuturecourse);
            }
            if (newCallBacksResponse.sSCintrestedBranch != null && !newCallBacksResponse.sSCintrestedBranch.isEmpty()) {
                mEttenthinterestedbranch.setText(newCallBacksResponse.sSCintrestedBranch);
            }

            if (newCallBacksResponse.entrenceTestName != null && !newCallBacksResponse.entrenceTestName.isEmpty()) {
                mEttenthEntranceExam.setText(newCallBacksResponse.entrenceTestName);
            }
            if (newCallBacksResponse.marks != null) {
                mEtintermarks.setText("" + newCallBacksResponse.expectedMarks);
            }
            if (newCallBacksResponse.interbranch != null && !newCallBacksResponse.interbranch.isEmpty()) {
                mEtinterCourse.setText(newCallBacksResponse.interbranch);
            }
            if (newCallBacksResponse.interPassyear != null) {
                mEtinterpassyr.setText("" + newCallBacksResponse.interPassyear);
            }
            if (newCallBacksResponse.expectedMarks != null) {
                mEtattempeExpectedmarks.setText("" + newCallBacksResponse.expectedMarks);
            }
            if (newCallBacksResponse.previousMarks != null) {
                mEtPreviousmarks.setText("" + newCallBacksResponse.previousMarks);
            }
          /*  if (newCallBacksResponse.marks != null) {
                mEtmarksobtained.setText("" + newCallBacksResponse.marks);
            }*/
            if (newCallBacksResponse.graduationUniversity != null && !newCallBacksResponse.graduationUniversity.isEmpty()) {
                mEtGraduniversity.setText(newCallBacksResponse.graduationUniversity);
            }

            if (newCallBacksResponse.graduationbranch != null && !newCallBacksResponse.graduationbranch.isEmpty()) {
                mEtGradDegree.setText(newCallBacksResponse.graduationbranch);
            }

            if (newCallBacksResponse.graduationMarks != null) {
                mEtGradDegreemarks.setText("" + newCallBacksResponse.graduationMarks);
            }
            if (newCallBacksResponse.graduationpassyear != null) {
                mEtGradpassyr.setText("" + newCallBacksResponse.graduationpassyear);
            }
            if (newCallBacksResponse.cast != null && !newCallBacksResponse.cast.isEmpty()) {
                if (newCallBacksResponse.cast.equalsIgnoreCase("oc ")) {
                    rboc.setChecked(true);
                } else if (newCallBacksResponse.cast.equalsIgnoreCase("bc")) {
                    rbbc.setChecked(true);
                } else if (newCallBacksResponse.cast.equalsIgnoreCase("sc")) {
                    rbsc.setChecked(true);
                } else if (newCallBacksResponse.cast.equalsIgnoreCase("st")) {
                    rbst.setChecked(true);
                }
            }
            if (newCallBacksResponse.subCast != null && !newCallBacksResponse.subCast.isEmpty()) {
                mEtsubcaste.setText(newCallBacksResponse.subCast);
            }
            if (newCallBacksResponse.stateOfintrest != null && !newCallBacksResponse.stateOfintrest.isEmpty()) {
                if (newCallBacksResponse.stateOfintrest.equalsIgnoreCase("Local")) {
                    local.setChecked(true);
                } else if (newCallBacksResponse.stateOfintrest.equalsIgnoreCase("Abroad")) {
                    abroad.setChecked(true);
                } else if (newCallBacksResponse.stateOfintrest.equalsIgnoreCase("Long Term")) {
                    rbstateofintrestlongterm.setChecked(true);
                }
            }

            if (newCallBacksResponse.courseType != null && !newCallBacksResponse.courseType.isEmpty()) {
                if (newCallBacksResponse.courseType.equalsIgnoreCase("Short Term ")) {
                    shortterm.setChecked(true);
                } else if (newCallBacksResponse.courseType.equalsIgnoreCase("Long Term")) {
                    longterm.setChecked(true);
                }
            }

            if (newCallBacksResponse.entrenceTestName != null && !newCallBacksResponse.entrenceTestName.isEmpty()) {
                mEtRegistrationTest.setText(newCallBacksResponse.entrenceTestName);
            }
            if (newCallBacksResponse.entrancetestmarks != null) {
                mEtEntrancetestmarks.setText("" + newCallBacksResponse.entrancetestmarks);
            }
            if (newCallBacksResponse.fatherName != null && !newCallBacksResponse.fatherName.isEmpty()) {
                mEtPname.setText(newCallBacksResponse.fatherName);
            }
            if (newCallBacksResponse.matherName != null && !newCallBacksResponse.matherName.isEmpty()) {
                mEtmothername.setText(newCallBacksResponse.matherName);
            }
            if (newCallBacksResponse.fathercontact != null && !newCallBacksResponse.fathercontact.isEmpty()) {
                mEtpphno.setText(newCallBacksResponse.fathercontact);
            }
            if (newCallBacksResponse.mathercon != null && !newCallBacksResponse.mathercon.isEmpty()) {
                mEtParentAlt.setText(newCallBacksResponse.mathercon);
            }
            if (newCallBacksResponse.income != null && !newCallBacksResponse.income.isEmpty()) {
                mEtIncomeinLakhs.setText(newCallBacksResponse.income);
            }

            if (newCallBacksResponse.country != null && !newCallBacksResponse.country.isEmpty()) {
                mEtCountryOthers.setText(newCallBacksResponse.country);
            }
            if (newCallBacksResponse.localBudget != null) {
                mEtLocalBudget.setText("" + newCallBacksResponse.localBudget);
            }
            if (newCallBacksResponse.abroadBudget != null) {
                mEtAbroadBudget.setText("" + newCallBacksResponse.abroadBudget);
            }

            if (newCallBacksResponse.visitType != null && !newCallBacksResponse.visitType.isEmpty()) {
                if (newCallBacksResponse.visitType.equalsIgnoreCase("House visit")) {
                    house.setChecked(true);
                } else if (newCallBacksResponse.visitType.equalsIgnoreCase("Office visit")) {
                    rbofficevisit.setChecked(true);
                }/* else {
                    personal.setChecked(true);
                }*/

            }
            if (newCallBacksResponse.finalFeedBack != null && !newCallBacksResponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newCallBacksResponse.finalFeedBack);
            }

            if (newCallBacksResponse.callStatus != null && !newCallBacksResponse.callStatus.isEmpty()) {
                if (newCallBacksResponse.callStatus.equalsIgnoreCase("int")) {
                    rbintersted.setChecked(true);
                } else if (newCallBacksResponse.callStatus.equalsIgnoreCase("notint")) {
                    rbnotinterested.setChecked(true);
                } else if (newCallBacksResponse.callStatus.equalsIgnoreCase("Call Back")) {
                    rbcallback.setChecked(true);
                    mEtOthers.setVisibility(View.VISIBLE);
                } else if (newCallBacksResponse.callStatus.equalsIgnoreCase("nr")) {
                    rbnottresponding.setChecked(true);
                } else if (newCallBacksResponse.callStatus.equalsIgnoreCase("so")) {
                    rbswitchoff.setChecked(true);
                } else if (newCallBacksResponse.callStatus.equalsIgnoreCase("oc")) {
                    rboutofcoverage.setChecked(true);
                } else if (newCallBacksResponse.callStatus.equalsIgnoreCase("others")) {
                    rbothers.setChecked(true);
                }

            }

            if (newCallBacksResponse.fatherOcupat != null && !newCallBacksResponse.fatherOcupat.isEmpty()) {
                if (newCallBacksResponse.fatherOcupat.equalsIgnoreCase("Job ")) {
                    fatherjob.setChecked(true);
                } else if (newCallBacksResponse.fatherOcupat.equalsIgnoreCase("Bussiness")) {
                    bussiness.setChecked(true);
                } else if (newCallBacksResponse.fatherOcupat.equalsIgnoreCase("Farmer")) {
                    farmer.setChecked(true);
                }
            }
            if (newCallBacksResponse.occSector != null && !newCallBacksResponse.occSector.isEmpty()) {
                if (newCallBacksResponse.occSector.equalsIgnoreCase("Private ")) {
                    pvt.setChecked(true);
                } else if (newCallBacksResponse.occSector.equalsIgnoreCase("Govt")) {
                    govt.setChecked(true);
                }
            }
/*
            if(newCallBacksResponse.reminderdateTime!=null && !newCallBacksResponse.reminderdateTime.isEmpty()){
                mEtremainderdate.setText(newCallBacksResponse.reminderdateTime);
            }*/
            if (newCallBacksResponse.reminderMessage != null && !newCallBacksResponse.reminderMessage.isEmpty()) {
                mEtremtext.setText(newCallBacksResponse.reminderMessage);
            }

            if (newCallBacksResponse.mIncomeinLaks != null) {
                mEtmotherincome.setText("" + newCallBacksResponse.mIncomeinLaks);
            }


            if (newCallBacksResponse.fatherJobdescription != null && !newCallBacksResponse.fatherJobdescription.isEmpty()) {
                mEtfatherbussiness.setText(newCallBacksResponse.fatherJobdescription);
            }

            if (newCallBacksResponse.siblingCourse != null && !newCallBacksResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newCallBacksResponse.siblingCourse);
            }

            if (newCallBacksResponse.visitMsg != null && !newCallBacksResponse.visitMsg.isEmpty()) {
                mEtvisitmessage.setText(newCallBacksResponse.visitMsg);
            }

            if (newCallBacksResponse.visitStatus != null) {
                if (newCallBacksResponse.visitStatus.equals(true)) {
                    yesrb3.setChecked(true);
                } else if (newCallBacksResponse.visitStatus.equals(false)) {
                    norb3.setChecked(true);
                }
            }

            if (newCallBacksResponse.canditintrests != null && !newCallBacksResponse.canditintrests.isEmpty()) {
                if (newCallBacksResponse.canditintrests.equalsIgnoreCase("Parent")) {
                    appparent.setChecked(true);
                } else if (newCallBacksResponse.canditintrests.equalsIgnoreCase("Counsultancy")) {
                    appcounsultancy.setChecked(true);
                }
            }

            if (newCallBacksResponse.entranceQualification != null && !newCallBacksResponse.entranceQualification.isEmpty()) {
                mEttenthEntranceQual.setText(newCallBacksResponse.entranceQualification);
            }
            if (newCallBacksResponse.motherOccupationDesc != null && !newCallBacksResponse.motherOccupationDesc.isEmpty()) {
                if (newCallBacksResponse.motherOccupationDesc.equalsIgnoreCase("Job ")) {
                    mjob.setChecked(true);
                } else if (newCallBacksResponse.motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                    housewife.setChecked(true);
                }

            }

            if (newCallBacksResponse.motherJobdesc != null && !newCallBacksResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newCallBacksResponse.motherJobdesc);
            }

            if (newCallBacksResponse.consutancyDescr != null && !newCallBacksResponse.consutancyDescr.isEmpty()) {
                mEtcounsul.setText(newCallBacksResponse.consutancyDescr);
            }

            if (newCallBacksResponse.studyinAbroadDesc != null && !newCallBacksResponse.studyinAbroadDesc.isEmpty()) {
                if (newCallBacksResponse.studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                    yes1.setChecked(true);
                } else if (newCallBacksResponse.studyinAbroadDesc.equalsIgnoreCase("No ")) {
                    no1.setChecked(true);
                }

            }

            if (newCallBacksResponse.siblingCountry != null && !newCallBacksResponse.siblingCountry.isEmpty()) {
                mEtabroad.setText(newCallBacksResponse.siblingCountry);
            }
            if (newCallBacksResponse.siblingFee != null && !newCallBacksResponse.siblingFee.isEmpty()) {
                mEtSibilingsfee.setText(newCallBacksResponse.siblingFee);
            }
            if (newCallBacksResponse.siblingCourse != null && !newCallBacksResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newCallBacksResponse.siblingCourse);
            }

            if (newCallBacksResponse.visitDescriptiopn != null && !newCallBacksResponse.visitDescriptiopn.isEmpty()) {
                if (newCallBacksResponse.visitDescriptiopn.equalsIgnoreCase(" yes")) {
                    yesrb3.setChecked(true);
                } else if (newCallBacksResponse.visitDescriptiopn.equalsIgnoreCase("No")) {
                    norb3.setChecked(true);
                }

            }

            if (newCallBacksResponse.feedBackDescription != null && !newCallBacksResponse.feedBackDescription.isEmpty()) {
                mEtfeedback.setText(newCallBacksResponse.feedBackDescription);
            }
            if (newCallBacksResponse.abroadKnowledgeDesc != null && !newCallBacksResponse.abroadKnowledgeDesc.isEmpty()) {
                if (newCallBacksResponse.abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                    yes.setChecked(true);
                } else if (newCallBacksResponse.abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                    no.setChecked(true);
                }

            }
            if (newCallBacksResponse.callbackDate != null && !newCallBacksResponse.callbackDate.isEmpty()) {
                mEtOthers.setText(newCallBacksResponse.callbackDate);
            }
            if (newCallBacksResponse.visitDate != null && !newCallBacksResponse.visitDate.isEmpty()) {
                mEtvisittext.setText(newCallBacksResponse.visitDate);
            }
            if (newCallBacksResponse.callOtheStatus != null && !newCallBacksResponse.callOtheStatus.isEmpty()) {
                mEtotherstatus.setText(newCallBacksResponse.callOtheStatus);
            }
            if (newCallBacksResponse.motherOccupationSector != null && !newCallBacksResponse.motherOccupationSector.isEmpty()) {
                if (newCallBacksResponse.motherOccupationSector.equalsIgnoreCase("Private ")) {
                    motherpvt.setChecked(true);
                } else if (newCallBacksResponse.motherOccupationSector.equalsIgnoreCase("Govt")) {
                    mothergovt.setChecked(true);
                }
            }
            if (newCallBacksResponse.motherJobdesc != null && !newCallBacksResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newCallBacksResponse.motherJobdesc);
            }

            if (newCallBacksResponse.intrestedCountry != null && !newCallBacksResponse.intrestedCountry.isEmpty()) {
                mSpinnerCountry.setSelection(spinnerPosition);
            }

            if (newCallBacksResponse.interbranch != null && !newCallBacksResponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelection(spinnerintercoursePosition);
            }
            if (newCallBacksResponse.interIntrestedCourse != null && !newCallBacksResponse.interIntrestedCourse.isEmpty()) {
                mSpinnerInterInterestedCourse.setSelection(spinnerinterintrestedcoursePosition);
            }


            if (newCallBacksResponse.siblings != null) {
                mSpinnerChildren.setSelection(newCallBacksResponse.siblings);
            }

            if (newCallBacksResponse.entranceTestAttemts != null) {
                mSpinnerinterentranceAttempts.setSelection(newCallBacksResponse.entranceTestAttemts);
            }

            if (newCallBacksResponse.state != null && !newCallBacksResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }

            if (newCallBacksResponse.leadfrom != null && !newCallBacksResponse.leadfrom.isEmpty()) {
                mEtSource.setText(newCallBacksResponse.leadfrom);
            }
           /* if (newCallBacksResponse.state!= null && !newCallBacksResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }*/
            //set all data required
        }


        if (getIntent().getStringExtra("case") != null && !getIntent().getStringExtra("case").isEmpty() &&
                getIntent().getStringExtra("case").equalsIgnoreCase("7")) {
            newPendingRemindersResponse = (NewPendingRemindersResponse) getIntent().getSerializableExtra("prdata");
        }
        Log.e("newpendingresponse", "" + newPendingRemindersResponse);
        if (newPendingRemindersResponse != null) {
            if (newPendingRemindersResponse.gender != null && !newPendingRemindersResponse.gender.isEmpty()) {
                if (newPendingRemindersResponse.gender.equalsIgnoreCase("male")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }
            }
            if (newPendingRemindersResponse.country != null && !newPendingRemindersResponse.country.isEmpty()) {
                mEtLocation.setText(newPendingRemindersResponse.country);
            }
            if (newPendingRemindersResponse.areaName != null && !newPendingRemindersResponse.areaName.isEmpty()) {
                mEtAreaname.setText(newPendingRemindersResponse.areaName);
            }
            if (newPendingRemindersResponse.city != null && !newPendingRemindersResponse.city.isEmpty()) {
                mEtCity.setText(newPendingRemindersResponse.city);
            }
            if (newPendingRemindersResponse.alternativecontact != null && !newPendingRemindersResponse.alternativecontact.isEmpty()) {
                mEtStdaltcontact.setText(newPendingRemindersResponse.alternativecontact);
            }
            if (newPendingRemindersResponse.country != null && !newPendingRemindersResponse.country.isEmpty()) {
                mEtLocation.setText(newPendingRemindersResponse.country);
            }

            if (newPendingRemindersResponse.qalification != null && !newPendingRemindersResponse.qalification.isEmpty()) {
                if (newPendingRemindersResponse.qalification.equalsIgnoreCase("SSC ")) {
                    ssc.setChecked(true);
                } else if (newPendingRemindersResponse.qalification.equalsIgnoreCase("Inter")) {
                    inter.setChecked(true);
                } else if (newPendingRemindersResponse.qalification.equalsIgnoreCase("graduation")) {
                    graduation.setChecked(true);
                }
            }
            if (newPendingRemindersResponse.sSCmarks != null) {
                //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newPendingRemindersResponse.sSCmarks)));
                mEttenthmarks.setText("" + newPendingRemindersResponse.sSCmarks);
            }
            if (newPendingRemindersResponse.sscfuturecourse != null && !newPendingRemindersResponse.sscfuturecourse.isEmpty()) {
                mEttenthInterestdCourse.setText(newPendingRemindersResponse.sscfuturecourse);
            }
            if (newPendingRemindersResponse.sSCintrestedBranch != null && !newPendingRemindersResponse.sSCintrestedBranch.isEmpty()) {
                mEttenthinterestedbranch.setText(newPendingRemindersResponse.sSCintrestedBranch);
            }

            if (newPendingRemindersResponse.marks != null) {
                mEtintermarks.setText("" + newPendingRemindersResponse.marks);
            }

            if (newPendingRemindersResponse.entrenceTestName != null && !newPendingRemindersResponse.entrenceTestName.isEmpty()) {
                mEttenthEntranceExam.setText(newPendingRemindersResponse.entrenceTestName);
            }
            if (newPendingRemindersResponse.interbranch != null && !newPendingRemindersResponse.interbranch.isEmpty()) {
                mEtinterCourse.setText(newPendingRemindersResponse.interbranch);
            }
            if (newPendingRemindersResponse.interPassyear != null) {
                mEtinterpassyr.setText("" + newPendingRemindersResponse.interPassyear);
            }
            if (newPendingRemindersResponse.expectedMarks != null) {
                mEtattempeExpectedmarks.setText("" + newPendingRemindersResponse.expectedMarks);
            }
            if (newPendingRemindersResponse.previousMarks != null) {
                mEtPreviousmarks.setText("" + newPendingRemindersResponse.previousMarks);
            }
           /* if (newPendingRemindersResponse.marks != null) {
                mEtmarksobtained.setText("" + newPendingRemindersResponse.marks);
            }*/
            if (newPendingRemindersResponse.graduationUniversity != null && !newPendingRemindersResponse.graduationUniversity.isEmpty()) {
                mEtGraduniversity.setText(newPendingRemindersResponse.graduationUniversity);
            }

            if (newPendingRemindersResponse.graduationbranch != null && !newPendingRemindersResponse.graduationbranch.isEmpty()) {
                mEtGradDegree.setText(newPendingRemindersResponse.graduationbranch);
            }

            if (newPendingRemindersResponse.graduationMarks != null) {
                mEtGradDegreemarks.setText("" + newPendingRemindersResponse.graduationMarks);
            }
            if (newPendingRemindersResponse.graduationpassyear != null) {
                mEtGradpassyr.setText("" + newPendingRemindersResponse.graduationpassyear);
            }

            if (newPendingRemindersResponse.fatherJob != null && !newPendingRemindersResponse.fatherJob.isEmpty()) {
                mEtGovtjobtitle.setText(newPendingRemindersResponse.fatherJob);
            }
            if (newPendingRemindersResponse.cast != null && !newPendingRemindersResponse.cast.isEmpty()) {
                if (newPendingRemindersResponse.cast.equalsIgnoreCase("oc ")) {
                    rboc.setChecked(true);
                } else if (newPendingRemindersResponse.cast.equalsIgnoreCase("bc")) {
                    rbbc.setChecked(true);
                } else if (newPendingRemindersResponse.cast.equalsIgnoreCase("sc")) {
                    rbsc.setChecked(true);
                } else if (newPendingRemindersResponse.cast.equalsIgnoreCase("st")) {
                    rbst.setChecked(true);
                }
            }
            if (newPendingRemindersResponse.subCast != null && !newPendingRemindersResponse.subCast.isEmpty()) {
                mEtsubcaste.setText(newPendingRemindersResponse.subCast);
            }
            if (newPendingRemindersResponse.stateOfintrest != null && !newPendingRemindersResponse.stateOfintrest.isEmpty()) {
                if (newPendingRemindersResponse.stateOfintrest.equalsIgnoreCase("Local")) {
                    local.setChecked(true);
                } else if (newPendingRemindersResponse.stateOfintrest.equalsIgnoreCase("Abroad")) {
                    abroad.setChecked(true);
                } else if (newPendingRemindersResponse.stateOfintrest.equalsIgnoreCase("Long Term")) {
                    rbstateofintrestlongterm.setChecked(true);
                }
            }
            if (newPendingRemindersResponse.courseType != null && !newPendingRemindersResponse.courseType.isEmpty()) {
                if (newPendingRemindersResponse.courseType.equalsIgnoreCase("Short Term ")) {
                    shortterm.setChecked(true);
                } else if (newPendingRemindersResponse.courseType.equalsIgnoreCase("Long Term")) {
                    longterm.setChecked(true);
                }
            }


            if (newPendingRemindersResponse.entrenceTestName != null && !newPendingRemindersResponse.entrenceTestName.isEmpty()) {
                mEtRegistrationTest.setText(newPendingRemindersResponse.entrenceTestName);
            }
            if (newPendingRemindersResponse.entrancetestmarks != null) {
                mEtEntrancetestmarks.setText("" + newPendingRemindersResponse.entrancetestmarks);
            }
            if (newPendingRemindersResponse.fatherName != null && !newPendingRemindersResponse.fatherName.isEmpty()) {
                mEtPname.setText(newPendingRemindersResponse.fatherName);
            }
            if (newPendingRemindersResponse.matherName != null && !newPendingRemindersResponse.matherName.isEmpty()) {
                mEtmothername.setText(newPendingRemindersResponse.matherName);
            }
            if (newPendingRemindersResponse.fathercontact != null && !newPendingRemindersResponse.fathercontact.isEmpty()) {
                mEtpphno.setText(newPendingRemindersResponse.fathercontact);
            }
            if (newPendingRemindersResponse.mathercon != null && !newPendingRemindersResponse.mathercon.isEmpty()) {
                mEtParentAlt.setText(newPendingRemindersResponse.mathercon);
            }
            if (newPendingRemindersResponse.income != null && !newPendingRemindersResponse.income.isEmpty()) {
                mEtIncomeinLakhs.setText(newPendingRemindersResponse.income);
            }

            if (newPendingRemindersResponse.country != null && !newPendingRemindersResponse.country.isEmpty()) {
                mEtCountryOthers.setText(newPendingRemindersResponse.country);
            }
            if (newPendingRemindersResponse.localBudget != null) {
                mEtLocalBudget.setText("" + newPendingRemindersResponse.localBudget);
            }
            if (newPendingRemindersResponse.abroadBudget != null) {
                mEtAbroadBudget.setText("" + newPendingRemindersResponse.abroadBudget);
            }

            if (newPendingRemindersResponse.visitType != null && !newPendingRemindersResponse.visitType.isEmpty()) {
                if (newPendingRemindersResponse.visitType.equalsIgnoreCase("House visit")) {
                    house.setChecked(true);
                } else if (newPendingRemindersResponse.visitType.equalsIgnoreCase("Office visit")) {
                    rbofficevisit.setChecked(true);
                } /*else {
                    personal.setChecked(true);
                }*/

            }
            if (newPendingRemindersResponse.finalFeedBack != null && !newPendingRemindersResponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newPendingRemindersResponse.finalFeedBack);
            }

            if (newPendingRemindersResponse.callStatus != null && !newPendingRemindersResponse.callStatus.isEmpty()) {
                if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("int")) {
                    rbintersted.setChecked(true);
                } else if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("notint")) {
                    rbnotinterested.setChecked(true);
                } else if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("Call Back")) {
                    rbcallback.setChecked(true);
                } else if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("nr")) {
                    rbnottresponding.setChecked(true);
                } else if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("so")) {
                    rbswitchoff.setChecked(true);
                } else if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("oc")) {
                    rboutofcoverage.setChecked(true);
                } else if (newPendingRemindersResponse.callStatus.equalsIgnoreCase("others")) {
                    rbothers.setChecked(true);
                }

            }


            if (newPendingRemindersResponse.reminderdateTime != null && !newPendingRemindersResponse.reminderdateTime.isEmpty()) {
                mEtremainderdate.setText(newPendingRemindersResponse.reminderdateTime);
            }
            if (newPendingRemindersResponse.reminderMessage != null && !newPendingRemindersResponse.reminderMessage.isEmpty()) {
                mEtremtext.setText(newPendingRemindersResponse.reminderMessage);
            }


            if (newPendingRemindersResponse.fatherOcupat != null && !newPendingRemindersResponse.fatherOcupat.isEmpty()) {
                if (newPendingRemindersResponse.fatherOcupat.equalsIgnoreCase("Job ")) {
                    fatherjob.setChecked(true);
                } else if (newPendingRemindersResponse.fatherOcupat.equalsIgnoreCase("Bussiness")) {
                    bussiness.setChecked(true);
                } else if (newPendingRemindersResponse.fatherOcupat.equalsIgnoreCase("Farmer")) {
                    farmer.setChecked(true);
                }
            }
            if (newPendingRemindersResponse.occSector != null && !newPendingRemindersResponse.occSector.isEmpty()) {
                if (newPendingRemindersResponse.occSector.equalsIgnoreCase("Private ")) {
                    pvt.setChecked(true);
                } else if (newPendingRemindersResponse.occSector.equalsIgnoreCase("Govt")) {
                    govt.setChecked(true);
                }
            }
            if (newPendingRemindersResponse.mIncomeinLaks != null) {
                mEtmotherincome.setText("" + newPendingRemindersResponse.mIncomeinLaks);
            }

            if (newPendingRemindersResponse.fatherJobdescription != null && !newPendingRemindersResponse.fatherJobdescription.isEmpty()) {
                mEtfatherbussiness.setText(newPendingRemindersResponse.fatherJobdescription);
            }


            if (newPendingRemindersResponse.siblingCourse != null && !newPendingRemindersResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newPendingRemindersResponse.siblingCourse);
            }
            if (newPendingRemindersResponse.visitMsg != null && !newPendingRemindersResponse.visitMsg.isEmpty()) {
                mEtvisitmessage.setText(newPendingRemindersResponse.visitMsg);
            }
            if (newPendingRemindersResponse.visitStatus != null) {
                if (newPendingRemindersResponse.visitStatus.equals(true)) {
                    yesrb3.setChecked(true);
                } else if (newPendingRemindersResponse.visitStatus.equals(false)) {
                    norb3.setChecked(true);
                }
            }

            if (newPendingRemindersResponse.canditintrests != null && !newPendingRemindersResponse.canditintrests.isEmpty()) {
                if (newPendingRemindersResponse.canditintrests.equalsIgnoreCase("Parent")) {
                    appparent.setChecked(true);
                } else if (newPendingRemindersResponse.canditintrests.equalsIgnoreCase("Counsultancy")) {
                    appcounsultancy.setChecked(true);
                }
            }

            if (newPendingRemindersResponse.entranceQualification != null && !newPendingRemindersResponse.entranceQualification.isEmpty()) {
                mEttenthEntranceQual.setText(newPendingRemindersResponse.entranceQualification);
            }

            if (newPendingRemindersResponse.motherOccupationDesc != null && !newPendingRemindersResponse.motherOccupationDesc.isEmpty()) {
                if (newPendingRemindersResponse.motherOccupationDesc.equalsIgnoreCase("Job ")) {
                    mjob.setChecked(true);
                } else if (newPendingRemindersResponse.motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                    housewife.setChecked(true);
                }

            }

            if (newPendingRemindersResponse.motherJobdesc != null && !newPendingRemindersResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newPendingRemindersResponse.motherJobdesc);
            }

            if (newPendingRemindersResponse.consutancyDescr != null && !newPendingRemindersResponse.consutancyDescr.isEmpty()) {
                mEtcounsul.setText(newPendingRemindersResponse.consutancyDescr);
            }

            if (newPendingRemindersResponse.studyinAbroadDesc != null && !newPendingRemindersResponse.studyinAbroadDesc.isEmpty()) {
                if (newPendingRemindersResponse.studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                    yes1.setChecked(true);
                } else if (newPendingRemindersResponse.studyinAbroadDesc.equalsIgnoreCase("No ")) {
                    no1.setChecked(true);
                }

            }
            if (newPendingRemindersResponse.siblingCountry != null && !newPendingRemindersResponse.siblingCountry.isEmpty()) {
                mEtabroad.setText(newPendingRemindersResponse.siblingCountry);
            }
            if (newPendingRemindersResponse.siblingFee != null && !newPendingRemindersResponse.siblingFee.isEmpty()) {
                mEtSibilingsfee.setText(newPendingRemindersResponse.siblingFee);
            }
            if (newPendingRemindersResponse.siblingCourse != null && !newPendingRemindersResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newPendingRemindersResponse.siblingCourse);
            }

            if (newPendingRemindersResponse.visitDescriptiopn != null && !newPendingRemindersResponse.visitDescriptiopn.isEmpty()) {
                if (newPendingRemindersResponse.visitDescriptiopn.equalsIgnoreCase(" yes")) {
                    yesrb3.setChecked(true);
                } else if (newPendingRemindersResponse.visitDescriptiopn.equalsIgnoreCase("No")) {
                    norb3.setChecked(true);
                }

            }

            if (newPendingRemindersResponse.feedBackDescription != null && !newPendingRemindersResponse.feedBackDescription.isEmpty()) {
                mEtfeedback.setText(newPendingRemindersResponse.feedBackDescription);
            }
            if (newPendingRemindersResponse.abroadKnowledgeDesc != null && !newPendingRemindersResponse.abroadKnowledgeDesc.isEmpty()) {
                if (newPendingRemindersResponse.abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                    yes.setChecked(true);
                } else if (newCallBacksResponse.abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                    no.setChecked(true);
                }

            }

            if (newPendingRemindersResponse.callbackDate != null && !newPendingRemindersResponse.callbackDate.isEmpty()) {
                mEtOthers.setText(newPendingRemindersResponse.callbackDate);
            }
            if (newPendingRemindersResponse.visitDate != null && !newPendingRemindersResponse.visitDate.isEmpty()) {
                mEtvisittext.setText(newPendingRemindersResponse.visitDate);
            }
            if (newPendingRemindersResponse.callOtheStatus != null && !newPendingRemindersResponse.callOtheStatus.isEmpty()) {
                mEtotherstatus.setText(newPendingRemindersResponse.callOtheStatus);
            }

            if (newPendingRemindersResponse.motherOccupationSector != null && !newPendingRemindersResponse.motherOccupationSector.isEmpty()) {
                if (newPendingRemindersResponse.motherOccupationSector.equalsIgnoreCase("Private ")) {
                    motherpvt.setChecked(true);
                } else if (newPendingRemindersResponse.motherOccupationSector.equalsIgnoreCase("Govt")) {
                    mothergovt.setChecked(true);
                }
            }

            if (newPendingRemindersResponse.interbranch != null && !newPendingRemindersResponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelection(spinnerintercoursePosition);
            }
            if (newPendingRemindersResponse.interIntrestedCourse != null && !newPendingRemindersResponse.interIntrestedCourse.isEmpty()) {
                mSpinnerInterInterestedCourse.setSelection(spinnerinterintrestedcoursePosition);
            }

            if (newPendingRemindersResponse.siblings != null) {
                mSpinnerChildren.setSelection(newPendingRemindersResponse.siblings);
            }

            if (newPendingRemindersResponse.entranceTestAttemts != null) {
                mSpinnerinterentranceAttempts.setSelection(newPendingRemindersResponse.entranceTestAttemts);
            }

            if (newPendingRemindersResponse.state != null && !newPendingRemindersResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }

            if (newPendingRemindersResponse.leadfrom != null && !newPendingRemindersResponse.leadfrom.isEmpty()) {
                mEtSource.setText(newPendingRemindersResponse.leadfrom);
            }
           /* if (newPendingRemindersResponse.state!= null && !newPendingRemindersResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }*/
            //set all data required
        }


        if (getIntent().getStringExtra("case") != null && !getIntent().getStringExtra("case").isEmpty() &&
                getIntent().getStringExtra("case").equalsIgnoreCase("9")) {
            newTodaycallBacksResponse = (NewTodaycallBacksResponse) getIntent().getSerializableExtra("datatoday");
        }
        Log.e("newpendingresponse", "" + newTodaycallBacksResponse);
        if (newTodaycallBacksResponse != null) {
            if (newTodaycallBacksResponse.gender != null && !newTodaycallBacksResponse.gender.isEmpty()) {
                if (newTodaycallBacksResponse.gender.equalsIgnoreCase("male")) {
                    male.setChecked(true);
                } else {
                    female.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.country != null && !newTodaycallBacksResponse.country.isEmpty()) {
                mEtLocation.setText(newTodaycallBacksResponse.country);
            }
            if (newTodaycallBacksResponse.areaName != null && !newTodaycallBacksResponse.areaName.isEmpty()) {
                mEtAreaname.setText(newTodaycallBacksResponse.areaName);
            }
            if (newTodaycallBacksResponse.city != null && !newTodaycallBacksResponse.city.isEmpty()) {
                mEtCity.setText(newTodaycallBacksResponse.city);
            }
            if (newTodaycallBacksResponse.alternativecontact != null && !newTodaycallBacksResponse.alternativecontact.isEmpty()) {
                mEtStdaltcontact.setText(newTodaycallBacksResponse.alternativecontact);
            }
            if (newTodaycallBacksResponse.country != null && !newTodaycallBacksResponse.country.isEmpty()) {
                mEtLocation.setText(newTodaycallBacksResponse.country);
            }

            if (newTodaycallBacksResponse.qalification != null && !newTodaycallBacksResponse.qalification.isEmpty()) {
                if (newTodaycallBacksResponse.qalification.equalsIgnoreCase("SSC ")) {
                    ssc.setChecked(true);
                } else if (newTodaycallBacksResponse.qalification.equalsIgnoreCase("Inter")) {
                    inter.setChecked(true);
                } else if (newTodaycallBacksResponse.qalification.equalsIgnoreCase("graduation")) {
                    graduation.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.sSCmarks != null) {
                //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newTodaycallBacksResponse.sSCmarks)));
                mEttenthmarks.setText("" + newTodaycallBacksResponse.sSCmarks);
            }
            if (newTodaycallBacksResponse.sscfuturecourse != null && !newTodaycallBacksResponse.sscfuturecourse.isEmpty()) {
                mEttenthInterestdCourse.setText(newTodaycallBacksResponse.sscfuturecourse);
            }
            if (newTodaycallBacksResponse.sSCintrestedBranch != null && !newTodaycallBacksResponse.sSCintrestedBranch.isEmpty()) {
                mEttenthinterestedbranch.setText(newTodaycallBacksResponse.sSCintrestedBranch);
            }

            if (newTodaycallBacksResponse.fatherJob != null && !newTodaycallBacksResponse.fatherJob.isEmpty()) {
                mEtGovtjobtitle.setText(newTodaycallBacksResponse.fatherJob);
            }
            if (newTodaycallBacksResponse.entrenceTestName != null && !newTodaycallBacksResponse.entrenceTestName.isEmpty()) {
                mEttenthEntranceExam.setText(newTodaycallBacksResponse.entrenceTestName);
            }
            if (newTodaycallBacksResponse.interbranch != null && !newTodaycallBacksResponse.interbranch.isEmpty()) {
                mEtinterCourse.setText(newTodaycallBacksResponse.interbranch);
            }
            if (newTodaycallBacksResponse.interPassyear != null) {
                mEtinterpassyr.setText("" + newTodaycallBacksResponse.interPassyear);
            }
            if (newTodaycallBacksResponse.expectedMarks != null) {
                mEtattempeExpectedmarks.setText("" + newTodaycallBacksResponse.expectedMarks);
            }
            if (newTodaycallBacksResponse.previousMarks != null) {
                mEtPreviousmarks.setText("" + newTodaycallBacksResponse.previousMarks);
            }
            if (newTodaycallBacksResponse.marks != null) {
                mEtmarksobtained.setText("" + newTodaycallBacksResponse.marks);
            }
            if (newTodaycallBacksResponse.graduationUniversity != null && !newTodaycallBacksResponse.graduationUniversity.isEmpty()) {
                mEtGraduniversity.setText(newTodaycallBacksResponse.graduationUniversity);
            }

            if (newTodaycallBacksResponse.graduationbranch != null && !newTodaycallBacksResponse.graduationbranch.isEmpty()) {
                mEtGradDegree.setText(newTodaycallBacksResponse.graduationbranch);
            }

            if (newTodaycallBacksResponse.graduationMarks != null) {
                mEtGradDegreemarks.setText("" + newTodaycallBacksResponse.graduationMarks);
            }
            if (newTodaycallBacksResponse.graduationpassyear != null) {
                mEtGradpassyr.setText("" + newTodaycallBacksResponse.graduationpassyear);
            }
            if (newTodaycallBacksResponse.cast != null && !newTodaycallBacksResponse.cast.isEmpty()) {
                if (newTodaycallBacksResponse.cast.equalsIgnoreCase("oc ")) {
                    rboc.setChecked(true);
                } else if (newTodaycallBacksResponse.cast.equalsIgnoreCase("bc")) {
                    rbbc.setChecked(true);
                } else if (newTodaycallBacksResponse.cast.equalsIgnoreCase("sc")) {
                    rbsc.setChecked(true);
                } else if (newTodaycallBacksResponse.cast.equalsIgnoreCase("st")) {
                    rbst.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.subCast != null && !newTodaycallBacksResponse.subCast.isEmpty()) {
                mEtsubcaste.setText(newTodaycallBacksResponse.subCast);
            }
            if (newTodaycallBacksResponse.stateOfintrest != null && !newTodaycallBacksResponse.stateOfintrest.isEmpty()) {
                if (newTodaycallBacksResponse.stateOfintrest.equalsIgnoreCase("local")) {
                    local.setChecked(true);
                } else if (newTodaycallBacksResponse.stateOfintrest.equalsIgnoreCase("abroad")) {
                    abroad.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.entrenceTestName != null && !newTodaycallBacksResponse.entrenceTestName.isEmpty()) {
                mEtRegistrationTest.setText(newTodaycallBacksResponse.entrenceTestName);
            }
            if (newTodaycallBacksResponse.entrancetestmarks != null) {
                mEtEntrancetestmarks.setText("" + newTodaycallBacksResponse.entrancetestmarks);
            }
            if (newTodaycallBacksResponse.fatherName != null && !newTodaycallBacksResponse.fatherName.isEmpty()) {
                mEtPname.setText(newTodaycallBacksResponse.fatherName);
            }
            if (newTodaycallBacksResponse.matherName != null && !newTodaycallBacksResponse.matherName.isEmpty()) {
                mEtmothername.setText(newTodaycallBacksResponse.matherName);
            }
            if (newTodaycallBacksResponse.fathercontact != null && !newTodaycallBacksResponse.fathercontact.isEmpty()) {
                mEtpphno.setText(newTodaycallBacksResponse.fathercontact);
            }
            if (newTodaycallBacksResponse.mathercon != null && !newTodaycallBacksResponse.mathercon.isEmpty()) {
                mEtParentAlt.setText(newTodaycallBacksResponse.mathercon);
            }
            if (newTodaycallBacksResponse.income != null && !newTodaycallBacksResponse.income.isEmpty()) {
                mEtIncomeinLakhs.setText(newTodaycallBacksResponse.income);
            }

            if (newTodaycallBacksResponse.country != null && !newTodaycallBacksResponse.country.isEmpty()) {
                mEtCountryOthers.setText(newTodaycallBacksResponse.country);
            }
            if (newTodaycallBacksResponse.localBudget != null) {
                mEtLocalBudget.setText("" + newTodaycallBacksResponse.localBudget);
            }
            if (newTodaycallBacksResponse.abroadBudget != null) {
                mEtAbroadBudget.setText("" + newTodaycallBacksResponse.abroadBudget);
            }

            if (newTodaycallBacksResponse.visitType != null && !newTodaycallBacksResponse.visitType.isEmpty()) {
                if (newTodaycallBacksResponse.visitType.equalsIgnoreCase("House visit")) {
                    house.setChecked(true);
                } else if (newTodaycallBacksResponse.visitType.equalsIgnoreCase("Office visit")) {
                    rbofficevisit.setChecked(true);
                } /*else {
                    personal.setChecked(true);
                }*/

            }
            if (newTodaycallBacksResponse.finalFeedBack != null && !newTodaycallBacksResponse.finalFeedBack.isEmpty()) {
                mEtremarks.setText(newTodaycallBacksResponse.finalFeedBack);
            }

            if (newTodaycallBacksResponse.callStatus != null && !newTodaycallBacksResponse.callStatus.isEmpty()) {
                if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("int")) {
                    rbintersted.setChecked(true);
                } else if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("notint")) {
                    rbnotinterested.setChecked(true);
                } else if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("Call Back")) {
                    rbcallback.setChecked(true);
                    mEtOthers.setVisibility(View.VISIBLE);
                } else if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("nr")) {
                    rbnottresponding.setChecked(true);
                } else if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("so")) {
                    rbswitchoff.setChecked(true);
                } else if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("oc")) {
                    rboutofcoverage.setChecked(true);
                } else if (newTodaycallBacksResponse.callStatus.equalsIgnoreCase("others")) {
                    rbothers.setChecked(true);
                }

            }
            if (newTodaycallBacksResponse.courseType != null && !newTodaycallBacksResponse.courseType.isEmpty()) {
                if (newTodaycallBacksResponse.courseType.equalsIgnoreCase("Short Term ")) {
                    shortterm.setChecked(true);

                } else if (newTodaycallBacksResponse.courseType.equalsIgnoreCase("Long Term")) {
                    longterm.setChecked(true);
                }

            }

            if (newTodaycallBacksResponse.reminderdateTime != null && !newTodaycallBacksResponse.reminderdateTime.isEmpty()) {
                mEtremainderdate.setText(newTodaycallBacksResponse.reminderdateTime);
            }
            if (newTodaycallBacksResponse.reminderMessage != null && !newTodaycallBacksResponse.reminderMessage.isEmpty()) {
                mEtremtext.setText(newTodaycallBacksResponse.reminderMessage);
            }

            if (newTodaycallBacksResponse.fatherOcupat != null && !newTodaycallBacksResponse.fatherOcupat.isEmpty()) {
                if (newTodaycallBacksResponse.fatherOcupat.equalsIgnoreCase("Job ")) {
                    fatherjob.setChecked(true);
                } else if (newTodaycallBacksResponse.fatherOcupat.equalsIgnoreCase("Bussiness")) {
                    bussiness.setChecked(true);
                } else if (newTodaycallBacksResponse.fatherOcupat.equalsIgnoreCase("Farmer")) {
                    farmer.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.occSector != null && !newTodaycallBacksResponse.occSector.isEmpty()) {
                if (newTodaycallBacksResponse.occSector.equalsIgnoreCase("Private ")) {
                    pvt.setChecked(true);
                } else if (newTodaycallBacksResponse.occSector.equalsIgnoreCase("Govt")) {
                    govt.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.mIncomeinLaks != null) {
                mEtmotherincome.setText("" + newTodaycallBacksResponse.mIncomeinLaks);
            }

            if (newTodaycallBacksResponse.fatherJobdescription != null && !newTodaycallBacksResponse.fatherJobdescription.isEmpty()) {
                mEtfatherbussiness.setText(newTodaycallBacksResponse.fatherJobdescription);
            }

            if (newTodaycallBacksResponse.siblingCourse != null && !newTodaycallBacksResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newTodaycallBacksResponse.siblingCourse);
            }
            if (newTodaycallBacksResponse.visitMsg != null && !newTodaycallBacksResponse.visitMsg.isEmpty()) {
                mEtvisitmessage.setText(newTodaycallBacksResponse.visitMsg);
            }

            if (newTodaycallBacksResponse.visitStatus != null) {
                if (newTodaycallBacksResponse.visitStatus.equals(true)) {
                    yesrb3.setChecked(true);
                } else if (newTodaycallBacksResponse.visitStatus.equals(false)) {
                    norb3.setChecked(true);
                }
            }

            if (newTodaycallBacksResponse.canditintrests != null && !newTodaycallBacksResponse.canditintrests.isEmpty()) {
                if (newTodaycallBacksResponse.canditintrests.equalsIgnoreCase("Parent")) {
                    appparent.setChecked(true);
                } else if (newTodaycallBacksResponse.canditintrests.equalsIgnoreCase("Counsultancy")) {
                    appcounsultancy.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.entranceQualification != null && !newTodaycallBacksResponse.entranceQualification.isEmpty()) {
                mEttenthEntranceQual.setText(newTodaycallBacksResponse.entranceQualification);
            }
            if (newTodaycallBacksResponse.motherOccupationDesc != null && !newTodaycallBacksResponse.motherOccupationDesc.isEmpty()) {
                if (newTodaycallBacksResponse.motherOccupationDesc.equalsIgnoreCase("Job ")) {
                    mjob.setChecked(true);
                } else if (newTodaycallBacksResponse.motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                    housewife.setChecked(true);
                }

            }

            if (newTodaycallBacksResponse.motherJobdesc != null && !newTodaycallBacksResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newTodaycallBacksResponse.motherJobdesc);
            }
            if (newTodaycallBacksResponse.consutancyDescr != null && !newTodaycallBacksResponse.consutancyDescr.isEmpty()) {
                mEtcounsul.setText(newTodaycallBacksResponse.consutancyDescr);
            }

            if (newTodaycallBacksResponse.studyinAbroadDesc != null && !newTodaycallBacksResponse.studyinAbroadDesc.isEmpty()) {
                if (newTodaycallBacksResponse.studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                    yes1.setChecked(true);
                } else if (newTodaycallBacksResponse.studyinAbroadDesc.equalsIgnoreCase("No ")) {
                    no1.setChecked(true);
                }

            }

            if (newTodaycallBacksResponse.siblingCountry != null && !newTodaycallBacksResponse.siblingCountry.isEmpty()) {
                mEtabroad.setText(newTodaycallBacksResponse.siblingCountry);
            }
            if (newTodaycallBacksResponse.siblingFee != null && !newTodaycallBacksResponse.siblingFee.isEmpty()) {
                mEtSibilingsfee.setText(newTodaycallBacksResponse.siblingFee);
            }
            if (newTodaycallBacksResponse.siblingCourse != null && !newTodaycallBacksResponse.siblingCourse.isEmpty()) {
                mEtSibilingsDuration.setText(newTodaycallBacksResponse.siblingCourse);
            }


            if (newTodaycallBacksResponse.visitDescriptiopn != null && !newTodaycallBacksResponse.visitDescriptiopn.isEmpty()) {
                if (newTodaycallBacksResponse.visitDescriptiopn.equalsIgnoreCase(" yes")) {
                    yesrb3.setChecked(true);
                } else if (newTodaycallBacksResponse.visitDescriptiopn.equalsIgnoreCase("No")) {
                    norb3.setChecked(true);
                }

            }

            if (newTodaycallBacksResponse.feedBackDescription != null && !newTodaycallBacksResponse.feedBackDescription.isEmpty()) {
                mEtfeedback.setText(newTodaycallBacksResponse.feedBackDescription);
            }

            if (newTodaycallBacksResponse.abroadKnowledgeDesc != null && !newTodaycallBacksResponse.abroadKnowledgeDesc.isEmpty()) {
                if (newTodaycallBacksResponse.abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                    yes.setChecked(true);
                } else if (newCallBacksResponse.abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                    no.setChecked(true);
                }

            }
            if (newTodaycallBacksResponse.callbackDate != null && !newTodaycallBacksResponse.callbackDate.isEmpty()) {
                mEtOthers.setText(newTodaycallBacksResponse.callbackDate);
            }
            if (newTodaycallBacksResponse.visitDate != null && !newTodaycallBacksResponse.visitDate.isEmpty()) {
                mEtvisittext.setText(newTodaycallBacksResponse.visitDate);
            }

            if (newTodaycallBacksResponse.callOtheStatus != null && !newTodaycallBacksResponse.callOtheStatus.isEmpty()) {
                mEtotherstatus.setText(newTodaycallBacksResponse.callOtheStatus);
            }

            if (newTodaycallBacksResponse.motherOccupationSector != null && !newTodaycallBacksResponse.motherOccupationSector.isEmpty()) {
                if (newTodaycallBacksResponse.motherOccupationSector.equalsIgnoreCase("Private ")) {
                    motherpvt.setChecked(true);
                } else if (newTodaycallBacksResponse.motherOccupationSector.equalsIgnoreCase("Govt")) {
                    mothergovt.setChecked(true);
                }
            }
            if (newTodaycallBacksResponse.motherJobdesc != null && !newTodaycallBacksResponse.motherJobdesc.isEmpty()) {
                mEtmotherdepartment.setText(newTodaycallBacksResponse.motherJobdesc);
            }

            if (newTodaycallBacksResponse.intrestedCountry != null && !newTodaycallBacksResponse.intrestedCountry.isEmpty()) {
                mSpinnerCountry.setSelection(spinnerPosition);
            }

            if (newTodaycallBacksResponse.interbranch != null && !newTodaycallBacksResponse.interbranch.isEmpty()) {
                mSpinnerInterCourse.setSelection(spinnerintercoursePosition);
            }
            if (newTodaycallBacksResponse.interIntrestedCourse != null && !newTodaycallBacksResponse.interIntrestedCourse.isEmpty()) {
                mSpinnerInterInterestedCourse.setSelection(spinnerinterintrestedcoursePosition);
            }

            if (newTodaycallBacksResponse.siblings != null) {
                mSpinnerChildren.setSelection(newTodaycallBacksResponse.siblings);
            }

            if (newTodaycallBacksResponse.entranceTestAttemts != null) {
                mSpinnerinterentranceAttempts.setSelection(newTodaycallBacksResponse.entranceTestAttemts);
            }

            if (newTodaycallBacksResponse.state != null && !newTodaycallBacksResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }

            if (newTodaycallBacksResponse.leadfrom != null && !newTodaycallBacksResponse.leadfrom.isEmpty()) {
                mEtSource.setText(newTodaycallBacksResponse.leadfrom);
            }
/*
            if (newTodaycallBacksResponse.state!= null && !newTodaycallBacksResponse.state.isEmpty()) {
                mSpinnerStates.setSelection(spinnerstatesPosition);
            }*/
            //set all data required
        }


    }

    private void CheckNumber() {

        contactNumber = mEtcontactnumber.getText().toString();
        // Log.e("contact number",""+contactNumber);

        if (contactNumber != null && !contactNumber.isEmpty()) {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<CheckResponse> checkResponseCall = apiInterface.getCheckResponse(contactNumber);
            checkResponseCall.enqueue(new Callback<CheckResponse>() {
                @Override
                public void onResponse(Call<CheckResponse> call, final Response<CheckResponse> response) {
                    Log.e("response:",""+response);
                    if (response != null && response.isSuccessful()) {


                        if (response.body().errorMessage.equalsIgnoreCase("Success")) {


                            AlertDialog.Builder builder = new AlertDialog.Builder(NewformActivity.this);
                            builder.setCancelable(false);
                            builder.setTitle("Record Found");
                            builder.setMessage("If You Want to fill Data !!!");
                            builder.setPositiveButton("FillData", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {


                                    if (response.body().leadName != null && !response.body().leadName.isEmpty()) {
                                        mEtstdname.setText(response.body().leadName);
                                    }

                                    if (response.body().leadfrom != null && !response.body().leadfrom.isEmpty()) {
                                        mEtSource.setText(response.body().leadfrom);
                                    }
                                    if (response.body().country != null && !response.body().country.isEmpty()) {
                                        mEtLocation.setText(response.body().country);
                                    }
                                    if (response.body().gender != null) {

                                        if (response.body().gender.equalsIgnoreCase("male")) {
                                            male.setChecked(true);
                                        } else {
                                            female.setChecked(true);
                                        }
                                    }

                                    if (response.body().areaName != null && !response.body().areaName.isEmpty()) {
                                        mEtAreaname.setText(response.body().areaName);
                                    }


                                    if (response.body().city != null && !response.body().city.isEmpty()) {
                                        mEtCity.setText(response.body().city);
                                    }

                                    if (response.body().alternativecontact != null && !response.body().alternativecontact.isEmpty()) {
                                        mEtStdaltcontact.setText(response.body().alternativecontact);
                                    }


                                    if (response.body().fatherJob != null && !response.body().fatherJob.isEmpty()) {
                                        mEtGovtjobtitle.setText(response.body().fatherJob);
                                    }
                                    if (response.body().qalification != null) {

                                        if (response.body().qalification.equalsIgnoreCase("SSC ")) {
                                            ssc.setChecked(true);
                                        } else if (response.body().qalification.equalsIgnoreCase("Inter")) {
                                            inter.setChecked(true);
                                        } else if (response.body().qalification.equalsIgnoreCase("graduation")) {
                                            graduation.setChecked(true);
                                        }

                                    }

                                    if (response.body().sSCmarks != null) {
                                        //  mEttenthmarks.setText((int) Double.parseDouble(String.valueOf(newCallBacksResponse.sSCmarks)));
                                        mEttenthmarks.setText("" + response.body().sSCmarks);
                                    }
                                    if (response.body().sscfuturecourse != null && !response.body().sscfuturecourse.isEmpty()) {
                                        mEttenthInterestdCourse.setText(response.body().sscfuturecourse);
                                    }
                                    if (response.body().sSCintrestedBranch != null && !response.body().sSCintrestedBranch.isEmpty()) {
                                        mEttenthinterestedbranch.setText(response.body().sSCintrestedBranch);
                                    }

                                    if (response.body().entrenceTestName != null && !response.body().entrenceTestName.isEmpty()) {
                                        mEttenthEntranceExam.setText(response.body().entrenceTestName);
                                    }
                                    if (response.body().marks != null) {
                                        mEtintermarks.setText("" + response.body().expectedMarks);
                                    }
                                    if (response.body().interbranch != null && !response.body().interbranch.isEmpty()) {
                                        mEtinterCourse.setText(response.body().interbranch);
                                    }
                                    if (response.body().interPassyear != null) {
                                        mEtinterpassyr.setText("" + response.body().interPassyear);
                                    }
                                    if (response.body().expectedMarks != null) {
                                        mEtattempeExpectedmarks.setText("" + response.body().expectedMarks);
                                    }
                                    if (response.body().previousMarks != null) {
                                        mEtPreviousmarks.setText("" + response.body().previousMarks);
                                    }
          /*  if (newCallBacksResponse.marks != null) {
                mEtmarksobtained.setText("" + newCallBacksResponse.marks);
            }*/
                                    if (response.body().graduationUniversity != null && !response.body().graduationUniversity.isEmpty()) {
                                        mEtGraduniversity.setText(response.body().graduationUniversity);
                                    }

                                    if (response.body().graduationbranch != null && !response.body().graduationbranch.isEmpty()) {
                                        mEtGradDegree.setText(response.body().graduationbranch);
                                    }

                                    if (response.body().graduationMarks != null) {
                                        mEtGradDegreemarks.setText("" + response.body().graduationMarks);
                                    }
                                    if (response.body().graduationpassyear != null) {
                                        mEtGradpassyr.setText("" + response.body().graduationpassyear);
                                    }
                                    if (response.body().cast != null && !response.body().cast.isEmpty()) {
                                        if (response.body().cast.equalsIgnoreCase("oc ")) {
                                            rboc.setChecked(true);
                                        } else if (response.body().cast.equalsIgnoreCase("bc")) {
                                            rbbc.setChecked(true);
                                        } else if (response.body().cast.equalsIgnoreCase("sc")) {
                                            rbsc.setChecked(true);
                                        } else if (response.body().cast.equalsIgnoreCase("st")) {
                                            rbst.setChecked(true);
                                        }
                                    }
                                    if (response.body().subCast != null && !response.body().subCast.isEmpty()) {
                                        mEtsubcaste.setText(response.body().subCast);
                                    }
                                    if (response.body().stateOfintrest != null && !response.body().stateOfintrest.isEmpty()) {
                                        if (response.body().stateOfintrest.equalsIgnoreCase("Local")) {
                                            local.setChecked(true);
                                        } else if (response.body().stateOfintrest.equalsIgnoreCase("Abroad")) {
                                            abroad.setChecked(true);
                                        } else if (response.body().stateOfintrest.equalsIgnoreCase("Long Term")) {
                                            rbstateofintrestlongterm.setChecked(true);
                                        }
                                    }

                                    if (response.body().courseType != null && !response.body().courseType.isEmpty()) {
                                        if (response.body().courseType.equalsIgnoreCase("Short Term ")) {
                                            shortterm.setChecked(true);
                                        } else if (response.body().courseType.equalsIgnoreCase("Long Term")) {
                                            longterm.setChecked(true);
                                        }
                                    }

                                    if (response.body().entrenceTestName != null && !response.body().entrenceTestName.isEmpty()) {
                                        mEtRegistrationTest.setText(response.body().entrenceTestName);
                                    }
                                    if (response.body().entrancetestmarks != null) {
                                        mEtEntrancetestmarks.setText("" + response.body().entrancetestmarks);
                                    }
                                    if (response.body().fatherName != null && !response.body().fatherName.isEmpty()) {
                                        mEtPname.setText(response.body().fatherName);
                                    }
                                    if (response.body().matherName != null && !response.body().matherName.isEmpty()) {
                                        mEtmothername.setText(response.body().matherName);
                                    }
                                    if (response.body().fathercontact != null && !response.body().fathercontact.isEmpty()) {
                                        mEtpphno.setText(response.body().fathercontact);
                                    }
                                    if (response.body().mathercon != null && !response.body().mathercon.isEmpty()) {
                                        mEtParentAlt.setText(response.body().mathercon);
                                    }
                                    if (response.body().income != null && !response.body().income.isEmpty()) {
                                        mEtIncomeinLakhs.setText(response.body().income);
                                    }

                                    if (response.body().country != null && !response.body().country.isEmpty()) {
                                        mEtCountryOthers.setText(response.body().country);
                                    }
                                    if (response.body().localBudget != null) {
                                        mEtLocalBudget.setText("" + response.body().localBudget);
                                    }
                                    if (response.body().abroadBudget != null) {
                                        mEtAbroadBudget.setText("" + response.body().abroadBudget);
                                    }

                                    if (response.body().visitType != null && !response.body().visitType.isEmpty()) {
                                        if (response.body().visitType.equalsIgnoreCase("House visit")) {
                                            house.setChecked(true);
                                        } else if (response.body().visitType.equalsIgnoreCase("Office visit")) {
                                            rbofficevisit.setChecked(true);
                                        }/* else {
                    personal.setChecked(true);
                }*/

                                    }
                                    if (response.body().finalFeedBack != null && !response.body().finalFeedBack.isEmpty()) {
                                        mEtremarks.setText(response.body().finalFeedBack);
                                    }

                                    if (response.body().callStatus != null && !response.body().callStatus.isEmpty()) {
                                        if (response.body().callStatus.equalsIgnoreCase("int")) {
                                            rbintersted.setChecked(true);
                                        } else if (response.body().callStatus.equalsIgnoreCase("notint")) {
                                            rbnotinterested.setChecked(true);
                                        } else if (response.body().callStatus.equalsIgnoreCase("Call Back")) {
                                            rbcallback.setChecked(true);
                                            mEtOthers.setVisibility(View.VISIBLE);
                                        } else if (response.body().callStatus.equalsIgnoreCase("nr")) {
                                            rbnottresponding.setChecked(true);
                                        } else if (response.body().callStatus.equalsIgnoreCase("so")) {
                                            rbswitchoff.setChecked(true);
                                        } else if (response.body().callStatus.equalsIgnoreCase("oc")) {
                                            rboutofcoverage.setChecked(true);
                                        } else if (response.body().callStatus.equalsIgnoreCase("others")) {
                                            rbothers.setChecked(true);
                                        }

                                    }

                                    if (response.body().fatherOcupat != null && !response.body().fatherOcupat.isEmpty()) {
                                        if (response.body().fatherOcupat.equalsIgnoreCase("Job ")) {
                                            fatherjob.setChecked(true);
                                        } else if (response.body().fatherOcupat.equalsIgnoreCase("Bussiness")) {
                                            bussiness.setChecked(true);
                                        } else if (response.body().fatherOcupat.equalsIgnoreCase("Farmer")) {
                                            farmer.setChecked(true);
                                        }
                                    }
                                    if (response.body().occSector != null && !response.body().occSector.isEmpty()) {
                                        if (response.body().occSector.equalsIgnoreCase("Private ")) {
                                            pvt.setChecked(true);
                                        } else if (response.body().occSector.equalsIgnoreCase("Govt")) {
                                            govt.setChecked(true);
                                        }
                                    }
/*
            if(response.body().reminderdateTime!=null && !response.body().reminderdateTime.isEmpty()){
                mEtremainderdate.setText(response.body().reminderdateTime);
            }*/
                                    if (response.body().reminderMessage != null && !response.body().reminderMessage.isEmpty()) {
                                        mEtremtext.setText(response.body().reminderMessage);
                                    }

                                    if (response.body().mIncomeinLaks != null) {
                                        mEtmotherincome.setText("" + response.body().mIncomeinLaks);
                                    }


                                    if (response.body().fatherJobdescription != null && !response.body().fatherJobdescription.isEmpty()) {
                                        mEtfatherbussiness.setText(response.body().fatherJobdescription);
                                    }

                                    if (response.body().siblingCourse != null && !response.body().siblingCourse.isEmpty()) {
                                        mEtSibilingsDuration.setText(response.body().siblingCourse);
                                    }

                                    if (response.body().visitMsg != null && !response.body().visitMsg.isEmpty()) {
                                        mEtvisitmessage.setText(response.body().visitMsg);
                                    }

                                    if (response.body().visitStatus != null) {
                                        if (response.body().visitStatus.equals(true)) {
                                            yesrb3.setChecked(true);
                                        } else if (response.body().visitStatus.equals(false)) {
                                            norb3.setChecked(true);
                                        }
                                    }

                                    if (response.body().canditintrests != null && !response.body().canditintrests.isEmpty()) {
                                        if (response.body().canditintrests.equalsIgnoreCase("Parent")) {
                                            appparent.setChecked(true);
                                        } else if (response.body().canditintrests.equalsIgnoreCase("Counsultancy")) {
                                            appcounsultancy.setChecked(true);
                                        }
                                    }

                                    if (response.body().entranceQualification != null && !response.body().entranceQualification.isEmpty()) {
                                        mEttenthEntranceQual.setText(response.body().entranceQualification);
                                    }
                                    if (response.body().motherOccupationDesc != null && !response.body().motherOccupationDesc.isEmpty()) {
                                        if (response.body().motherOccupationDesc.equalsIgnoreCase("Job ")) {
                                            mjob.setChecked(true);
                                        } else if (response.body().motherOccupationDesc.equalsIgnoreCase("House Wife")) {
                                            housewife.setChecked(true);
                                        }

                                    }

                                    if (response.body().motherJobdesc != null && !response.body().motherJobdesc.isEmpty()) {
                                        mEtmotherdepartment.setText(response.body().motherJobdesc);
                                    }

                                    if (response.body().consutancyDescr != null && !response.body().consutancyDescr.isEmpty()) {
                                        mEtcounsul.setText(response.body().consutancyDescr);
                                    }

                                    if (response.body().studyinAbroadDesc != null && !response.body().studyinAbroadDesc.isEmpty()) {
                                        if (response.body().studyinAbroadDesc.equalsIgnoreCase(" yes")) {
                                            yes1.setChecked(true);
                                        } else if (response.body().studyinAbroadDesc.equalsIgnoreCase("No ")) {
                                            no1.setChecked(true);
                                        }

                                    }

                                    if (response.body().siblingCountry != null && !response.body().siblingCountry.isEmpty()) {
                                        mEtabroad.setText(response.body().siblingCountry);
                                    }
                                    if (response.body().siblingFee != null && !response.body().siblingFee.isEmpty()) {
                                        mEtSibilingsfee.setText(response.body().siblingFee);
                                    }
                                    if (response.body().siblingCourse != null && !response.body().siblingCourse.isEmpty()) {
                                        mEtSibilingsDuration.setText(response.body().siblingCourse);
                                    }

                                    if (response.body().visitDescriptiopn != null && !response.body().visitDescriptiopn.isEmpty()) {
                                        if (response.body().visitDescriptiopn.equalsIgnoreCase(" yes")) {
                                            yesrb3.setChecked(true);
                                        } else if (response.body().visitDescriptiopn.equalsIgnoreCase("No")) {
                                            norb3.setChecked(true);
                                        }

                                    }

                                    if (response.body().feedBackDescription != null && !response.body().feedBackDescription.isEmpty()) {
                                        mEtfeedback.setText(response.body().feedBackDescription);
                                    }
                                    if (response.body().abroadKnowledgeDesc != null && !response.body().abroadKnowledgeDesc.isEmpty()) {
                                        if (response.body().abroadKnowledgeDesc.equalsIgnoreCase(" yes")) {
                                            yes.setChecked(true);
                                        } else if (response.body().abroadKnowledgeDesc.equalsIgnoreCase("No ")) {
                                            no.setChecked(true);
                                        }

                                    }
                                    if (response.body().callbackDate != null && !response.body().callbackDate.isEmpty()) {
                                        mEtOthers.setText(response.body().callbackDate);
                                    }
                                    if (response.body().visitDate != null && !response.body().visitDate.isEmpty()) {
                                        mEtvisittext.setText(response.body().visitDate);
                                    }
                                    if (response.body().callOtheStatus != null && !response.body().callOtheStatus.isEmpty()) {
                                        mEtotherstatus.setText(response.body().callOtheStatus);
                                    }
                                    if (response.body().motherOccupationSector != null && !response.body().motherOccupationSector.isEmpty()) {
                                        if (response.body().motherOccupationSector.equalsIgnoreCase("Private ")) {
                                            motherpvt.setChecked(true);
                                        } else if (response.body().motherOccupationSector.equalsIgnoreCase("Govt")) {
                                            mothergovt.setChecked(true);
                                        }
                                    }
                                    if (response.body().motherJobdesc != null && !response.body().motherJobdesc.isEmpty()) {
                                        mEtmotherdepartment.setText(response.body().motherJobdesc);
                                    }

                                    if (response.body().intrestedCountry != null && !response.body().intrestedCountry.isEmpty()) {
                                        int count = 0;
                                        for (String area : categories) {
                                            count++;
                                            if (area.equalsIgnoreCase(response.body().intrestedCountry)) {
                                                break;
                                            }
                                        }
                                        mSpinnerCountry.setSelection(count-1);
                                    }

                                    if (response.body().interbranch != null && !response.body().interbranch.isEmpty()) {
                                        int count = 0;
                                        for (String area : categoriesInterCourse) {
                                            count++;
                                            if (area.equalsIgnoreCase(response.body().interbranch)) {
                                                break;
                                            }
                                        }

                                        mSpinnerInterCourse.setSelection(count-1);
                                    }
                                    if (response.body().interIntrestedCourse != null && !response.body().interIntrestedCourse.isEmpty()) {

                                        int count = 0;
                                        for (String area : categoriesCourse) {
                                            count++;
                                            if (area.equalsIgnoreCase(response.body().interIntrestedCourse)) {
                                                break;
                                            }
                                        }
                                        mSpinnerInterInterestedCourse.setSelection(count-1);
                                    }

                                    if (response.body().state != null && !response.body().state.isEmpty()) {


                                        int count = 0;
                                        for (String area : states) {
                                            count++;
                                            if (area.equalsIgnoreCase(response.body().state)) {
                                                break;
                                            }
                                        }
                                        mSpinnerStates.setSelection(count-1);
                                       // Log.e("statesposition", "" + spinnerstatesPosition);
                                    }
                                    if (response.body().siblings != null) {
                                        mSpinnerChildren.setSelection(response.body().siblings);
                                    }

                                    if (response.body().entranceTestAttemts != null) {
                                        mSpinnerinterentranceAttempts.setSelection(response.body().entranceTestAttemts);
                                    }


                                    if (response.body().leadfrom != null && !response.body().leadfrom.isEmpty()) {
                                        mEtSource.setText(response.body().leadfrom);
                                    }
                                }


                            })
                                    .setNegativeButton("Cancel ", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            dialog.dismiss();

                                        }
                                    });

                            // Create the AlertDialog object and return it
                            builder.create().show();
                        } else {


                            //Log.e("contact",""+contactNumber);
                            Toast.makeText(NewformActivity.this, "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(NewformActivity.this, "Record Not Found", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<CheckResponse> call, Throwable t) {
                    Toast.makeText(NewformActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }


    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.field, null);
        // Add the new row before the add field button.
        parentLayout.addView(rowView, parentLayout.getChildCount() - 1);
    }

    public void onDelete(View v) {
        parentLayout.removeView((View) v.getParent());
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
    public void onClick(View v) {
        switch (v.getId()) {
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
                mEtOthers.setVisibility(View.GONE);
                mEtotherstatus.setVisibility(View.VISIBLE);
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
                mEtotherstatus.setVisibility(View.GONE);
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
                mEtotherstatus.setVisibility(View.GONE);
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
                mEtotherstatus.setVisibility(View.GONE);
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
                mEtotherstatus.setVisibility(View.GONE);
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
                mEtotherstatus.setVisibility(View.GONE);
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
                mEtotherstatus.setVisibility(View.GONE);
                break;
            case R.id.btninfosubmit:

               /* if (mEtremainderdate.length()==0){
                    mEtremainderdate.setError("Plese Select Reminder Date");
                }

               else*/
                if (mEtremarks.length() == 0) {
                    mEtremarks.setError("Please Enter Conclusion.....");
                    mEtremarks.requestFocus();
                    // Toast.makeText(context, "Please Enter Conclusion.....", Toast.LENGTH_SHORT).show();
                } else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(NewformActivity.this);
                    builder.setMessage("Are you sure you want to submit?")
                            .setCancelable(false)
                            .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    loadInfoData(mEtstdname.getText().toString(), gender, mEtLocation.getText().toString(), mEtAreaname.getText().toString(),
                                            mEtCity.getText().toString(),
                                            mSpinnerStates.getSelectedItem().toString(),
                                            mEtfatherbussiness.getText().toString(), mEtmotherdepartment.getText().toString(),
                                            mEtcontactnumber.getText().toString(), mEtSource.getText().toString(),
                                            StudentQualification,
                                            mEttenthmarks.getText().toString(), mEttenthInterestdCourse.getText().toString(), mEttenthinterestedbranch.getText().toString(),
                                            mSpinnerInterCourse.getSelectedItem().toString(), mEtinterCourse.getText().toString(), mEtintermarks.getText().toString(),
                                            mSpinnerInterInterestedCourse.getSelectedItem().toString(),
                                            mEtinterinterestCourseother.getText().toString(), mEtinterpassyr.getText().toString(),
                                            mEtGradInterestedCourse.getText().toString(),
                                            mEtGraduniversity.getText().toString(),
                                            mEtGradpassyr.getText().toString(), mEtGradDegreemarks.getText().toString(), mEtEntrancetestmarks.getText().toString()
                                            , mSpinnerinterentranceAttempts.getSelectedItem().toString(),
                                            mEtRegistrationTest.getText().toString(), mEtPreviousmarks.getText().toString(), mEtattempeExpectedmarks.getText().toString(),
                                            mEtPname.getText().toString(), mEtmothername.getText().toString(),
                                            mEtpphno.getText().toString(), mEtParentAlt.getText().toString(), FatherOcupation, JobSector, mEtIncomeinLakhs.getText().toString(),
                                            mSpinnerChildren.getSelectedItem().toString(), sibilingsabroad, mSpinnerCountry.getSelectedItem().toString(),
                                            mEtabroad.getText().toString(), mEtSibilingsDuration.getText().toString(), mEtSibilingsfee.getText().toString(),
                                            caste, subcaste, mEtsubcaste.getText().toString(), stateinterest, mEtLocalBudget.getText().toString(), mEtAbroadBudget.getText().toString(),
                                            /*  officevisit,*/ mEtvisittext.getText().toString(), officevisittype, qulification, status, mEtStdaltcontact.getText().toString(), mEtremarks.getText().toString(),
                                            mEtremtext.getText().toString(), mEtremainderdate.getText().toString(), mEtOthers.getText().toString(), userName,
                                            Knowledgeonabroad, Followup,
                                            int_status, mEtvisitmessage.getText().toString(), mEtGovtjobtitle.getText().toString(), intrestedareas, mEtmotherincome.getText().toString()
                                            , mEtotherstatus.getText().toString(), mEttenthEntranceQual.getText().toString(), MotherOcupation, mEtcounsul.getText().toString(),
                                            sibilingsabroad, officevisit, mEtfeedback.getText().toString(), Knowledgeonabroad, mEtfatherbussiness.getText().toString(),
                                            motherjobsector, mEtmotherdepartment.getText().toString());
                                    /* InfoActivity.this.finish();*/
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            mBtnUpload.setVisibility(View.GONE);
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();

                }
            default:
                break;
        }
        mBtnUpload.setVisibility(View.VISIBLE);


    }


    private void loadInfoData(String LeadName, String Gender, String Country, String AreaName, String City,
                              String State, String FatherJobdescription, String MotherJobdesc, String ContactNumber, String Leadfrom, String Qalification, String SSCmarks,
                              String sscfuturecourse, String SSCintrestedBranch, String Interbranch,
                              String mEtinterCourse, String Marks, String InterIntrestedCourse, String mEtinterinterestCourseother,
                              String InterPassyear, String Graduationbranch, String GraduationUniversity,
                              String Graduationpassyear, String GraduationMarks, String Entrancetestmarks, String EntranceTestAttemts,
                              String EntrenceTestName, String PreviousMarks,
                              String ExpectedMarks, String FatherName, String MatherName,
                              String Fathercontact, String Mathercon, String FatherOcupat, String OccSector, String Income,
                              String Siblings, String AnyAbrod,
                              String IntrestedCountry, String SiblingCountry, String SiblingCourse, String SiblingFee, String cast,
                              String SubCast, String mEtsubcaste, String StateOfintrest,
                              String localBudget, String AbroadBudget, /*String VisitStatus,*/ String VisitDate, String VisitType,
                              String CourseType,
                              String CallStatus, String alternativecontact, String FinalFeedBack, String ReminderMessage, String ReminderdateTime,
                              String CallbackDate, String SalesName,
                              String knowledgeonabroad, String FallowUp, int int_status,
                              String VisitMsg, String FatherJob, String Canditintrests, String mIncomeinLaks, String CallOtheStatus, String EntranceQualification,
                              String MotherOccupationDesc, String ConsutancyDescr, String StudyinAbroadDesc, String VisitDescriptiopn, String FeedBackDescription,
                              String AbroadKnowledgeDesc, String FatherOcuDesc, String MotherOccupationSector, String MotherOccupationSectorDesc) {


        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();

        NewPostDataResponse newPostDataResponse = new NewPostDataResponse();
        newPostDataResponse.iD = id;
        //Log.e("id number",""+newPostDataResponse.iD);
        newPostDataResponse.leadName = LeadName;
        newPostDataResponse.gender = Gender;
        newPostDataResponse.country = Country;
        newPostDataResponse.areaName = AreaName;
        newPostDataResponse.city = City;
        newPostDataResponse.state = State;
        newPostDataResponse.fatherJobdescription = FatherJobdescription;
        newPostDataResponse.motherJobdesc = MotherJobdesc;
        newPostDataResponse.contactNumber = ContactNumber;
        newPostDataResponse.leadfrom = Leadfrom;
        newPostDataResponse.qalification = Qalification;

        try {
            if (SSCmarks != null)
                sscmarks = Double.parseDouble(SSCmarks);
            newPostDataResponse.sSCmarks = Double.parseDouble(SSCmarks);
        } catch (NumberFormatException e) {
            sscmarks = Double.valueOf(0);
        }
        newPostDataResponse.sscfuturecourse = sscfuturecourse;
        newPostDataResponse.sSCintrestedBranch = SSCintrestedBranch;
        newPostDataResponse.interbranch = Interbranch;
        newPostDataResponse.interbranchothers = mEtinterCourse;

        try {
            if (Marks != null)
                newPostDataResponse.marks = Double.parseDouble(Marks);
            intermarks = Double.parseDouble(Marks);

        } catch (NumberFormatException e) {
            intermarks = Double.valueOf(0);
        }
        newPostDataResponse.interIntrestedCourse = InterIntrestedCourse;
        newPostDataResponse.interIntrestedCourseothers = mEtinterinterestCourseother;

        try {
            if (InterPassyear != null)
                newPostDataResponse.interPassyear = Integer.parseInt(InterPassyear);
            interpassyr = Integer.parseInt(InterPassyear);
        } catch (NumberFormatException e) {
            interpassyr = Integer.valueOf(0);
        }
        newPostDataResponse.graduationbranch = Graduationbranch;
        newPostDataResponse.graduationUniversity = GraduationUniversity;

        try {
            if (Graduationpassyear != null)
                newPostDataResponse.graduationpassyear = Integer.parseInt(Graduationpassyear);

            gradpassyr = Integer.parseInt(Graduationpassyear);
        } catch (NumberFormatException e) {
            gradpassyr = Integer.valueOf(0);
        }

        try {
            if (GraduationMarks != null)
                newPostDataResponse.graduationMarks = Double.parseDouble(GraduationMarks);
            gradmarks = Double.parseDouble(GraduationMarks);
        } catch (NumberFormatException e) {
            gradmarks = Double.valueOf(0);
        }

        try {
            if (Entrancetestmarks != null)
                newPostDataResponse.entrancetestmarks = Double.parseDouble(Entrancetestmarks);
            entraancemarks = Double.parseDouble(Entrancetestmarks);
        } catch (NumberFormatException e) {
            entraancemarks = Double.valueOf(0);
        }

        newPostDataResponse.entranceTestAttemts = Integer.parseInt(EntranceTestAttemts);
        newPostDataResponse.entrenceTestName = EntrenceTestName;

        try {
            if (PreviousMarks != null)
                newPostDataResponse.previousMarks = Double.valueOf(PreviousMarks);
            previousmarks = Double.parseDouble(PreviousMarks);
        } catch (NumberFormatException e) {
            previousmarks = Double.valueOf(0);
        }

        try {
            if (ExpectedMarks != null)
                newPostDataResponse.expectedMarks = Double.valueOf(ExpectedMarks);
            expectedmarks = Double.parseDouble(ExpectedMarks);
        } catch (NumberFormatException e) {
            expectedmarks = Double.valueOf(0);
        }
        newPostDataResponse.fatherName = FatherName;
        newPostDataResponse.matherName = MatherName;
        newPostDataResponse.fathercontact = Fathercontact;
        newPostDataResponse.mathercon = Mathercon;
        newPostDataResponse.fatherOcupat = FatherOcupat;
        newPostDataResponse.occSector = OccSector;
        newPostDataResponse.income = Income;
        newPostDataResponse.siblings = Integer.parseInt(Siblings);
        newPostDataResponse.anyAbrod = Boolean.valueOf(AnyAbrod);
        newPostDataResponse.intrestedCountry = IntrestedCountry;
        newPostDataResponse.siblingCountry = SiblingCountry;
        newPostDataResponse.siblingCourse = SiblingCourse;
        newPostDataResponse.siblingFee = SiblingFee;
        newPostDataResponse.cast = cast /*+ "-" + SubCast*/;

        newPostDataResponse.subCast = mEtsubcaste;
        newPostDataResponse.stateOfintrest = StateOfintrest;
//       newPostDataResponse.localBudget = Integer.parseInt(localBudget);
        try {
            if (localBudget != null)
                newPostDataResponse.localBudget = Integer.parseInt(localBudget);
            mlocalbudget = Integer.parseInt(localBudget);
        } catch (NumberFormatException e) {
            mlocalbudget = Integer.valueOf(0);
        }

        try {
            if (AbroadBudget != null)
                newPostDataResponse.abroadBudget = Integer.parseInt(AbroadBudget);
            mabroadbudget = Integer.parseInt(AbroadBudget);
        } catch (NumberFormatException e) {
            mabroadbudget = Integer.valueOf(0);
        }


        newPostDataResponse.visitStatus = true;


        //  newPostDataResponse.visitStatus = Boolean.valueOf(VisitStatus);
        newPostDataResponse.visitDate = VisitDate;
        newPostDataResponse.visitType = VisitType;
        newPostDataResponse.courseType = CourseType;
        newPostDataResponse.callStatus = CallStatus;
        newPostDataResponse.alternativecontact = alternativecontact;
        newPostDataResponse.finalFeedBack = FinalFeedBack;
        newPostDataResponse.salesName = userName;
        newPostDataResponse.fallowUp = Boolean.parseBoolean(FallowUp);
        newPostDataResponse.reminderdateTime = ReminderdateTime;
        //Log.e("reminder",""+mEtremainderdate);
        newPostDataResponse.reminderMessage = ReminderMessage;
        newPostDataResponse.callbackDate = CallbackDate;

        newPostDataResponse.visitMsg = VisitMsg;
        newPostDataResponse.fatherJob = FatherJob;
        newPostDataResponse.canditintrests = Canditintrests;
        try {
            if (mIncomeinLaks != null)
                newPostDataResponse.mIncomeinLaks = Integer.parseInt(mIncomeinLaks);
            motherincomeinlakhs = Integer.parseInt(mIncomeinLaks);
        } catch (NumberFormatException e) {
            motherincomeinlakhs = Integer.valueOf(0);
        }

        newPostDataResponse.callOtheStatus = CallOtheStatus;
        newPostDataResponse.callFrom = from;
      /*  newPostDataResponse.reminderStatus = false;
        newPostDataResponse.callBackStatus = false;*/

       /* if (mEtremainderdate.getText().toString()!=null && mEtremainderdate.getText().toString().isEmpty()){

            newPostDataResponse.reminderStatus=true;

        }*/
        //  newPostDataResponse.reminderStatus = false;

        if (chkyes.isChecked()) {
            newPostDataResponse.reminderStatus = true;
        } else {
            newPostDataResponse.reminderStatus = false;
        }

        if (rbcallback.isChecked()) {

            newPostDataResponse.callBackStatus = true;

        } else {
            newPostDataResponse.callBackStatus = false;
        }
        newPostDataResponse.entranceQualification = EntranceQualification;
        newPostDataResponse.motherOccupationDesc = MotherOccupationDesc;
        newPostDataResponse.consutancyDescr = ConsutancyDescr;
        newPostDataResponse.studyinAbroadDesc = StudyinAbroadDesc;
        newPostDataResponse.visitDescriptiopn = VisitDescriptiopn;
        newPostDataResponse.feedBackDescription = FeedBackDescription;
        newPostDataResponse.abroadKnowledgeDesc = AbroadKnowledgeDesc;
        newPostDataResponse.fatherOcuDesc = FatherOcuDesc;
        newPostDataResponse.motherOccupationSector = MotherOccupationSector;
        newPostDataResponse.motherOccupationSectorDesc = MotherOccupationSectorDesc;


        Log.e("New updateInfoResponse", "" + newPostDataResponse.toString());

        //set all remaining fields
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<NewPostDataResponse> Call = apiInterface.getPostDataResponse(newPostDataResponse);
        Call.enqueue(new Callback<NewPostDataResponse>() {
            @Override
            public void onResponse(Call<NewPostDataResponse> call, Response<NewPostDataResponse> response) {

                if (response != null && response.isSuccessful()) {
                    Log.e("upload data", "upload successfullyyy......");
                    if (response.body().recardStatus) {

                        Toast.makeText(NewformActivity.this, "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        //Log.e("upload data","upload successfullyyy......");

                        startActivity(new Intent(NewformActivity.this, DashBoardActivity.class));
                        finish();

                    } else {
                        Toast.makeText(NewformActivity.this, "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(NewformActivity.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<NewPostDataResponse> call, Throwable t) {
                Toast.makeText(NewformActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        /*
      //Call<UpdateInfoResponse> updateInfoResponseCall = apiInterface.getUpdateInfoResponse();
        //*/
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        String item = parent.getItemAtPosition(position).toString();
        String item2 = parent.getItemAtPosition(position).toString();
        if (item.equalsIgnoreCase("Others")) {
            mEtinterCourse.setVisibility(View.VISIBLE);
            mEtinterinterestCourseother.setVisibility(View.VISIBLE);
            mEtCountryOthers.setVisibility(View.VISIBLE);
        } else if (item.equalsIgnoreCase("1")) {

            mEtPreviousmarks.setVisibility(View.GONE);
            mEtattempeExpectedmarks.setVisibility(View.VISIBLE);
            mEtmarksobtained.setVisibility(View.VISIBLE);
        } else if (item.equalsIgnoreCase("2")) {
            mEtPreviousmarks.setVisibility(View.VISIBLE);
            mEtattempeExpectedmarks.setVisibility(View.VISIBLE);
            mEtmarksobtained.setVisibility(View.VISIBLE);
        } else if (item.equalsIgnoreCase("3")) {
            mEtPreviousmarks.setVisibility(View.VISIBLE);
            mEtattempeExpectedmarks.setVisibility(View.VISIBLE);
            mEtmarksobtained.setVisibility(View.VISIBLE);
        } else if (item.equalsIgnoreCase("4")) {
            mEtPreviousmarks.setVisibility(View.VISIBLE);
            mEtattempeExpectedmarks.setVisibility(View.VISIBLE);
            mEtmarksobtained.setVisibility(View.VISIBLE);
        } else {
            mEtPreviousmarks.setVisibility(View.GONE);
            mEtattempeExpectedmarks.setVisibility(View.GONE);
            mEtmarksobtained.setVisibility(View.GONE);
            mEtattempeExpectedmarks.setVisibility(View.GONE);
            mEtinterCourse.setVisibility(View.GONE);
            mEtinterinterestCourseother.setVisibility(View.GONE);
        }


        // Showing selected spinner item
        // Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onSupportNavigateUp() {

        //onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(NewformActivity.this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewformActivity.this.finish();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(NewformActivity.this,
                            Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {


                        Toast.makeText(this, "Permission granted...", Toast.LENGTH_SHORT).show();
                          /*  TextView textView = findViewById(R.id.textincoming);

                           textView.setText(getCallDetails(context));*/

                        mEtcontactnumber.setText(getCallDetails(context).get(0).getNumber());

                        Log.e("numbet", "" + getCallDetails(context).get(0).getNumber());

                       /* incomingcallAdapter = new IncomingcallAdapter(getApplicationContext(),getCallDetails(context));
                        mRecycler.setAdapter(incomingcallAdapter);*/
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
            list.add(new IncomingResponse(phnumber, calldate, calltype, dir));


        }

        managecursor.close();
        /* return sb.toString();*/
        return list;

    }


    @Override
    public void onResume() {

        mEtcontactnumber.setText(getCallDetails(context).get(0).getNumber());

        super.onResume();
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        NewformActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}
