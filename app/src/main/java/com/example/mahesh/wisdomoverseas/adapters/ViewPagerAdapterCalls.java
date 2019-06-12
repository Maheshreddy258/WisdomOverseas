package com.example.mahesh.wisdomoverseas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterCalls extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();


    public ViewPagerAdapterCalls(FragmentManager manager) {
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

    public void addFrag(Fragment fragment) {
        mFragmentList.add(fragment);

    }
}
