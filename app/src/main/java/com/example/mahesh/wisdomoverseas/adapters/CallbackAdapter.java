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
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewCallBacksResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewTodaycallBacksResponse;

import java.util.List;

public class CallbackAdapter extends RecyclerView.Adapter<CallbackAdapter.CallBackViewHolder>{
    Context mContext;
    List<NewCallBacksResponse> callBacksResponseList;

    public CallbackAdapter(Context mContext, List<NewCallBacksResponse> callBacksResponseList) {
        this.mContext = mContext;
        this.callBacksResponseList = callBacksResponseList;
    }

    @NonNull
    @Override
    public CallBackViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.todaycallslist, viewGroup, false);
        // mContext=view.getContext();


        return new CallBackViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull CallBackViewHolder callBackViewHolder, final int i) {

        callBackViewHolder.mStname.setText(callBacksResponseList.get(i).leadName);
        callBackViewHolder.mCity.setText((CharSequence) callBacksResponseList.get(i).city);
        callBackViewHolder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInfo(i);

            }
        });

        callBackViewHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone =callBacksResponseList.get(i).contactNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intent.putExtra("CallBack Calls",callBacksResponseList.get(i).callFrom);
                mContext.startActivity(intent);
            }
        });

    }

    private void gotoInfo(int position) {

        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.putExtra("Id",callBacksResponseList.get(position).iD);
        Log.e("idddd", String.valueOf(callBacksResponseList.get(position).iD));
        intent.putExtra("studentName",callBacksResponseList.get(position).leadName);
        intent.putExtra("fatherName",callBacksResponseList.get(position).fatherName);
        intent.putExtra("IntrestedCountry",callBacksResponseList.get(position).intrestedCountry);
        intent.putExtra("InterCourse",callBacksResponseList.get(position).interbranch);
        intent.putExtra("intrestedcourese",callBacksResponseList.get(position).interIntrestedCourse);
        intent.putExtra("states",callBacksResponseList.get(position).state);
        intent.putExtra("children",callBacksResponseList.get(position).siblings);
        intent.putExtra("Attempts",callBacksResponseList.get(position).entranceTestAttemts);
        intent.putExtra("callfrom","Intrestedcalls");
        intent.putExtra("callfrom","Callbacks");
        intent.putExtra("case","5");
        intent.putExtra("ddata",callBacksResponseList.get(position));





        mContext.startActivity(intent);


    }

    @Override
    public int getItemCount() {
        return callBacksResponseList.size();
    }

    public  class  CallBackViewHolder extends RecyclerView.ViewHolder {


         public TextView mStname, mCity;
         public ImageView mCallprofile, mArrow, mCall, mInfo;

         public CallBackViewHolder(@NonNull View view) {
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
