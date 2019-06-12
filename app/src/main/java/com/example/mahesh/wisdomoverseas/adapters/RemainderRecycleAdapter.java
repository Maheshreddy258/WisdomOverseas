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
import com.example.mahesh.wisdomoverseas.ReminderNewInfoActivity;
import com.example.mahesh.wisdomoverseas.activities.InfoActivity;
import com.example.mahesh.wisdomoverseas.activities.InfoReminder;
import com.example.mahesh.wisdomoverseas.activities.NewInfoActivity;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewRemindersInfoResponse;
import com.example.mahesh.wisdomoverseas.models.responses.RemindersResponse;
import com.example.mahesh.wisdomoverseas.network.Constants;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RemainderRecycleAdapter extends RecyclerView.Adapter<RemainderRecycleAdapter.RemainderViewHolder> {


    Context mContext;
    List<NewRemindersInfoResponse> remindersResponseList;
    //List<RemindersResponse> todayCallResponseList;


    public RemainderRecycleAdapter(Context mContext, List<NewRemindersInfoResponse> remindersResponseList) {
        this.mContext = mContext;
        this.remindersResponseList = remindersResponseList;
    }

    @NonNull
    @Override
    public RemainderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.remainderslist,viewGroup,false);
        return new RemainderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RemainderViewHolder remainderViewHolder, final int i) {

        remainderViewHolder.mRemStdname.setText(remindersResponseList.get(i).leadName);
       // remainderViewHolder.mRemTime.setText(parseTime(remindersResponseList.get(i).reminderdate));
        remainderViewHolder.mRemTime.setText(Constants.convertStringToDate(remindersResponseList.get(i).reminderdateTime));

        Log.e("Reminder Date","reminderdate"+parseTime(remindersResponseList.get(i).reminderdateTime));
        Log.e("Reminder Date","reminderdate"+remindersResponseList.get(i).reminderdateTime);

        remainderViewHolder.mRemInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInfo(i);
            }
        });
        remainderViewHolder.mRemPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = remindersResponseList.get(i).contactNumber;
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

            }
        });
    }
    private void gotoInfo(int position) {

        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("Id",remindersResponseList.get(position).iD);
        Log.e("id reminder", String.valueOf(remindersResponseList.get(position).iD));
        intent.putExtra("studentName",remindersResponseList.get(position).leadName);
        intent.putExtra("IntrestedCountry",remindersResponseList.get(position).intrestedCountry);
        intent.putExtra("InterCourse",remindersResponseList.get(position).interbranch);
        intent.putExtra("intrestedcourese",remindersResponseList.get(position).interIntrestedCourse);
        intent.putExtra("states",remindersResponseList.get(position).state);
        intent.putExtra("children",remindersResponseList.get(position).siblings);
        intent.putExtra("Attempts",remindersResponseList.get(position).entranceTestAttemts);
        intent.putExtra("callfrom","Reminder");
        intent.putExtra("case","4");
        intent.putExtra("dataaa",remindersResponseList.get(position));




        //intent.putExtra("TodayCalls",todayCallResponseList.get(position).callfrom);
        mContext.startActivity(intent);


    }


    @Override
    public int getItemCount() {
        return remindersResponseList.size();
    }

    public class RemainderViewHolder extends RecyclerView.ViewHolder{

        public TextView mRemStdname, mRemTime;
        public ImageView mRemPhone,mRemInfo;

        public RemainderViewHolder(@NonNull View itemView) {
            super(itemView);

            mRemStdname = (TextView)itemView.findViewById(R.id.tvremStdname);
            mRemTime =(TextView)itemView.findViewById(R.id.tvremtime);
            mRemPhone =(ImageView)itemView.findViewById(R.id.imgremphonecall);
            mRemInfo = (ImageView)itemView.findViewById(R.id.imgremlistinfo2);



        }
    }


    private String parseTime(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Log.e("ssss",""+date.replace("T"," "));
            Date date1 = format.parse(date.replace("T"," "));
            String d= new SimpleDateFormat("dd.MM.yyyy").format(date1);
            return d;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }

}
