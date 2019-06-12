package com.example.mahesh.wisdomoverseas.adapters;

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
import com.example.mahesh.wisdomoverseas.activities.NewInfoActivity;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPendingRemindersResponse;

import java.util.List;

public class PendingReminderAdapter  extends RecyclerView.Adapter<PendingReminderAdapter.MyViewHolder> {

        Context mContext;
       //List<TodayCallResponse> todayCallResponseList;
    List<NewPendingRemindersResponse> pendingRemindersResponseList;
        String CallFrom;

        //List<LeadsResponse> leadsResponseslist;


        public PendingReminderAdapter(Context mContext, List<NewPendingRemindersResponse> pendingRemindersResponseList
                /*List<LeadsResponse> leadsResponseslist*/) {
            this.mContext = mContext;
            this.pendingRemindersResponseList = pendingRemindersResponseList;
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
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

            myViewHolder.mStname.setText(pendingRemindersResponseList.get(i).leadName);
        myViewHolder.mCity.setText((CharSequence) pendingRemindersResponseList.get(i).city);
        myViewHolder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInfo(i);

            }
        });

        myViewHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone =pendingRemindersResponseList.get(i).contactNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intent.putExtra("Todaycalls",pendingRemindersResponseList.get(i).callFrom);
                mContext.startActivity(intent);
            }
        });

        }

        private void gotoInfo(int position) {

            Intent intent = new Intent(mContext, InfoActivity.class);
            intent.putExtra("Id",pendingRemindersResponseList.get(position).iD);
            Log.e("idddd", String.valueOf(pendingRemindersResponseList.get(position).iD));
            intent.putExtra("studentName",pendingRemindersResponseList.get(position).leadName);
            intent.putExtra("fatherName",pendingRemindersResponseList.get(position).fatherName);
            intent.putExtra("callfrom","Reminder");
            intent.putExtra("case","7");
            intent.putExtra("prdata",pendingRemindersResponseList.get(position));




            mContext.startActivity(intent);


        }




        @Override
        public int getItemCount() {
            //return leadsResponseslist.size();
            return pendingRemindersResponseList.size();
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


            }
        }
    }







