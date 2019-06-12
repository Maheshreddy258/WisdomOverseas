package com.example.mahesh.wisdomoverseas.activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.TodayCallsAdapter;
import com.example.mahesh.wisdomoverseas.adapters.ViewPagerAdapter;
import com.example.mahesh.wisdomoverseas.adapters.ViewPagerAdapterCalls;
import com.example.mahesh.wisdomoverseas.fragments.AllCallsFragment;
import com.example.mahesh.wisdomoverseas.fragments.Todaycallsfragment;

public class TodayCalls extends AppCompatActivity {


    Toolbar toolbar;
    ViewPager viewPager;
    TabLayout tabLayout;
    Context mContext;
    TodayCallsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_calls);

        toolbar = findViewById(R.id.toolbar);
       // toolbar.setTitle("Today Calls");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Today Calls");

        setupViewPager();

    }

    private void setupViewPager() {

        ViewPagerAdapterCalls viewPagerAdapterCalls = new ViewPagerAdapterCalls(getSupportFragmentManager());
        viewPagerAdapterCalls.addFrag(new Todaycallsfragment());
        viewPagerAdapterCalls.addFrag(new AllCallsFragment());
        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapterCalls);


        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.white));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F2B124"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        //tabLayout.setTabTextColors(Color.parseColor("#0d0d0d"), Color.parseColor("#0d0d0d"));

        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.black));

        tabLayout.getTabAt(0).setText("Today Calls");
        tabLayout.getTabAt(1).setText("All Calls");




    }



    /*for back button presses*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }





}
