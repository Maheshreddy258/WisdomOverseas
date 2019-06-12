package com.example.mahesh.wisdomoverseas.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodayCallResponse;
import com.example.mahesh.wisdomoverseas.network.Constants;

import java.util.List;

public class CallsAdapter extends RecyclerView.Adapter<CallsAdapter.MyViewHolder> {

    private Context mContext;
    //private ArrayList<String> snames;
    List<NewTodayCallResponse> todayCallResponseList;

    String Pending,joined,processing;

    public CallsAdapter(Context mcontext, /*ArrayList<String> snames*/ List<NewTodayCallResponse> todayCallResponseList) {
        this.mContext = mcontext;
        this.todayCallResponseList = todayCallResponseList;
      /*  this.snames = snames;*/
    }

    @NonNull
    @Override
    public CallsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recclerview_item, parent, false);

        return new MyViewHolder(itemView);

    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
         TextView mStudentname, mDate, mStatus,mRemarks;
          ImageView mImgphone;

        public MyViewHolder(View view) {
            super(view);
            mStudentname = (TextView) view.findViewById(R.id.tcstdname);
            mDate = (TextView)view.findViewById(R.id.tvprodate);
            mRemarks = (TextView)view.findViewById(R.id.tvpremark2);
            mImgphone =(ImageView) view.findViewById(R.id.imgprophone);

            mStatus = (TextView) view.findViewById(R.id.tvupdate);



        }
    }

    @Override
    public void onBindViewHolder(@NonNull CallsAdapter.MyViewHolder holder, final int position) {

       /* holder.studentname.setText(snames.get(position));
        holder.status.setText("processing");*/

       holder.mStudentname.setText(todayCallResponseList.get(position).leadName);
       holder.mDate.setText(todayCallResponseList.get(position).createdDate+"");
       holder.mRemarks.setText(todayCallResponseList.get(position).finalFeedBack+"");
       holder.mStatus.setText(String.valueOf(todayCallResponseList.get(position).leadInfoStatus));


       // holder.mStatus.setTextColor(Color.parseColor("#FFEB3B"));

        if (holder.mStatus.equals(Constants.PENDING)){
             holder.mStatus.setTextColor(Color.parseColor("#FFEB3B"));

        }else if (holder.mStatus.equals(Constants.PROCESSING)){

            holder.mStatus.setTextColor(Color.parseColor("#FFEB3B"));
        }else if (holder.mStatus.equals(Constants.JOINED)){

            holder.mStatus.setTextColor(Color.parseColor("#4CAF50"));
        }






        holder.mImgphone.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String phone= todayCallResponseList.get(position).contactNumber;
               Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
               mContext.startActivity(intent);
           }
       });

/*
        if(Pending.equalsIgnoreCase(Constants.PENDING))
        {
            holder.mStatus.setBackgroundColor(Color.parseColor("#FFEB3B"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else if (processing.equalsIgnoreCase(Constants.PROCESSING))
        {
            holder.mStatus.setBackgroundColor(Color.parseColor("#DF1212"));
            //  holder.imageView.setBackgroundColor(Color.parseColor("#FFFAF8FD"));
        }
        else {
            holder.mStatus.setBackgroundColor(Color.parseColor("#4CAF50"));
        }*/


    }

    @Override
    public int getItemCount() {
        return todayCallResponseList.size();
    }






}
