package com.example.mahesh.wisdomoverseas.activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mahesh.wisdomoverseas.adapters.CustomViewPager;
import com.example.mahesh.wisdomoverseas.fragments.FamilyInfoFragment;
import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.fragments.StudentInfoFragment;
import com.example.mahesh.wisdomoverseas.adapters.ViewPagerAdapterinfo;
import com.example.mahesh.wisdomoverseas.fragments.BasicInfofragment;
import com.example.mahesh.wisdomoverseas.models.responses.TodayCallResponse;

import java.util.List;

public class NewInfoActivity extends AppCompatActivity {

    Toolbar toolbar;
   //ViewPager viewPager;
    Context context;
    CustomViewPager viewPager;
    TabLayout tabLayout;
    public static String studentName,fatherName,Id,callfrom;
   //public static String Id;
    List<TodayCallResponse> todayCallResponseList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_info);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

         Id = String.valueOf(getIntent().getIntExtra("Id",0));
         studentName = getIntent().getStringExtra("studentName");
        fatherName = getIntent().getStringExtra("fatherName");
        callfrom = getIntent().getStringExtra("callfrom");

        Log.e("Id new info",""+Id);
        Log.e("studentName", ""+studentName);
        Log.e("fatherName", ""+fatherName);

        setupViewPager();
    }

    private void genId(int i){

      final int Id = todayCallResponseList.get(i).iD;

    }

    private void setupViewPager() {

        ViewPagerAdapterinfo viewPagerAdapterinfo = new ViewPagerAdapterinfo(getSupportFragmentManager());
        viewPagerAdapterinfo.addFrag(new BasicInfofragment());
        viewPagerAdapterinfo.addFrag(new StudentInfoFragment());
        viewPagerAdapterinfo.addFrag(new FamilyInfoFragment());

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(viewPagerAdapterinfo);
        viewPager.setPagingEnabled(false);


        tabLayout = findViewById(R.id.tabs);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.white));

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#F2B124"));
        tabLayout.setSelectedTabIndicatorHeight((int) (5 * getResources().getDisplayMetrics().density));
        //tabLayout.setTabTextColors(Color.parseColor("#0d0d0d"), Color.parseColor("#0d0d0d"));

        tabLayout.setTabTextColors(getResources().getColor(R.color.black), getResources().getColor(R.color.black));

        tabLayout.getTabAt(0).setText("Basic Info");
        tabLayout.getTabAt(1).setText("Student Info");
        tabLayout.getTabAt(2).setText("Family Info");

    }

    public void onTabSelected(TabLayout.Tab tab) {


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
