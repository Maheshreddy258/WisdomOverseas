package com.example.mahesh.wisdomoverseas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapterinfo extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private boolean swipeEnabled;


    public ViewPagerAdapterinfo(FragmentManager fm) {
        super(fm);
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


   /* public void addFrag(StudentInfoFragment studentInfoFragment) {
    }

    public void addFrag(FamilyInfoFragment basicInfofragment) {
    }

    public void addFrag(BasicInfofragment basicInfofragment) {
    }*/
}