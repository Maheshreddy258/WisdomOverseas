package com.example.mahesh.wisdomoverseas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.AllCallsAdapter;
import com.example.mahesh.wisdomoverseas.adapters.SearchAdapter;
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

public class SearchActivity extends AppCompatActivity implements android.widget.SearchView.OnQueryTextListener {

    Toolbar toolbar;
    RecyclerView mRecycleall;
    AllCallsAdapter allCallsAdapter;
    SearchAdapter searchAdapter;
    // List<AllCallsResponse> allCallsResponses;
    List<NewAllCallsResponse> allCallsResponses;
    ArrayList leadsResponseslist;
    SharedPref sharedPref;
    String userName,city1,student1;
    ProgressBar Allcalls_progressBar;
    android.widget.SearchView searchView;
    Boolean Status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);




        mRecycleall =(RecyclerView)findViewById(R.id.reclyclerviewall);
        mRecycleall.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycleall.setHasFixedSize(true);
        mRecycleall.setItemAnimator(new DefaultItemAnimator());

        prepareData();
    }

    private void prepareData() {


        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();
        Status = true;

        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
//            Allcalls_progressBar.setVisibility(View.VISIBLE);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewAllCallsResponse>> call = apiInterface.getAllCallsResponse(userName);
            call.enqueue(new Callback<List<NewAllCallsResponse>>() {
                @Override
                public void onResponse(Call<List<NewAllCallsResponse>> call, Response<List<NewAllCallsResponse>> response) {

                    //Allcalls_progressBar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response != null) {
                        List<NewAllCallsResponse> allCallsResponseList = new ArrayList<>();
                        allCallsResponseList = response.body();
                        if (allCallsResponseList != null && allCallsResponseList.size() > 0) {

                            Log.e("allcallscount",""+allCallsResponseList.size());
                            allCallsAdapter = new AllCallsAdapter(getApplicationContext(),allCallsResponseList);
                            mRecycleall.setAdapter(allCallsAdapter);
                        }else{
                            Toast.makeText(SearchActivity.this, "No AllCalls found..", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SearchActivity.this, "Failed To get the all calls List", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NewAllCallsResponse>> call, Throwable t) {
                    //Allcalls_progressBar.setVisibility(View.GONE);
                    Toast.makeText(SearchActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        String suggestWord = getIntent().getStringExtra("Phonenumber");
        searchView= (android.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);
        /*searchView.setQuery(suggestWord, true);*/
        /*searchView.clearFocus();*/
        return true;

    }


/*  public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search,menu);

        MenuItem menuItem = menu.findItem(R.id.search);
        searchView= (android.widget.SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);


    }*/

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {


        allCallsAdapter.getFilter().filter(newText);
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
}
