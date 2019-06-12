package com.example.mahesh.wisdomoverseas.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.InfoActivity;
import com.example.mahesh.wisdomoverseas.activities.ProfileActivity;
import com.example.mahesh.wisdomoverseas.activities.Report;
import com.example.mahesh.wisdomoverseas.activities.TodayCalls;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewAllCallsResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewRemindersInfoResponse;
import com.example.mahesh.wisdomoverseas.models.requests.ReportRequest;
import com.example.mahesh.wisdomoverseas.models.requests.UserLoginRequest;
import com.example.mahesh.wisdomoverseas.models.responses.CountResponse;
import com.example.mahesh.wisdomoverseas.models.responses.MyProfileResponse;
import com.example.mahesh.wisdomoverseas.models.responses.RemindersResponse;
import com.example.mahesh.wisdomoverseas.models.responses.ReportResponse;
import com.example.mahesh.wisdomoverseas.network.ConnectivityDetector;
import com.example.mahesh.wisdomoverseas.network.Constants;
import com.example.mahesh.wisdomoverseas.network.SharedPref;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiClient;
import com.example.mahesh.wisdomoverseas.network.retrofit.ApiInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReportFragment extends Fragment {

    TextView mRepemp, mRepcalls, mRepemp2, mRepcount, mRepmsg, mReprem, mReprem2, mRepmsg2;
    int id, calls, rem, year;
    String msgs, uname, month, subject, fileupload, createddate;
    boolean status;
    ReportRequest reportRequest;
    SharedPref sharedPref;
    String userName;
    ProgressBar profile_progressBar;
    FloatingActionButton fab_profile_Snackbar;
    EditText mRepetmsg;
    ProgressBar report_progressBar;
    FloatingActionButton fab_report_Snackbar;
    private List<String> dateList;
    private  List<String> callsList;
    NewAllCallsResponse newAllCallsResponse;
    Button mBtnmsgsubmit;

    public ReportFragment() {

    }


    public static ReportFragment newInstance(String param1, String param2) {
        ReportFragment fragment = new ReportFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_report, container, false);
        mRepemp = (TextView) view.findViewById(R.id.tvemprep);
        mRepemp2 = (TextView) view.findViewById(R.id.tvemprep2);
        mRepcalls = (TextView) view.findViewById(R.id.tvtotnoofcls);
        mRepcount = (TextView) view.findViewById(R.id.tvtotcount);
        mRepmsg = (TextView) view.findViewById(R.id.tvrepmsg);
        mRepetmsg = (EditText) view.findViewById(R.id.etrepmsg);
        dateList = new ArrayList<>();
        addSevenDays();

        //mRepmsg2 = (TextView) view.findViewById(R.id.tvrepmsg2);
        mReprem = (TextView) view.findViewById(R.id.tvremaindercls);
        mReprem2 = (TextView) view.findViewById(R.id.tvremaindercount);
        report_progressBar = view.findViewById(R.id.report_progressBar);
        fab_report_Snackbar = view.findViewById(R.id.fab_Login_Snackbar);
        mBtnmsgsubmit = view.findViewById(R.id.btnrepsubmit);
        mBtnmsgsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mRepetmsg.length() == 0) {
                    mRepetmsg.setError("Please Enter Message Here");
                    mRepetmsg.requestFocus();
                } else
                    setReportData(mRepetmsg.getText().toString());
            }
        });

        displayCountData();

        //setReportData();
        return view;
    }

    private void displayCountData() {

        sharedPref = new SharedPref(getActivity());
        userName = sharedPref.getUserName();
        Log.e("userName", "" + userName);
        if (userName != null && !userName.isEmpty()) {
            if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
                // profile_progressBar.setVisibility(View.VISIBLE);
                ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<CountResponse> countResponseCall = apiInterface.getCountResponse(userName);
                countResponseCall.enqueue(new Callback<CountResponse>() {
                    @Override
                    public void onResponse(Call<CountResponse> call, Response<CountResponse> response) {
                        // profile_progressBar.setVisibility(View.GONE);
                        if (response != null && response.isSuccessful()) {
                            if (response.body().userName != null) {
                                mRepemp2.setText(response.body().userName);

                            } else {
                                mRepemp2.setText(userName);
                            }
                           // mReprem2.setText(response.body().reminders + "");
                           mRepcount.setText(response.body().callsDone + "");

                            Log.e("Reminders", response.body().reminders + "");
                            Log.e("callsDone", response.body().callsDone + "");
                            if (response.body().reminders == 0) {
                                getReminderCount();

                            } else {

                                mReprem2.setText(response.body().reminders + "");
                            }
                        /*    if (response.body().callsDone == 0) {
                                getCallCount();

                            } else {

                                mRepcount.setText(response.body().callsDone + "");
                            }*/


                        }
                    }

                    @Override
                    public void onFailure(Call<CountResponse> call, Throwable t) {
                        //profile_progressBar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else {
            Snackbar.make(fab_profile_Snackbar, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();

        }
    }

    private void setReportData(String mMsg) {

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            report_progressBar.setVisibility(View.VISIBLE);

            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            //for getting current date
            Calendar calendar = Calendar.getInstance();
            calendar.getTime();


            ReportRequest reportRequest = new ReportRequest();
            reportRequest.callsDone = Integer.parseInt(mRepcount.getText().toString());
            reportRequest.iD = id;
            reportRequest.createdDate = createddate;
            reportRequest.userName = sharedPref.getUserName();
            reportRequest.reminders = Integer.parseInt(mReprem2.getText().toString());
            reportRequest.fileUpload = fileupload;
            reportRequest.month = month;
            reportRequest.status = true;
            reportRequest.year = year;
            reportRequest.messages = mMsg;
            reportRequest.subject = subject;

            Log.e("reportRequest", "" + reportRequest);

            final Call<ReportResponse> reportResponseCall = apiInterface.getReportResponse(reportRequest);
            reportResponseCall.enqueue(new Callback<ReportResponse>() {
                @Override
                public void onResponse(Call<ReportResponse> call, Response<ReportResponse> response) {
                    report_progressBar.setVisibility(View.GONE);

                    if (response != null && response.isSuccessful()) {

                        if (response.body().recordStatus) {

                            Toast.makeText(getActivity(), "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getActivity(), Report.class));
                            getActivity().finish();


                        } else {
                            Toast.makeText(getActivity(), "" + response.body().errorMessage, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(getActivity(), "Something went wrong,try again later", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ReportResponse> call, Throwable t) {
                    report_progressBar.setVisibility(View.GONE);
                    Toast.makeText(getActivity(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Snackbar.make(fab_report_Snackbar, getResources().getString(R.string.no_internet), Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }


    void getReminderCount() {
        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewRemindersInfoResponse>> call = apiInterface.getRemindersInfoResponse(userName);
            call.enqueue(new Callback<List<NewRemindersInfoResponse>>() {
                @Override
                public void onResponse(Call<List<NewRemindersInfoResponse>> call, Response<List<NewRemindersInfoResponse>> response) {

                    if (response.isSuccessful() && response != null) {

                        Log.e("response successs..","successfull");
                        List<NewRemindersInfoResponse> remindersResponseList = response.body();
                        if (remindersResponseList != null && remindersResponseList.size() > 0) {
                            List<NewRemindersInfoResponse> remindersResponseList1 = new ArrayList<>();
                            for (NewRemindersInfoResponse remindersResponse : remindersResponseList) {
                                if (remindersResponse.reminderdateTime != null && !remindersResponse.reminderdateTime.isEmpty()) {
                                    Date reminderDate = null;
                                    try {
                                        reminderDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(remindersResponse.reminderdateTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                                    String strDate = dateFormat.format(reminderDate);
                                    if (dateList != null && dateList.size() > 0 && dateList.contains(strDate)) {
                                        remindersResponseList1.add(remindersResponse);
                                    }
                                }
                            }
                            if (remindersResponseList1 != null && remindersResponseList1.size() > 0) {
                                mReprem2.setText(remindersResponseList1.size() + "");
                                Log.e("reminderrsssss",""+remindersResponseList1.size());
                            }

                        }
                    }else
                    {
                        Toast.makeText(getActivity(), "Something Went wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<NewRemindersInfoResponse>> call, Throwable t) {

                    Toast.makeText(getActivity(), ""+t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    void addSevenDays() {
        Calendar startDate = Calendar.getInstance();
        SimpleDateFormat dateFormatC = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
        String strDateC = dateFormatC.format(startDate.getTime());
        dateList.add(strDateC);
        for (int i = 1; i < 7; i++) {
            startDate = Calendar.getInstance();
            startDate.add(Calendar.DAY_OF_YEAR, i);
            SimpleDateFormat dateFormatC1 = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
            String strDateC1 = dateFormatC1.format(startDate.getTime());
            dateList.add(strDateC1);
        }
        Log.e("dateList", "" + dateList);

    }


  /*  void getCallCount(){

        if (ConnectivityDetector.isConnectingToInternet(getActivity())) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewAllCallsResponse>> call = apiInterface.getAllCallsResponse(userName);
            call.enqueue(new Callback<List<NewAllCallsResponse>>() {
                @Override
                public void onResponse(Call<List<NewAllCallsResponse>> call, Response<List<NewAllCallsResponse>> response) {
                    if (response.isSuccessful() && response != null) {
                        List<NewAllCallsResponse> newAllCallsResponseList = response.body();
                        if (newAllCallsResponseList != null && newAllCallsResponseList.size() > 0) {
                            List<NewAllCallsResponse> newAllCallsResponseList1 = new ArrayList<>();

                            if (callsList != null && callsList.size() > 0 ) {
                                newAllCallsResponseList1.add(newAllCallsResponse);
                            }

                            if (newAllCallsResponseList1 != null && newAllCallsResponseList1.size() > 0) {
                                mRepcount.setText(newAllCallsResponseList1.size() + "");
                            }
                        }
                        }
                    }


                @Override
                public void onFailure(Call<List<NewAllCallsResponse>> call, Throwable t) {

                }
            });
        }

    }*/

}
