package com.example.mahesh.wisdomoverseas.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.TrackStatus;
import com.example.mahesh.wisdomoverseas.adapters.CallsAdapter;
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


public class Completed extends Fragment {

    CallsAdapter callsAdapter;
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<String> snames;
    List<NewTodayCallResponse> todayCallResponses;
    ProgressBar Todaycalls_progressBar;
    Boolean Status;
    SharedPref sharedPref;
    String userName,city1,student1;




    private OnFragmentInteractionListener mListener;

    public Completed() {
        // Required empty public constructor
    }

    public static Completed newInstance(String param1, String param2) {
        Completed fragment = new Completed();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_completed, container, false);
        recyclerView=  view.findViewById(R.id.completedrecycleview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        List<NewTodayCallResponse> todayCallResponses= TrackStatus.completedList;
        Log.e("todayCallResponses",""+todayCallResponses);
        if(todayCallResponses!=null && todayCallResponses.size()>0){
            callsAdapter = new CallsAdapter(getActivity(), todayCallResponses);
            recyclerView.setAdapter(callsAdapter);
        }

        /*snames= new ArrayList<>();
        snames.add("pavan");
        snames.add("kiran ");
        snames.add("nagaraju");
        snames.add("james");
        snames.add("mahesh");
        snames.add("sravvan");
        snames.add("will smith");
        snames.add("john");
        CallsAdapter callsAdapter= new CallsAdapter(getActivity(),snames);
       *//* LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);*//*
        recyclerView.setAdapter(callsAdapter);*/

       // loadTrackStatusData();
        return view;
    }

    private void loadTrackStatusData() {
        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();
        Status = true;


        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            //Todaycalls_progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewTodayCallResponse>> call = apiInterface.getTodayLeadsResponse(userName);
            call.enqueue(new Callback<List<NewTodayCallResponse>>() {
                @Override
                public void onResponse(Call<List<NewTodayCallResponse>> call, Response<List<NewTodayCallResponse>> response) {

                    if (response.isSuccessful() && response != null) {
                        List<NewTodayCallResponse> todayCallResponseList = new ArrayList<>();
                        todayCallResponseList = response.body();
                        if (todayCallResponseList != null && todayCallResponseList.size() > 0) {
                            callsAdapter = new CallsAdapter(getActivity(), todayCallResponseList);
                            recyclerView.setAdapter(callsAdapter);
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed To get the Today calls List", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onFailure(Call<List<NewTodayCallResponse>> call, Throwable t) {
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }


    }
















    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
