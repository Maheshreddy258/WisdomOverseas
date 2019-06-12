package com.example.mahesh.wisdomoverseas.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.InfoActivity;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;

import java.util.List;

public class TodayCallsAdapter extends RecyclerView.Adapter<TodayCallsAdapter.MyViewHolder> {

    Context mContext;
    //List<TodayCallResponse> todayCallResponseList;
    List<NewTodayCallResponse> todayCallResponseList;
    String CallFrom;

    //List<LeadsResponse> leadsResponseslist;


    public TodayCallsAdapter(Context mContext, List<NewTodayCallResponse> todayCallResponseList
            /*List<LeadsResponse> leadsResponseslist*/) {
        this.mContext = mContext;
        this.todayCallResponseList = todayCallResponseList;
        //this.leadsResponseslist = leadsResponseslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todaycallslist, viewGroup, false);
        // mContext=view.getContext();


        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {

        myViewHolder.mCity.setText((CharSequence) todayCallResponseList.get(i).city);
        myViewHolder.mStname.setText(todayCallResponseList.get(i).leadName);
        myViewHolder.mStname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("StudentName:");
                alertDialog.setMessage(todayCallResponseList.get(i).leadName);
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });

       /*myViewHolder.mCity.setText((Integer) leadsResponseslist.get(i).city);
       myViewHolder.mStname.setText(leadsResponseslist.get(i).student);
*/
        myViewHolder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInfo(i);

            }
        });
      //myViewHolder.mInfo.setVisibility(View.GONE);
        myViewHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone =todayCallResponseList.get(i).contactNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                myViewHolder.mInfo.setVisibility(View.VISIBLE);
                intent.putExtra("Todaycalls",todayCallResponseList.get(i).callFrom);
                mContext.startActivity(intent);
                myViewHolder.mInfo.setVisibility(View.VISIBLE);

                Thread thread = new Thread(){

                    @Override
                    public void run() {

                        try{

                            sleep(12*1000);
                            gotoInfo(i);

                        }catch (Exception e){

                        }
                    }

                };
                thread.start();
/*
                myViewHolder.mInfo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        gotoInfo(i);
                        //myViewHolder.mInfo.setVisibility(View.VISIBLE);

                    }
                });*/


            }
        });


    }
  /*  @Override
    public void onResume() {
        //prepareData();
        loadRecyclerViewData();
        super.onResume();
    }*/



    private void gotoInfo(int position) {

        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.putExtra("Id",todayCallResponseList.get(position).iD);
        Log.e("idddd", String.valueOf(todayCallResponseList.get(position).iD));
        intent.putExtra("studentName",todayCallResponseList.get(position).leadName);
        intent.putExtra("city",todayCallResponseList.get(position).city);
        intent.putExtra("fatherName",todayCallResponseList.get(position).fatherName);
        intent.putExtra("IntrestedCountry",todayCallResponseList.get(position).intrestedCountry);
        intent.putExtra("InterCourse",todayCallResponseList.get(position).interbranch);
        intent.putExtra("intrestedcourese",todayCallResponseList.get(position).interIntrestedCourse);
        intent.putExtra("states",todayCallResponseList.get(position).state);
        intent.putExtra("children",todayCallResponseList.get(position).siblings);
        intent.putExtra("Attempts",todayCallResponseList.get(position).entranceTestAttemts);
        intent.putExtra("callfrom","TodayCalls");
        intent.putExtra("case","1");
        intent.putExtra("data",todayCallResponseList.get(position));





        mContext.startActivity(intent);


    }




    @Override
    public int getItemCount() {
        //return leadsResponseslist.size();
        return todayCallResponseList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mStname, mCity;
        public ImageView mCallprofile, mArrow, mCall, mInfo;

        public MyViewHolder(@NonNull View view) {
            super(view);

            mStname = (TextView) view.findViewById(R.id.tvStdname);
            mCity = (TextView) view.findViewById(R.id.tvcity);
            mCallprofile = (ImageView) view.findViewById(R.id.imgstd);
         //   mArrow = (ImageView) view.findViewById(R.id.imgaroow);
            mCall = (ImageView) view.findViewById(R.id.imgphonecall);
            mInfo = (ImageView) view.findViewById(R.id.imginfo);
          /* mInfo.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

               }
           });*/

        }
    }
}
