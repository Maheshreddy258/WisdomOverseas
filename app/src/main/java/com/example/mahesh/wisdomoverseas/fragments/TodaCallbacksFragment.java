package com.example.mahesh.wisdomoverseas.fragments;

import android.content.Context;
import android.net.Uri;
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
import com.example.mahesh.wisdomoverseas.adapters.CallbackAdapter;
import com.example.mahesh.wisdomoverseas.adapters.TodayCallsAdapter;
import com.example.mahesh.wisdomoverseas.adapters.TodaycallBacksAdapter;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewCallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodaycallBacksResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.Constants;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TodaCallbacksFragment extends Fragment {

    RecyclerView mRecycle;
    //TodayCallsAdapter todayCallsAdapter;
    TodaycallBacksAdapter todaycallBacksAdapter;
    ArrayList leadsResponseslist;
    SharedPref sharedPref;
    String userName,city1,student1;
    // List<TodayCallResponse> todayCallResponses;
    List<NewTodaycallBacksResponse> todaycallBacksResponses;
    ProgressBar Todaycalls_progressBar;
    Boolean Status,infoStatus;

    List<NewTodaycallBacksResponse> callBacksResponseList = new ArrayList<>();



    public TodaCallbacksFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_toda_callbacks, container, false);

        Todaycalls_progressBar = view.findViewById(R.id.todaycalls_progressBar);
        mRecycle = (RecyclerView) view.findViewById(R.id.todaycallbacksreclyclerview);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycle.setItemAnimator(new DefaultItemAnimator());

        prepareCallBackData();

        return view;
    }

    private void prepareCallBackData() {



        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();
        Status = true;
        infoStatus=true;
        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            Todaycalls_progressBar.setVisibility(View.VISIBLE);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewTodaycallBacksResponse>> call = apiInterface.getTodayCallBackResponse(userName);
            call.enqueue(new Callback<List<NewTodaycallBacksResponse>>() {
                @Override
                public void onResponse(Call<List<NewTodaycallBacksResponse>> call, Response<List<NewTodaycallBacksResponse>> response) {
                    Todaycalls_progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful() && response != null) {


                        callBacksResponseList = response.body();
                       // Log.e("todaycalls","todaycallsssss");

                        if (callBacksResponseList != null && callBacksResponseList.size() > 0) {

                            Date calendar = Calendar.getInstance().getTime();
                            SimpleDateFormat dateFormatC = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                            String strDateC = dateFormatC.format(calendar);
                            List<NewTodaycallBacksResponse> callBacksResponseList1 = new ArrayList<>();


                            for (NewTodaycallBacksResponse todaycallBacksResponse : callBacksResponseList) {
                                Log.e("today calls id", "" + todaycallBacksResponse.iD);
                                //convert the reminder date string to date format
                                if (todaycallBacksResponse.callbackDate!= null && !todaycallBacksResponse.callbackDate.isEmpty()) {
                                    Date callbackDate = null;
                                    try {
                                        callbackDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(todaycallBacksResponse.callbackDate);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                  /* Calendar reminderCalendar = Calendar.getInstance();
                                   reminderCalendar.setTime(reminderDate);*/
                                    Date dateConversion = Calendar.getInstance().getTime();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                                    String strDate = dateFormat.format(callbackDate);


                                    Log.e("calender date", "" + strDateC);
                                    Log.e("callbackdate", "" + strDate);

                                    if (strDate != null && strDateC != null && strDateC.equals(strDate)) {
                                        callBacksResponseList1.add(todaycallBacksResponse);
                                    }
                                }
                            }
                            if (callBacksResponseList1 != null && callBacksResponseList1.size() > 0) {
                                todaycallBacksAdapter = new TodaycallBacksAdapter(getActivity(), callBacksResponseList1);
                                mRecycle.setAdapter(todaycallBacksAdapter);
                            }

                           /* Log.e("todaycalls","todaycallsssss");
                           // Toast.makeText(getActivity(), "Today CallBacks....", Toast.LENGTH_SHORT).show();
                            todaycallBacksAdapter = new TodaycallBacksAdapter(getActivity(), callBacksResponseList);
                            mRecycle.setAdapter(todaycallBacksAdapter);*/
                        }else{
                            Toast.makeText(getActivity(), "No Today CallBacks Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Failed To get the Today calls List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NewTodaycallBacksResponse>> call, Throwable t) {
                    Todaycalls_progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }

    }

    @Override
    public void onResume() {
        prepareCallBackData();

        super.onResume();
    }



    public void onButtonPressed(Uri uri) {

    }


}
