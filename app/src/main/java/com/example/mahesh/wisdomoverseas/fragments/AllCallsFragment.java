package com.example.mahesh.wisdomoverseas.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.AllCallsAdapter;
import com.example.mahesh.wisdomoverseas.adapters.TodayCallsAdapter;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewAllCallsResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AllCallsFragment extends Fragment implements android.widget.SearchView.OnQueryTextListener {



    RecyclerView mRecycleall;
    AllCallsAdapter allCallsAdapter;
   // List<AllCallsResponse> allCallsResponses;
   List<NewAllCallsResponse> allCallsResponses;
    ArrayList leadsResponseslist;
    SharedPref sharedPref;
    String userName,city1,student1;
    ProgressBar Allcalls_progressBar;
    android.widget.SearchView searchView;
    Boolean Status;
    TodayCallsAdapter todayCallsAdapter;
    public AllCallsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_all_calls, container, false);
        Allcalls_progressBar = view.findViewById(R.id.allcalls_progressBar);
        mRecycleall =(RecyclerView)view.findViewById(R.id.reclyclerviewall);
        mRecycleall.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecycleall.setHasFixedSize(true);
        mRecycleall.setItemAnimator(new DefaultItemAnimator());

        prepareData();

        return view;
    }


    private void prepareData() {
        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();
        Status = true;

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
             Allcalls_progressBar.setVisibility(View.VISIBLE);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewAllCallsResponse>> call = apiInterface.getAllCallsResponse(userName);
            call.enqueue(new Callback<List<NewAllCallsResponse>>() {
                @Override
                public void onResponse(Call<List<NewAllCallsResponse>> call, Response<List<NewAllCallsResponse>> response) {

                    Allcalls_progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response != null) {
                        List<NewAllCallsResponse> allCallsResponseList = new ArrayList<>();
                        allCallsResponseList = response.body();
                        if (allCallsResponseList != null && allCallsResponseList.size() > 0) {

                            Log.e("allcallscount",""+allCallsResponseList.size());
                            allCallsAdapter = new AllCallsAdapter(getActivity(),allCallsResponseList);
                            mRecycleall.setAdapter(allCallsAdapter);
                        }else{
                            Toast.makeText(getActivity(), "No AllCalls found..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Failed To get the all calls List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NewAllCallsResponse>> call, Throwable t) {
                    Allcalls_progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }


  /*  void prepareData(){
        List<TodayCallResponse> todayCallResponseList=new ArrayList<>();
        for(int i=0;i<10;i++){
            TodayCallResponse todayCallResponse=new TodayCallResponse();
            todayCallResponse.city="City"+(i+1);
            todayCallResponse.student="Student"+(i+1);
            todayCallResponse.iD=(i+1);
            todayCallResponseList.add(todayCallResponse);
        }
      allCallsAdapter=new AllCallsAdapter(getActivity(),todayCallResponseList);
        mRecycleall.setAdapter(allCallsAdapter);
    }*/


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView= (android.widget.SearchView) menuItem.getActionView();
         searchView.setOnQueryTextListener(this);


    }

   /* @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      if (item.getItemId()==R.id.search){

          Toast.makeText(getActivity(), "Clicked on search...", Toast.LENGTH_SHORT).show();
      }
      return true;
    }*/

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

         allCallsAdapter.getFilter().filter(newText);


        return false;
    }
}
