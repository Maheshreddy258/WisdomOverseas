package com.example.mahesh.wisdomoverseas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.responses.MyReportResponse;
import com.example.mahesh.wisdomoverseas.models.responses.ReportResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyReportAdapter extends RecyclerView.Adapter<MyReportAdapter.MyReportHolder>  {

    Context mcntx;
    //List<ReportResponse> mReportreponse;
    List<MyReportResponse> myReportResponseList;
    //List<String> mReportList;


    public MyReportAdapter(Context mcntx, List<MyReportResponse> myReportResponseList) {
        this.mcntx = mcntx;
        this.myReportResponseList = myReportResponseList;
    }

    @NonNull
    @Override
    public MyReportHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v;
        v = LayoutInflater.from(mcntx).inflate(R.layout.myreportlistitem,viewGroup,false);
        MyReportHolder myReportHolder = new MyReportHolder(v);

        return myReportHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyReportHolder myReportHolder, int i) {
        myReportHolder.mCallCount.setText(myReportResponseList.get(i).callsDone+"");
        myReportHolder.mRemainder.setText(myReportResponseList.get(i).reminders+"");
        myReportHolder.mMsgr.setText(myReportResponseList.get(i).messages);
        myReportHolder.mDate.setText(parseDate(myReportResponseList.get(i).createdDate));
        myReportHolder.mTime.setText(parseTime(myReportResponseList.get(i).createdDate));

    }

    @Override
    public int getItemCount() {
        return myReportResponseList.size();
    }

    public class MyReportHolder extends RecyclerView.ViewHolder{

        TextView mCallCount,mRemainder,mMsg,mDate,mCall,mrem,mMsgr,mTime;

        public MyReportHolder(@NonNull View itemView) {
            super(itemView);
            mCallCount =itemView.findViewById(R.id.tvcallcount);
            mCall = itemView.findViewById(R.id.tvrepcall);
            mrem = itemView.findViewById(R.id.tvrem);
            mRemainder =itemView.findViewById(R.id.tvremcount);
            mMsg = itemView.findViewById(R.id.tvrepmsg);
            mMsgr = itemView.findViewById(R.id.tvmsg);
            mDate = itemView.findViewById(R.id.tvdate);
            mTime = itemView.findViewById(R.id.tvtime);

        }
    }
    private String parseTime(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        try {
            Date date1 = format.parse(date.replace("T"," "));
            String d= new SimpleDateFormat("HH:mm:ss").format(date1);
            return d;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
    private String parseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = format.parse(date.replace("T"," "));
            String d= new SimpleDateFormat("dd.MM.yyyy").format(date1);
            return d;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
