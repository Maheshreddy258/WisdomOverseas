package com.example.mahesh.wisdomoverseas.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.HistoryAdapter;
import com.example.mahesh.wisdomoverseas.models.responses.HistoryCalls;
import com.example.mahesh.wisdomoverseas.models.responses.TodayCallResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {


    Toolbar toolbar;
    RecyclerView mRecycler;
    HistoryAdapter historyAdapter;
    List<HistoryCalls> historyCallsList;
    SharedPref sharedPref;
    String userName,city1,student1;
    ProgressBar history_progressbar;
    TextView mTvhistorystdname,mTvhistorycalldate,mTvhistorystatus,mTvhistorycallduration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTvhistorystdname = findViewById(R.id.tvhistoryStdname);
        mTvhistorycalldate = findViewById(R.id.tvhidtorycalldate);
        mTvhistorystatus = findViewById(R.id.tvhidtorystatus);
     //   mTvhistorycallduration = findViewById(R.id.tvhistorycallduration);

        history_progressbar = findViewById(R.id.historyprogressbar);
        mRecycler = (RecyclerView)findViewById(R.id.pendingrecyclerview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycler.setItemAnimator(new DefaultItemAnimator());

       // preparePendingData();

        prepareHistoryData();

   /*     if (ContextCompat.checkSelfPermission(HistoryActivity.this, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED){

            if (ActivityCompat.shouldShowRequestPermissionRationale(HistoryActivity.this, Manifest.permission.READ_CALL_LOG)){
                ActivityCompat.requestPermissions(HistoryActivity.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
            }else{
                ActivityCompat.requestPermissions(HistoryActivity.this,new String[]{Manifest.permission.READ_CALL_LOG},1);
            }
        }else
        {

        }*/



    }

 /*   @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1 :
                if (grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(HistoryActivity.this,Manifest.permission.READ_CALL_LOG) ==PackageManager.PERMISSION_GRANTED){

                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(this, "Permission Not Granted", Toast.LENGTH_SHORT).show();
                }
        }
        return ;
    }*/

    public final String getCallDuration(){

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = getContentResolver().query(CallLog.CONTENT_URI,null,null,null,null);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        while (managedCursor.moveToNext()){

            String callType = managedCursor.getString(type);
          final  String Duration =managedCursor.getString(duration);
            String dir = null;

            int dircode = Integer.parseInt(callType);
            switch (dircode){
                case CallLog.Calls.OUTGOING_TYPE:
                  dir ="OUTGOING";
                  break;
            }
        }
        managedCursor.close();
        return sb.toString();
    }

    private void prepareHistoryData() {
        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();
        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {

            history_progressbar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<HistoryCalls>> call = apiInterface.getHistoryCalls(userName);
            call.enqueue(new Callback<List<HistoryCalls>>() {
                @Override
                public void onResponse(Call<List<HistoryCalls>> call, Response<List<HistoryCalls>> response) {
                    history_progressbar.setVisibility(View.GONE);
                    if (response.isSuccessful() && response != null) {

                        List<HistoryCalls> historyCallsList = new ArrayList<>();

                        historyCallsList = response.body();
                        if (historyCallsList != null && historyCallsList.size() > 0) {
                            Log.e("response","response");

                            historyAdapter = new HistoryAdapter(getApplicationContext(), historyCallsList);
                            Collections.reverse(historyCallsList);
                            mRecycler.setAdapter(historyAdapter);

                        }else {
                            Toast.makeText(getApplicationContext(), "No History Found", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<HistoryCalls>> call, Throwable t) {

                    history_progressbar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void preparePendingData() {

        List<TodayCallResponse> todayCallResponseList=new ArrayList<>();
        for(int i=0;i<10;i++){
            TodayCallResponse todayCallResponse=new TodayCallResponse();
            todayCallResponse.city="City"+(i+1);
            todayCallResponse.student="Student"+(i+1);
            todayCallResponse.iD=(i+1);
            todayCallResponseList.add(todayCallResponse);
        }
       // historyAdapter= new HistoryAdapter(getApplicationContext(),);
        mRecycler.setAdapter(historyAdapter);


    }
    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
}
