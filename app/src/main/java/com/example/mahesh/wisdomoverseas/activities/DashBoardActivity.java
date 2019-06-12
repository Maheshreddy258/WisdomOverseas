package com.example.mahesh.wisdomoverseas.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.responses.MyProfileResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashBoardActivity extends AppCompatActivity {

    CardView mCvtop, mCvtc, mCvrem, mCvts, mCvprofile, mCvhistory, mCvreport,mCvPendingReminders,mCvIntrestecalls,mCvcallbacks,mCvincoming
              ,mCvsearch,mCvrecord,mCvnewform;
    TextView mHi, mEn, mTvtc, mTvrem, mTvts, mTvprofile, mTvtp, mTvreport,mTvpr,mTvic,mTvcc,mTvincoming,mTvsearch,mTvrecord,mTvnewForm;
    ImageView mImgprofiletop, mImgphone, mImgrem, mImgts, mImgprofile, mImgtp, mImgreport,mImgpr,mImgic,mImgcc,mImgincoming,mImgsearch,mImgrecord,
              mImgnewform;
    Toolbar toolbar;
    SharedPref sharedPref;
    String userName;
    ProgressBar profile_progressBar;
    FloatingActionButton fab_profile_Snackbar;
    SharedPreferences sharedpreferences;
    FloatingActionButton fab_Login_Snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar();
        profile_progressBar =findViewById(R.id.profile_progressBar);
        mCvtop = findViewById(R.id.cv2);
        mCvtc = findViewById(R.id.cv3);
        mCvrem = findViewById(R.id.cv4);
        mCvts = findViewById(R.id.cv5);
        mCvprofile = findViewById(R.id.cv6);
        mCvhistory = findViewById(R.id.cv7);
        mCvreport = findViewById(R.id.cv8);
        mCvPendingReminders=findViewById(R.id.cv9);
        mCvIntrestecalls =findViewById(R.id.cv10);
        mCvcallbacks = findViewById(R.id.cv11);
        mCvincoming = findViewById(R.id.cvincoming);
        mCvsearch = findViewById(R.id.cvsearch);
        mCvrecord = findViewById(R.id.cvrecord);
        mCvnewform = findViewById(R.id.cvnewform);



        mHi = findViewById(R.id.tvhi);
        mEn = findViewById(R.id.tven);
        mTvtc = findViewById(R.id.tvcalls);
        mTvrem = findViewById(R.id.tvrem);
        mTvts = findViewById(R.id.tvts);
        mTvprofile = findViewById(R.id.tvprofile);
       // mTvtp = findViewById(R.id.tvtp);
        mTvreport = findViewById(R.id.tvreport);
        mTvpr =findViewById(R.id.tvpr);
        mTvic=findViewById(R.id.tvic);
        mTvcc = findViewById(R.id.tvcc);
        mTvincoming = findViewById(R.id.tvincoming);
        mTvsearch = findViewById(R.id.tvsearch);
        mTvrecord = findViewById(R.id.tvrecord);
        mTvnewForm = findViewById(R.id.tvnewform);


        mImgprofiletop = findViewById(R.id.profileimg);
        mImgphone = findViewById(R.id.phone);
        mImgrem = findViewById(R.id.imgrem);
        mImgts = findViewById(R.id.imgts);
        mImgprofile = findViewById(R.id.imgprofile);
        //mImgtp = findViewById(R.id.imgtp);
        mImgreport = findViewById(R.id.imgrem);
        mImgpr=findViewById(R.id.imgpr);
        mImgic=findViewById(R.id.imgic);
        mImgcc = findViewById(R.id.imgccc);
        mImgincoming = findViewById(R.id.imgincoming);
        mImgsearch  = findViewById(R.id.imgsearch);
        mImgrecord =findViewById(R.id.imgrecord);
        mImgnewform = findViewById(R.id.imgnewform);

        SharedPref dataProccessor = new SharedPref(getApplicationContext());
        String usern = dataProccessor.getUName();
        mEn.setText(usern);
        mImgprofiletop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, ProfileActivity.class));
            }
        });

        mCvtc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, TodayCalls.class));


            }
        });
        mCvts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, TrackStatus.class));

            }
        });
        mCvhistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, HistoryActivity.class));

            }
        });

        mCvrem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, RemainderActivity.class));
            }
        });


        mCvprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashBoardActivity.this, ProfileActivity.class));
            }
        });

        mCvreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DashBoardActivity.this, Report.class));

            }
        });
        mCvPendingReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, PendingRemindersActivity.class));
                //Toast.makeText(DashBoardActivity.this, "This will live soon ...Thankyou!!", Toast.LENGTH_SHORT).show();
            }
        });

        mCvIntrestecalls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, IntrestedCallsActivity.class));
                //Toast.makeText(DashBoardActivity.this, "This will live soon ...Thankyou!!", Toast.LENGTH_SHORT).show();
            }
        });

        mCvcallbacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, CallBacksActivity.class));
                //Toast.makeText(DashBoardActivity.this, "This will live soon ...Thankyou!!", Toast.LENGTH_SHORT).show();
            }
        });

        mCvincoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, IncomingcallsActivity.class));
                //Toast.makeText(DashBoardActivity.this, "This will live soon ...Thankyou!!", Toast.LENGTH_SHORT).show();
            }
        });
        mCvsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, SearchActivity.class));
                //Toast.makeText(DashBoardActivity.this, "This will live soon ...Thankyou!!", Toast.LENGTH_SHORT).show();
            }
        });


        mCvnewform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoardActivity.this, NewformActivity.class));
                //Toast.makeText(DashBoardActivity.this, "This will live soon ...Thankyou!!", Toast.LENGTH_SHORT).show();
            }
        });



        setName();

    }

    private void setName() {
        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();

        //check if the user name is not null and not empty
        if (userName != null && !userName.isEmpty()) {
            if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
                profile_progressBar.setVisibility(View.VISIBLE);
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<MyProfileResponse> profileResponseCall = apiInterface.getProfileDetails(userName);
                profileResponseCall.enqueue(new Callback<MyProfileResponse>() {
                    @Override
                    public void onResponse(Call<MyProfileResponse> call, Response<MyProfileResponse> response) {
                        profile_progressBar.setVisibility(View.GONE);
                        if (response != null && response.isSuccessful()) {
                            Log.e("userName", "" + userName);
                            mEn.setText(response.body().name);

                        }
                    }
                    @Override
                    public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                        //profile_progressBar.setVisibility(View.GONE);
                        Toast.makeText(DashBoardActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                Snackbar.make(fab_Login_Snackbar, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();

            }


        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuitem, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.logout:
                //Toast.makeText(getApplicationContext(), "Item 1 Selected", Toast.LENGTH_LONG).show();
                SharedPref.deleteShared();
                startActivity(new Intent(DashBoardActivity.this, LoginActivity.class));
                finish();
                return true;

            /*case R.id.changepasssword:


               *//* startActivity(new Intent(DashBoardActivity.this, forgotpwdActivity.class));
                finish();*//*
                Toast.makeText(this, "This will live soon....", Toast.LENGTH_SHORT).show();
                return true;*/

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashBoardActivity.this.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

    }
}






    


