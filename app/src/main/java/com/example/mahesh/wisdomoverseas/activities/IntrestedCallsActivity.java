package com.example.mahesh.wisdomoverseas.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.InterestedcallAdapter;
import com.example.mahesh.wisdomoverseas.adapters.PendingReminderAdapter;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewInterestedCallResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPendingRemindersResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IntrestedCallsActivity extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView mRecycler;

    List<NewInterestedCallResponse> interestedCallResponsesList;
    SharedPref sharedPref;
    String userName,city1,student1;
    InterestedcallAdapter interestedcallAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intrested_calls);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecycler = (RecyclerView)findViewById(R.id.intrestedcallreclyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());


        preparedIntrestedcallData();
    }

    private void preparedIntrestedcallData() {

        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();

        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewInterestedCallResponse>> call = apiInterface.getInterestedCalls(userName);
            call.enqueue(new Callback<List<NewInterestedCallResponse>>() {
                @Override
                public void onResponse(Call<List<NewInterestedCallResponse>> call, Response<List<NewInterestedCallResponse>> response) {
                    if (response.isSuccessful() && response != null) {

                        List<NewInterestedCallResponse> interestedCallResponsesList = new ArrayList<>();
                        interestedCallResponsesList = response.body();
                        if (interestedCallResponsesList != null && interestedCallResponsesList.size() > 0) {
                            Log.e("response","response");

                            //Toast.makeText(IntrestedCallsActivity.this, "Hellooo.....", Toast.LENGTH_SHORT).show();
                            interestedcallAdapter     = new InterestedcallAdapter(getApplicationContext(), interestedCallResponsesList);
                            mRecycler.setAdapter(interestedcallAdapter);
                        }else {
                            Toast.makeText(getApplicationContext(), "No Intrestedcalls Found", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText( getApplicationContext(), "Failed to get the intrested Calls Data", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<NewInterestedCallResponse>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
}
