package com.example.mahesh.wisdomoverseas.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.TodayCallsAdapter;

import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;
import com.facebook.shimmer.ShimmerFrameLayout;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Todaycallsfragment extends Fragment {

    RecyclerView mRecycle;
    TodayCallsAdapter todayCallsAdapter;
     ArrayList leadsResponseslist;
    SharedPref sharedPref;
    private ShimmerFrameLayout shimmerFrameLayout;
    String userName,city1,student1;
   // List<TodayCallResponse> todayCallResponses;
    List<NewTodayCallResponse> todayCallResponses;
    ProgressBar Todaycalls_progressBar;
    Boolean Status,infoStatus;





    public Todaycallsfragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_todaycallsfragment, container, false);



        Todaycalls_progressBar = view.findViewById(R.id.todaycalls_progressBar);
        mRecycle = (RecyclerView) view.findViewById(R.id.reclyclerview);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycle.setItemAnimator(new DefaultItemAnimator());

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = df.format(c);
        shimmerFrameLayout = view.findViewById(R.id.shimmerlayout);

        prepareData();

        return view;
    }
    void prepareData() {
        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();
        Status = true;
        infoStatus=true;

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            Todaycalls_progressBar.setVisibility(View.VISIBLE);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewTodayCallResponse>> call = apiInterface.getTodayLeadsResponse(userName);
            call.enqueue(new Callback<List<NewTodayCallResponse>>() {
                @Override
                public void onResponse(Call<List<NewTodayCallResponse>> call, Response<List<NewTodayCallResponse>> response) {
                    Todaycalls_progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful() && response != null) {
                        List<NewTodayCallResponse> todayCallResponseList = new ArrayList<>();
                        todayCallResponseList = response.body();

                        if (todayCallResponseList != null && todayCallResponseList.size() > 0) {
                            for(NewTodayCallResponse todayCallResponse:todayCallResponseList){
                                Log.e("todayId:",""+todayCallResponse.iD);
                            }

                            todayCallsAdapter = new TodayCallsAdapter(getActivity(), todayCallResponseList);

                            shimmerFrameLayout.stopShimmer();
                            shimmerFrameLayout.setVisibility(View.GONE);
                            mRecycle.setVisibility(View.VISIBLE);
                            mRecycle.setAdapter(todayCallsAdapter);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed To get the Today calls List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NewTodayCallResponse>> call, Throwable t) {
                    Todaycalls_progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public void onResume() {
        shimmerFrameLayout.startShimmer();
        prepareData();

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        shimmerFrameLayout.stopShimmer();
    }
}







