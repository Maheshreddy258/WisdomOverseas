package com.example.mahesh.wisdomoverseas.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.MyReportAdapter;
import com.example.mahesh.wisdomoverseas.adapters.ViewPagerAdapter;
import com.example.mahesh.wisdomoverseas.fragments.AllCallsFragment;
import com.example.mahesh.wisdomoverseas.fragments.MyReportFragment;
import com.example.mahesh.wisdomoverseas.fragments.ReportFragment;
import com.example.mahesh.wisdomoverseas.fragments.Todaycallsfragment;
import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;
import com.example.mahesh.wisdomoverseas.models.responses.TodayCallResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Report extends AppCompatActivity /*implements MyReportFragment.OnFragmentInteractionListener*/ {

    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    SharedPref sharedPref;
    String userName, city1, student1;
    boolean Status;
    public static List<MyReportResponse> report,myreport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        report=new ArrayList<>();
        myreport=new ArrayList<>();

//        String count = String.valueOf(Integer.parseInt(getIntent().getStringExtra("count")));
       // loadReportData();
        setupViewPager(viewPager);
    }



    private void loadReportData() {

        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();
        Status = true;

        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
            // Todaycalls_progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<MyReportResponse>> call = apiInterface.getMyReportResponse(userName);
            call.enqueue(new Callback<List<MyReportResponse>>() {
                @Override
                public void onResponse(Call<List<MyReportResponse>> call, Response<List<MyReportResponse>> response) {
                    if (response.isSuccessful() && response != null) {
                        List<MyReportResponse> myReportResponseList = new ArrayList<>();
                        myReportResponseList = response.body();
                        if (myReportResponseList != null && myReportResponseList.size() > 0) {
                            for(MyReportResponse myReportResponse:myReportResponseList){
                                if(myReportResponse.recordStatus){
                                    myreport.add(myReportResponse);

                                }else if (myReportResponse.recordStatus) {

                                    report.add(myReportResponse);
                                }

                            }
                            setupViewPager(viewPager);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed To get the Today calls List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<MyReportResponse>> call, Throwable t) {


                }
            });



        }


    }

    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFrag(new ReportFragment(),report);
        viewPagerAdapter.addFrag(new MyReportFragment(),myreport);
        this.viewPager = findViewById(R.id.viewpager);
        this.viewPager.setAdapter(viewPagerAdapter);


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(this.viewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
        tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.black));

        tabLayout.getTabAt(0).setText("Todays Report");
        tabLayout.getTabAt(1).setText("All Reports");
    }





    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }

  /*  @Override
    public void onFragmentInteraction(Uri uri) {

    }*/
}
