package com.example.mahesh.wisdomoverseas.activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.responses.MyProfileResponse;
import com.example.mahesh.wisdomoverseas.models.responses.UserLoginResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


    TextView mTvnamep, mTvunamep, mTvdesgp, mTvemailp, mTvphnop, mTvaltnop, mTvnamep2, mTvunamep2,
            mTvdesgp2, mTvemailp2, mTvphnop2, mTvaltnop2, mTvproname, mTvprodesg;
    ImageView mImgpro;
    Toolbar toolbar;
    SharedPref sharedPref;
    String userName;
    ProgressBar profile_progressBar;
    FloatingActionButton fab_profile_Snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fab_profile_Snackbar = findViewById(R.id.fab_profile_Snackbar);
        profile_progressBar =findViewById(R.id.profile_progressBar);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTvnamep = findViewById(R.id.txtnamesc);
        mTvnamep2 = findViewById(R.id.txtnamesc2);
        mTvdesgp = findViewById(R.id.txtdesigsc);
        mTvdesgp2 = findViewById(R.id.txtdesgsc2);
        mTvemailp = findViewById(R.id.txtemailsc);
        mTvemailp2 = findViewById(R.id.txtemailsc2);
        mTvphnop = findViewById(R.id.txtphnosc);
        mTvphnop2 = findViewById(R.id.txtphnosc2);
        mTvaltnop = findViewById(R.id.txtaltnosc);
        mTvaltnop2 = findViewById(R.id.txtaltnosc2);
        mTvunamep = findViewById(R.id.txtusernamesc);
        mTvunamep2 = findViewById(R.id.txtusernamesc2);
        mTvproname = findViewById(R.id.tvprofilesc);
        mTvprodesg = findViewById(R.id.tvrolesc);

        mImgpro = findViewById(R.id.imageViewsc);
        //init sharedpref
        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();
        Log.e("userName", "" + userName);
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
                            mTvnamep2.setText(response.body().name);
                            mTvdesgp2.setText(response.body().role);
                            mTvemailp2.setText(response.body().email);
                            mTvphnop2.setText(response.body().phone);
                            mTvaltnop2.setText(response.body().state);
                            mTvunamep2.setText(userName);
                            mTvproname.setText(response.body().name);
                            mTvprodesg.setText(response.body().role);
                        }
                    }

                    @Override
                    public void onFailure(Call<MyProfileResponse> call, Throwable t) {
                        profile_progressBar.setVisibility(View.GONE);
                        Toast.makeText(ProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Snackbar.make(fab_profile_Snackbar, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();

        }


    }

    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }
}
