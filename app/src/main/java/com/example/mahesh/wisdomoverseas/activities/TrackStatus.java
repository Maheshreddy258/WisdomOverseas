package com.example.mahesh.wisdomoverseas.activities;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.fragments.Completed;
import com.example.mahesh.wisdomoverseas.fragments.NewlyAdded;
import com.example.mahesh.wisdomoverseas.fragments.Processing;
import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.Constants;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrackStatus extends AppCompatActivity implements Completed.OnFragmentInteractionListener,
        NewlyAdded.OnFragmentInteractionListener, Processing.OnFragmentInteractionListener {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    public TextView TextStatus;
    String TextStatus2="";
    public static final int COLOR_RED_500 = 0xFFF44336;

    SharedPref sharedPref;
    String userName, city1, student1;
    boolean Status;
    public static List<NewTodayCallResponse> newlyAddedList,processingList,completedList;
    //public static List<TodayCallResponse> newlyAddedList,processingList,completedList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_status);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        newlyAddedList=new ArrayList<>();
        processingList=new ArrayList<>();
        completedList=new ArrayList<>();
        loadTrackStatusData();
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        // TextStatus = findViewById(R.id.tvupdate);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.white));
        tabLayout.setTabTextColors(getResources().getColor(R.color.black),getResources().getColor(R.color.black));

    }
    private void loadTrackStatusData() {

        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();
        Status = true;


        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
            // Todaycalls_progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewTodayCallResponse>> call = apiInterface.getTodayLeadsResponse(userName);
            call.enqueue(new Callback<List<NewTodayCallResponse>>() {
                @Override
                public void onResponse(Call<List<NewTodayCallResponse>> call, Response<List<NewTodayCallResponse>> response) {

                    if (response.isSuccessful() && response != null) {
                        List<NewTodayCallResponse> todayCallResponseList = new ArrayList<>();
                        todayCallResponseList = response.body();
                        if (todayCallResponseList != null && todayCallResponseList.size() > 0) {
                           for(NewTodayCallResponse todayCallResponse:todayCallResponseList){
                               if(todayCallResponse.leadInfoStatus!=null && !todayCallResponse.leadInfoStatus.isEmpty()){
                                   if(todayCallResponse.leadInfoStatus.equalsIgnoreCase(Constants.PENDING)){

                                       newlyAddedList.add(todayCallResponse);

                                   }else if(todayCallResponse.leadInfoStatus.equalsIgnoreCase(Constants.PROCESSING)){

                                       processingList.add(todayCallResponse);
                                   }else if(todayCallResponse.leadInfoStatus.equalsIgnoreCase(Constants.JOINED)){
                                       completedList.add(todayCallResponse);

                                   }
                               }

                           }
                           setupViewPager(viewPager);

                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Failed To get the Calls List", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<List<NewTodayCallResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());


        adapter.addFragment(new NewlyAdded(), "NEWLY ADDED",newlyAddedList);
        adapter.addFragment(new Processing(), "Processing",processingList);
        adapter.addFragment(new Completed(), "COMPLETED",completedList);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class ViewPagerAdapter extends FragmentPagerAdapter  {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title, List<NewTodayCallResponse> todayCallResponses) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
}
