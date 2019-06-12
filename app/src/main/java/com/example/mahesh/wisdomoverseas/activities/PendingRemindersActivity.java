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
import com.example.mahesh.wisdomoverseas.adapters.PendingReminderAdapter;
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

public class PendingRemindersActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView mRecycler;
     PendingReminderAdapter pendingReminderAdapter;
    List<NewPendingRemindersResponse> pendingRemindersResponseList;
    SharedPref sharedPref;
    String userName,city1,student1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_reminders);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecycler = (RecyclerView)findViewById(R.id.pendingremindersreclyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());

        preparePendingRemindersData();


    }

    private void preparePendingRemindersData() {

        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();

        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewPendingRemindersResponse>> call = apiInterface.getPendingReminderCalls(userName);
            call.enqueue(new Callback<List<NewPendingRemindersResponse>>() {
                @Override
                public void onResponse(Call<List<NewPendingRemindersResponse>> call, Response<List<NewPendingRemindersResponse>> response) {
                    if (response.isSuccessful() && response != null) {

                        List<NewPendingRemindersResponse> pendingRemindersResponseList = new ArrayList<>();
                        pendingRemindersResponseList = response.body();
                        if (pendingRemindersResponseList != null && pendingRemindersResponseList.size() > 0) {
                            Log.e("response","response");

                            pendingReminderAdapter = new PendingReminderAdapter(getApplicationContext(), pendingRemindersResponseList);
                            mRecycler.setAdapter(pendingReminderAdapter);

                        }else {
                            Toast.makeText(getApplicationContext(), "No Pending Reminders  Found", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(PendingRemindersActivity.this, "Failed to get the Pending Reminders Data..", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<List<NewPendingRemindersResponse>> call, Throwable t) {
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
