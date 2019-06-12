package com.example.mahesh.wisdomoverseas.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

import com.example.mahesh.wisdomoverseas.ItemclickListner;
import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.InfoActivity;
import com.example.mahesh.wisdomoverseas.activities.LeadStatusActivity;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewInterestedCallResponse;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewPendingRemindersResponse;

import java.util.List;

public class InterestedcallAdapter extends RecyclerView.Adapter<InterestedcallAdapter.InterestedcallViewHolder> {
    Context mContext;
    List<NewInterestedCallResponse> interestedCallResponseList;
    private ItemclickListner itemclickListner;

    public InterestedcallAdapter(Context mContext, List<NewInterestedCallResponse> interestedCallResponseList) {
        this.mContext = mContext;
        this.interestedCallResponseList = interestedCallResponseList;
    }



    @NonNull
    @Override
    public InterestedcallViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.intrestedcallslist, viewGroup, false);
        // mContext=view.getContext();
        return new InterestedcallViewHolder(view);




    }

    @Override
    public void onBindViewHolder(@NonNull InterestedcallViewHolder interestedcallViewHolder, final int i) {

        interestedcallViewHolder.mStname.setText(interestedCallResponseList.get(i).leadName);
        //interestedcallViewHolder.mCity.setText((CharSequence) interestedCallResponseList.get(i).city);

        interestedcallViewHolder.mCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                gotoNext(i);

            }
        });

        interestedcallViewHolder.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInfo(i);

            }
        });
        interestedcallViewHolder.mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String phone =interestedCallResponseList.get(i).contactNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));


                intent.putExtra("Todaycalls",interestedCallResponseList.get(i).callFrom);
                mContext.startActivity(intent);
            }
        });

    }

    private void gotoNext(int i) {
        Intent intent = new Intent(mContext, LeadStatusActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Id",interestedCallResponseList.get(i).iD);
        mContext.startActivity(intent);
    }


    private void gotoInfo(int position) {

        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.putExtra("Id",interestedCallResponseList.get(position).iD);
        Log.e("idddd", String.valueOf(interestedCallResponseList.get(position).iD));
        intent.putExtra("studentName",interestedCallResponseList.get(position).leadName);
        intent.putExtra("fatherName",interestedCallResponseList.get(position).fatherName);
        intent.putExtra("IntrestedCountry",interestedCallResponseList.get(position).intrestedCountry);
        intent.putExtra("InterCourse",interestedCallResponseList.get(position).interbranch);
        intent.putExtra("intrestedcourese",interestedCallResponseList.get(position).interIntrestedCourse);
        intent.putExtra("states",interestedCallResponseList.get(position).state);
        intent.putExtra("children",interestedCallResponseList.get(position).siblings);
        intent.putExtra("Attempts",interestedCallResponseList.get(position).entranceTestAttemts);
        intent.putExtra("callfrom","Intrestedcalls");
        intent.putExtra("case","3");
        intent.putExtra("dataa",interestedCallResponseList.get(position));



        /*Bundle bundle = new Bundle();

        bundle.putString("Id", String.valueOf(todayCallResponseList.get(position).iD));
        Log.e("id",""+todayCallResponseList.get(position).iD);
        Log.e("studentname",""+todayCallResponseList.get(position).student);

        bundle.putString("studentName",todayCallResponseList.get(position).student);
        bundle.putString("from","TodayCalls");


        BasicInfofragment fragInfo = new BasicInfofragment();
        fragInfo.setArguments(bundle);
*/

        mContext.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return interestedCallResponseList.size();
    }




    public class  InterestedcallViewHolder extends RecyclerView.ViewHolder {


        public TextView mStname, mCity;
        public ImageView mCallprofile, mArrow, mCall, mInfo;

        public InterestedcallViewHolder(@NonNull View view) {
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
