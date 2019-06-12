package com.example.mahesh.wisdomoverseas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;
import com.example.mahesh.wisdomoverseas.models.responses.TodayCallResponse;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();


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

    public void addFrag(Fragment fragment,List<MyReportResponse> myReportResponses) {
        mFragmentList.add(fragment);

    }




}