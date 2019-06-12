package com.example.mahesh.wisdomoverseas.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;

import com.example.mahesh.wisdomoverseas.models.requests.UserLoginRequest;
import com.example.mahesh.wisdomoverseas.models.responses.UserLoginResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    CardView mCardView;
    TextView mTxtlogin, mPwd, mTxtfpwd;
    EditText mEtlogin, mEtpwd;
    Button mBtnLogin;
    ProgressBar loader;
    SharedPref sharedPref;
    FloatingActionButton fab_Login_Snackbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fab_Login_Snackbar = findViewById(R.id.fab_Login_Snackbar);
        mCardView = findViewById(R.id.cv1);
        mTxtlogin = findViewById(R.id.txtlogin);
        mPwd = findViewById(R.id.txtpwd);
        mTxtfpwd = findViewById(R.id.txtfpwd);
        //loader = findViewById(R.id.login_progressBar);
        mEtlogin = findViewById(R.id.etlogin);
        mEtpwd = findViewById(R.id.etpwd);

        mBtnLogin = findViewById(R.id.btnlogin);
        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        mTxtfpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, forgotpwdActivity.class));
                finish();
            }
        });
        //init sharedpref
        sharedPref = new SharedPref(getApplicationContext());
    }

    private void userLogin() {
        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
            final String login = mEtlogin.getText().toString().trim();
            final String pass = mEtpwd.getText().toString().trim();

            if (login.isEmpty()) {
                mEtlogin.setError("LoginId is required");
                mEtlogin.requestFocus();
                return;
            }
            if (pass.isEmpty()) {
                mEtpwd.setError("Password is required");
                mEtpwd.requestFocus();
                return;
            }
//            loader.setVisibility(View.VISIBLE);
            final UserLoginRequest userLoginRequest = new UserLoginRequest(login, pass);
            userLoginRequest.userName = login;
            userLoginRequest.pwd = pass;


            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<UserLoginResponse> call = apiInterface.getServiceResponse(userLoginRequest);

            call.enqueue(new Callback<UserLoginResponse>() {
                @Override
                public void onResponse(Call<UserLoginResponse> call, Response<UserLoginResponse> response) {
                   // loader.setVisibility(View.GONE);
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().loginStatus) {
                                startActivity(new Intent(LoginActivity.this, DashBoardActivity.class));
                                finishAffinity();
                                //to save in the shared pref
                                sharedPref.userLogin(userLoginRequest);
                            } else {
                                Toast.makeText(LoginActivity.this, "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<UserLoginResponse> call, Throwable t) {
                    //loader.setVisibility(View.GONE);
                    Log.e("response:", "" + t.getMessage());

                }
            });
        } else {
            Snackbar.make(fab_Login_Snackbar, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();

        }


    }

    @SuppressLint("NewApi")
    private void showLoading() {
        mEtlogin.setError(null);
        mEtpwd.setError(null);
        loader.setVisibility(View.VISIBLE);
        mBtnLogin.setVisibility(View.GONE);
    }

    @SuppressLint("NewApi")
    private void showForm() {
        //TransitionManager.beginDelayedTransition(Button);
        loader.setVisibility(View.GONE);
        mBtnLogin.setVisibility(View.VISIBLE);
    }

}
