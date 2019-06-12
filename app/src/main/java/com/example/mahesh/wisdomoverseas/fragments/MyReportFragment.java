package com.example.mahesh.wisdomoverseas.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.RemainderActivity;
import com.example.mahesh.wisdomoverseas.activities.Report;
import com.example.mahesh.wisdomoverseas.adapters.MyReportAdapter;
import com.example.mahesh.wisdomoverseas.adapters.TodayCallsAdapter;
import com.example.mahesh.wisdomoverseas.models.requests.ReportRequest;
import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;
import com.example.mahesh.wisdomoverseas.models.responses.ReportResponse;
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


public class MyReportFragment extends Fragment implements View.OnClickListener {


    int id, calls, rem, year;
    String msgs, uname, month, subject, fileupload, createddate;
    boolean status;
    ReportRequest reportRequest;
    private RecyclerView mMyreportrecycle;
    SharedPref sharedPref;
    String userName, city1, student1;
    List<MyReportResponse> myReportResponseList;
    MyReportAdapter myReportAdapter;
    //private List<ReportResponse> myReportresponselist;
    private FloatingActionButton fab;

    //private List<String> snames;


    public MyReportFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_report, container, false);
        // myReportresponselist = new ArrayList<>();

        mMyreportrecycle = (RecyclerView) view.findViewById(R.id.myreportreclyclerview);
        mMyreportrecycle.setHasFixedSize(true);
        mMyreportrecycle.setNestedScrollingEnabled(false);
        mMyreportrecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(this);
        //sharedPref=new SharedPref(getActivity());

       /* snames =new ArrayList<>();
        snames.add("pvn");
        snames.add("pvn2");
        snames.add("pvn3");
        snames.add("pvn4");
        snames.add("pvn5");*/




        return view;
    }


    private void loadRecyclerViewData() {

        //mMyreportrecycle.setVisibility(View.GONE);
        // String username = sharedPref.getUserName();
        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();

        /*ReportRequest reportRequest = new ReportRequest();
        reportRequest.createdDate = createddate;
        reportRequest.callsDone = calls;
        reportRequest.reminders = rem;
        reportRequest.messages = msgs;
*/
        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
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
                            myReportAdapter = new MyReportAdapter(getActivity(), myReportResponseList);
                            mMyreportrecycle.setAdapter(myReportAdapter);
                        } else {
                            Toast.makeText(getActivity(), "No reports found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed To get the Reports List", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<MyReportResponse>> call, Throwable t) {

                }
            });
        }

    }

    @Override
    public void onResume() {
        //prepareData();
        loadRecyclerViewData();
        super.onResume();
    }

    void prepareData() {
        for (int i = 0; i < 10; i++) {
            ReportResponse reportResponse = new ReportResponse();
            reportResponse.callsDone = i + 1;
            reportResponse.reminders = i + 2;
            reportResponse.createdDate = (i + 1) + " Feb " + " 2019";
            //myReportresponselist.add(reportResponse);
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                startActivity(new Intent(getActivity(), RemainderActivity.class));
                //Toast.makeText(getActivity(), "Clicked on floating action button", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }
}
