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
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewCallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CallBackFragment extends Fragment {

    RecyclerView mRecycle;
    //TodayCallsAdapter todayCallsAdapter;
    CallbackAdapter callbackAdapter;
    ArrayList leadsResponseslist;
    SharedPref sharedPref;
    String userName,city1,student1;
    // List<TodayCallResponse> todayCallResponses;
    List<NewCallBacksResponse> callBacksResponseList;
    ProgressBar Todaycalls_progressBar;
    Boolean Status,infoStatus;

    public CallBackFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_call_back, container, false);
        Todaycalls_progressBar = view.findViewById(R.id.todaycalls_progressBar);
        mRecycle = (RecyclerView) view.findViewById(R.id.Callbackreclyclerview);
        mRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycle.setItemAnimator(new DefaultItemAnimator());

        prepareCallBackData();

        return  view;
    }

    private void prepareCallBackData() {


        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();
        Status = true;
        infoStatus=true;
        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            Todaycalls_progressBar.setVisibility(View.VISIBLE);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewCallBacksResponse>> call = apiInterface.getCallBackCalls(userName);
            call.enqueue(new Callback<List<NewCallBacksResponse>>() {
                @Override
                public void onResponse(Call<List<NewCallBacksResponse>> call, Response<List<NewCallBacksResponse>> response) {
                    Todaycalls_progressBar.setVisibility(View.GONE);

                    if (response.isSuccessful() && response != null) {
                        List<NewCallBacksResponse> callBacksResponseList = new ArrayList<>();
                        callBacksResponseList = response.body();

                        if (callBacksResponseList != null && callBacksResponseList.size() > 0) {
                            for(NewCallBacksResponse todayCallResponse:callBacksResponseList){
                                Log.e("todayId:",""+todayCallResponse.iD);
                            }
                           // Toast.makeText(getActivity(), "CallBacks....", Toast.LENGTH_SHORT).show();
                            callbackAdapter = new CallbackAdapter(getActivity(), callBacksResponseList);
                            mRecycle.setAdapter(callbackAdapter);
                        }else {
                            Toast.makeText(getActivity(), "No CallBacks Found", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Failed To get the CallBacks List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NewCallBacksResponse>> call, Throwable t) {
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


}
