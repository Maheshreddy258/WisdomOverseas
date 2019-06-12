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
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodaycallBacksResponse;

import java.util.List;

public class TodaycallBacksAdapter extends  RecyclerView.Adapter<TodaycallBacksAdapter.TodayCallViewHolder>{


    Context mContext;
    List<NewTodaycallBacksResponse> todaycallBacksResponseList;

    public TodaycallBacksAdapter(Context mContext, List<NewTodaycallBacksResponse> todaycallBacksResponseList) {
        this.mContext = mContext;
        this.todaycallBacksResponseList = todaycallBacksResponseList;
    }

    @NonNull
    @Override
    public TodayCallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todaycallslist, viewGroup, false);
        // mContext=view.getContext();


        return new TodayCallViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayCallViewHolder todayCallViewHolder,final int i) {

        todayCallViewHolder.mStname.setText(todaycallBacksResponseList.get(i).leadName);
        todayCallViewHolder.mCity.setText((CharSequence) todaycallBacksResponseList.get(i).city);
        todayCallViewHolder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoInfo(i);
            }
        });
        todayCallViewHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone =todaycallBacksResponseList.get(i).contactNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intent.putExtra("CallBack Calls",todaycallBacksResponseList.get(i).callFrom);
                mContext.startActivity(intent);
            }
        });

    }

    private void gotoInfo(int position) {


        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.putExtra("Id",todaycallBacksResponseList.get(position).iD);
        Log.e("idddd", String.valueOf(todaycallBacksResponseList.get(position).iD));
        intent.putExtra("studentName",todaycallBacksResponseList.get(position).leadName);
        intent.putExtra("fatherName",todaycallBacksResponseList.get(position).fatherName);
        intent.putExtra("callfrom","TodayCalls");
        intent.putExtra("case","9");
        intent.putExtra("datatoday",todaycallBacksResponseList.get(position));




        mContext.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return todaycallBacksResponseList.size();
    }

    public class  TodayCallViewHolder extends RecyclerView.ViewHolder{


        public TextView mStname, mCity;
        public ImageView mCallprofile, mArrow, mCall, mInfo;

        public TodayCallViewHolder(@NonNull View view) {
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
