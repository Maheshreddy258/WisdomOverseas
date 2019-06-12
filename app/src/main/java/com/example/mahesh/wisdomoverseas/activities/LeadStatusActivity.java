package com.example.mahesh.wisdomoverseas.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewUpdateLeadResponse;
import com.example.mahesh.wisdomoverseas.models.responses.HistoryCalls;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeadStatusActivity extends AppCompatActivity {


    TextView mTextView;
    RadioGroup mRadiogrplead;
    RadioButton mClose,mProcess;
    Button mBtnStatus;
    Toolbar toolbar;
    int leadstatus ,id=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead_status);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        mTextView = findViewById(R.id.txtlead);
        mRadiogrplead = findViewById(R.id.radioGrouplead);
        mClose = findViewById(R.id.close);
        mProcess = findViewById(R.id.process);
        mBtnStatus = findViewById(R.id.btnstatus);
        mBtnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateStudentStatus();
            }
        });

        id = getIntent().getIntExtra("Id", 0);
        mRadiogrplead.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.close) {
                    leadstatus = 1;


                } else if (checkedId == R.id.process) {

                    leadstatus = 3;


                }
            }
        });


    }

    private void UpdateStudentStatus() {
        Log.e("id",""+id);
        Log.e("status",""+leadstatus);

        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<NewUpdateLeadResponse> call = apiInterface.getUpdateLead(id,leadstatus);
            call.enqueue(new Callback<NewUpdateLeadResponse>() {
                @Override
                public void onResponse(Call<NewUpdateLeadResponse> call, Response<NewUpdateLeadResponse> response) {

                    if (response.isSuccessful() && response != null) {
                        Toast.makeText(LeadStatusActivity.this, ""+response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LeadStatusActivity.this, IntrestedCallsActivity.class));
                        finish();
                    }else{
                        Toast.makeText(LeadStatusActivity.this, "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(Call<NewUpdateLeadResponse> call, Throwable t) {
                    Toast.makeText(LeadStatusActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
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
