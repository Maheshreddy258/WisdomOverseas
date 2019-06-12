package com.example.mahesh.wisdomoverseas.activities;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.adapters.RemainderRecycleAdapter;
import com.example.mahesh.wisdomoverseas.adapters.TodayCallsAdapter;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewRemindersInfoResponse;
import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;
import com.example.mahesh.wisdomoverseas.models.responses.RemindersResponse;
import com.example.mahesh.wisdomoverseas.models.responses.TodayCallResponse;
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

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemainderActivity extends AppCompatActivity {


    Toolbar toolbar;
    ProgressBar reminder_progressBar;
    //  CalendarView calendarView;
    HorizontalCalendar horizontalCalendar;
    RecyclerView mRecycle;
    SharedPref sharedPref;
    String userName, city1, student1;
    //List<RemindersResponse> remindersResponse;
    List<NewRemindersInfoResponse> remindersInfoResponses;
    String userdate = "";
    RemainderRecycleAdapter remainderRecycleAdapter;
    String OnselectConvert;
    //List<RemindersResponse> remindersResponseList = new ArrayList<>();
    List<NewRemindersInfoResponse> remindersResponseList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remainder);

        reminder_progressBar = findViewById(R.id.profile_progressBar);
        mRecycle = findViewById(R.id.recycleremainders);
        mRecycle.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecycle.setItemAnimator(new DefaultItemAnimator());

        toolbar = findViewById(R.id.toolbar);
        //horizontalCalendar = findViewById(R.id.calendarView);


//        calendarView = findViewById(R.id.calendarView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);



        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        Log.e("startDate:", "" + startDate.getTime());
         startDate.add(Calendar.DAY_OF_YEAR, -365);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_YEAR, 365);
        Log.e("endDate:", "" + endDate.getTime());
        //  endDate.add(Calendar.DAY_OF_YEAR, -22);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();
        loadRemainderData();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                List<NewRemindersInfoResponse> responseList = new ArrayList<>();
                remainderRecycleAdapter = new RemainderRecycleAdapter(getApplicationContext(), responseList);
                mRecycle.setAdapter(remainderRecycleAdapter);
                Date calendar = Calendar.getInstance().getTime();
                SimpleDateFormat dateFormatCOnselect = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                String strDateCOnselect = dateFormatCOnselect.format(date.getTime());
                //do something
                Log.e("mdate", "" + date.getTime());
                if (remindersResponseList != null && remindersResponseList.size() > 0) {

                        for (NewRemindersInfoResponse remindersResponse : remindersResponseList) {
                        //convert the reminder date string to date format
                        if (remindersResponse.reminderdateTime != null && !remindersResponse.reminderdateTime.isEmpty()) {
                            Date reminderDate = null;
                            try {
                                reminderDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(remindersResponse.reminderdateTime);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Date reminderCalendar = Calendar.getInstance().getTime();
                            SimpleDateFormat dateFormatCOnselect2 = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                            String dateOnselect = dateFormatCOnselect2.format(reminderDate);

                            //reminderCalendar.setTime(reminderDate);
                            Log.e("reminderCalendar", "" + strDateCOnselect);
                            Log.e("calendar", "" + dateOnselect);
                            if (strDateCOnselect != null && dateOnselect != null && strDateCOnselect.equals(dateOnselect)) {
                                responseList.add(remindersResponse);
                            }
                        }
                    }
                    if (responseList != null && responseList.size() > 0) {
                        remainderRecycleAdapter = new RemainderRecycleAdapter(getApplicationContext(), responseList);
                        mRecycle.setAdapter(remainderRecycleAdapter);
                    } else {
                        Toast.makeText(RemainderActivity.this, "No reminder found on this date", Toast.LENGTH_SHORT).show();
                    }

                }
            }

        });

        //prepareRemainderData();


    }

    private void loadRemainderData() {

        sharedPref = new SharedPref(getApplicationContext());
        userName = sharedPref.getUserName();
        if (ConnectivityDetector.isConnectingToInternet(getApplicationContext())) {
            //reminder_progressBar.setVisibility(View.VISIBLE);
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<List<NewRemindersInfoResponse>> call = apiInterface.getRemindersInfoResponse(userName);
            call.enqueue(new Callback<List<NewRemindersInfoResponse>>() {
                @Override
                public void onResponse(Call<List<NewRemindersInfoResponse>> call, Response<List<NewRemindersInfoResponse>> response) {
                    if (response.isSuccessful() && response != null) {

                        remindersResponseList = response.body();
                        if (remindersResponseList != null && remindersResponseList.size() > 0) {

                            Log.e("reminder", "Get Reminder");


                            Date calendar = Calendar.getInstance().getTime();
                            SimpleDateFormat dateFormatC = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                            String strDateC = dateFormatC.format(calendar);

                            List<NewRemindersInfoResponse> remindersResponseList1 = new ArrayList<>();
                            for (NewRemindersInfoResponse remindersResponse : remindersResponseList) {
                                Log.e("reminderid", "" + remindersResponse.iD);
                                //convert the reminder date string to date format
                                if (remindersResponse.reminderdateTime != null && !remindersResponse.reminderdateTime.isEmpty()) {
                                    Date reminderDate = null;
                                    try {
                                        reminderDate = new SimpleDateFormat(Constants.DATEFORMAT).parse(remindersResponse.reminderdateTime);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                  /* Calendar reminderCalendar = Calendar.getInstance();
                                   reminderCalendar.setTime(reminderDate);*/
                                    Date dateConversion = Calendar.getInstance().getTime();
                                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
                                    String strDate = dateFormat.format(reminderDate);


                                    Log.e("reminderCalendar", "" + strDateC);
                                    Log.e("calendar", "" + strDate);

                                    if (strDate != null && strDateC != null && strDateC.equals(strDate)) {
                                        remindersResponseList1.add(remindersResponse);
                                    }
                                }
                            }
                            if (remindersResponseList1 != null && remindersResponseList1.size() > 0) {
                                remainderRecycleAdapter = new RemainderRecycleAdapter(getApplicationContext(), remindersResponseList1);
                                mRecycle.setAdapter(remainderRecycleAdapter);
                                addNotification();
                            } else {
                                Toast.makeText(RemainderActivity.this, "No reminders found on this date", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Failed to get the  Reminders Data", Toast.LENGTH_SHORT).show();
                        }
                    }

                }

                @Override
                public void onFailure(Call<List<NewRemindersInfoResponse>> call, Throwable t) {
                    //reminder_progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }
    }

    private void addNotification() {


            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(this)
                            .setSmallIcon(R.drawable.wisdomicon)
                            .setContentTitle("Reminders..")
                            .setContentText("You have a reminders today.. ");

            Intent notificationIntent = new Intent(this, RemainderActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent);

            // Add as notification
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

           Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
           builder.setSound(alarmSound);
            manager.notify(0, builder.build());
        }


    private void prepareRemainderData() {
        List<NewRemindersInfoResponse> todayCallResponseList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NewRemindersInfoResponse todayCallResponse = new NewRemindersInfoResponse();
            todayCallResponse.createdDate = (i + 1) + " :00 Am";
            todayCallResponse.leadName = "Student" + (i + 1);
            todayCallResponse.iD = (i + 1);
            todayCallResponseList.add(todayCallResponse);
        }
        remainderRecycleAdapter = new RemainderRecycleAdapter(getApplicationContext(), todayCallResponseList);
        mRecycle.setAdapter(remainderRecycleAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // todo: goto back activity from here
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onResume() {
        //prepareData();
        loadRemainderData();;
        super.onResume();
    }

}