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
import android.widget.Button;
import android.widget.TextView;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.responses.HistoryCalls;
import com.example.mahesh.wisdomoverseas.network.Constants;
import com.example.mahesh.wisdomoverseas.network.SharedPref;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    Context mContext;
    List<HistoryCalls> historyCallsList;
    SharedPref sharedPref;
    String userName;

    public HistoryAdapter(Context mContext, List<HistoryCalls> historyCallsList) {
        this.mContext = mContext;
        this.historyCallsList = historyCallsList;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.historyitems,viewGroup,false);
        return new HistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder historyViewHolder, final int i) {
        historyViewHolder.mHistoryStdname.setText(historyCallsList.get(i).leadName);
        historyViewHolder.mHistoryCalldate.setText(parseTime(historyCallsList.get(i).callDate));
        SimpleDateFormat inputFormat = new SimpleDateFormat(Constants.HISTORYDATEFORMAT);
        historyViewHolder.mHistoryStatus.setText(historyCallsList.get(i).callStatus);
        Intent intent =new Intent();
        String callduration = intent.getStringExtra("callDuration");
//     historyViewHolder.mHistorycallduration.setText(callduration);
       // Log.e("calldddd...",""+callduration);

        historyViewHolder.mBtnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPref = new SharedPref(mContext.getApplicationContext());
                userName = sharedPref.getUserName();
               int  id = historyCallsList.get(i).leadId;
               Log.e("history id",""+historyCallsList.get(i).leadId);
                Uri uri = Uri.parse("https://wo.brandwar.in/Employee/UploadFilesData/"+id+"?sales="+userName);
                // missing 'http://' will cause crashed
                Log.e("lead id",""+id);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return historyCallsList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder{

          TextView mHistoryStdname,mHistoryCalldate,mHistoryStatus,mHistorycallduration;

          String CallDuration;
          Button mBtnupload;

        public HistoryViewHolder(@NonNull View itemView) {

            super(itemView);

            mHistoryStdname = (TextView)itemView.findViewById(R.id.tvhistoryStdname);
            mHistoryCalldate =(TextView)itemView.findViewById(R.id.tvhidtorycalldate);
            mHistoryStatus =(TextView)itemView.findViewById(R.id.tvhidtorystatus);
           // mHistorycallduration = (TextView)itemView.findViewById(R.id.tvhistorycallduration);
           mBtnupload = itemView.findViewById(R.id.btnhistoryupload);
        }
    }



    private String parseTime(String date){
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
    private String parseDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date1 = format.parse(date.replace("T"," "));
            String d= new SimpleDateFormat("HH:mm:ss").format(date1);
            return d;
        }catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
/*
    private  String getCallDuration(){

        StringBuffer sb = new StringBuffer();
        Cursor managedCursor = mContext.getContentResolver().query(CallLog.CONTENT_URI,null,null,null,null);
        int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
        int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
        while (managedCursor.moveToNext()){

            String callType = managedCursor.getString(type);
            String Duration =managedCursor.getString(duration);
            String dir = null;

            int dircode = Integer.parseInt(callType);
            switch (dircode){
                case CallLog.Calls.OUTGOING_TYPE:
                    dir ="OUTGOING";
                    break;
            }

            Toast.makeText(mContext, "Call Duration "+Duration, Toast.LENGTH_SHORT).show();
        }
        managedCursor.close();
        return sb.toString();
    }*/

}

