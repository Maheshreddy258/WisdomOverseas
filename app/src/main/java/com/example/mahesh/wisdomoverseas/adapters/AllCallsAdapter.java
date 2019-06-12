package com.example.mahesh.wisdomoverseas.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.InfoActivity;
import com.example.mahesh.wisdomoverseas.activities.SearchActivity;
import com.example.mahesh.wisdomoverseas.models.Newresponses.NewAllCallsResponse;

import java.util.ArrayList;
import java.util.List;

public class AllCallsAdapter extends RecyclerView.Adapter<AllCallsAdapter.AllCallsHoldeer> implements Filterable {


    Context mContext;
    List<NewAllCallsResponse> allCallsResponseList;
    List<NewAllCallsResponse> allCallsResponseListFull;
    Dialog mydialog;
    android.widget.SearchView searchView;

    public AllCallsAdapter(Context mContext, List<NewAllCallsResponse> allCallsResponseList) {
        this.mContext = mContext;
        this.allCallsResponseList = allCallsResponseList;
        allCallsResponseListFull = new ArrayList<>(allCallsResponseList);
    }

    @NonNull
    @Override
    public AllCallsHoldeer onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.allcalllist,viewGroup,false);
        return new AllCallsHoldeer(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllCallsHoldeer allCallsHoldeer,final int i) {


/*
        mydialog = new Dialog(mContext);
        mydialog.setContentView(R.layout.dialog_content);
        TextView stdname = (TextView)mydialog.findViewById(R.id.dialogstudentname);
        TextView city = (TextView)mydialog.findViewById(R.id.dialogcity);
        TextView id = (TextView)mydialog.findViewById(R.id.dialogid);
        TextView status = (TextView)mydialog.findViewById(R.id.dialogStatus);
        stdname.setText(allCallsResponseList.get(i).leadName);
        city.setText(allCallsResponseList.get(i).city);
        id.setText(String.valueOf(allCallsResponseList.get(i).iD));
        status.setText(allCallsResponseList.get(i).callStatus);*/



//        allCallsHoldeer.mAllnum.setText(todayCallResponseList.get(i).contactNumber);
        allCallsHoldeer.mAllname.setText(allCallsResponseList.get(i).leadName);

        /*allCallsHoldeer.mAllname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
                alertDialog.setTitle("StudentName:");
                alertDialog.setMessage(allCallsResponseList.get(i).leadName);
                alertDialog.setTitle("City:");
                alertDialog.setMessage(allCallsResponseList.get(i).city);
                AlertDialog dialog = alertDialog.create();
                dialog.show();
            }
        });*/

       // allCallsHoldeer.mcontactnumber.setText((CharSequence) allCallsResponseList.get(i).contactNumber);
        allCallsHoldeer.mAllLocality.setText((CharSequence) allCallsResponseList.get(i).city);
        allCallsHoldeer.mAllStatus.setText(String.valueOf(allCallsResponseList.get(i).callStatus));
        allCallsHoldeer.mInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoInfo(i);

            }
        });

        /*allCallsHoldeer.rel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mydialog.show();
            }
        });*/


    }

    private void gotoInfo(int position) {

        Intent intent = new Intent(mContext, InfoActivity.class);
        intent.putExtra("Id",allCallsResponseList.get(position).iD);
        Log.e("idddd", String.valueOf(allCallsResponseList.get(position).iD));
        intent.putExtra("studentName",allCallsResponseList.get(position).leadName);
        intent.putExtra("fatherName",allCallsResponseList.get(position).fatherName);
        intent.putExtra("IntrestedCountry",allCallsResponseList.get(position).intrestedCountry);
        intent.putExtra("InterCourse",allCallsResponseList.get(position).interbranch);
        intent.putExtra("intrestedcourese",allCallsResponseList.get(position).interIntrestedCourse);
        intent.putExtra("states",allCallsResponseList.get(position).state);
        intent.putExtra("children",allCallsResponseList.get(position).siblings);
        intent.putExtra("Attempts",allCallsResponseList.get(position).entranceTestAttemts);
        intent.putExtra("count",allCallsResponseList.size());
        intent.putExtra("callfrom","TodayCalls");
        intent.putExtra("case","2");
        intent.putExtra("data",allCallsResponseList.get(position));



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
        return allCallsResponseList.size();
    }

    public class AllCallsHoldeer  extends RecyclerView.ViewHolder{

        public TextView mAllnum,mAllname,mAllLocality,mAllStatus,mcontactnumber;
        public ImageView mInfo;
        SearchView searchView;
        RelativeLayout rel;

        public AllCallsHoldeer(@NonNull View itemView) {
            super(itemView);
            rel = (RelativeLayout) itemView.findViewById(R.id.relative);
            mAllname = (TextView) itemView.findViewById(R.id.tvallname);
            //mAllnum= (TextView)itemView.findViewById(R.id.tvallphnnumber);
            mAllLocality= (TextView)itemView.findViewById(R.id.tvalllocality);
            mAllStatus =(TextView)itemView.findViewById(R.id.tvallstatus);
            mInfo =(ImageView)itemView.findViewById(R.id.imginfo);
           // mcontactnumber = (TextView)itemView.findViewById(R.id.tvnumber);

        }
    }


 public String setColort(String color){

      return "";
 }


 public  void updateList( List<NewAllCallsResponse> newList){

     allCallsResponseList = new ArrayList<>();
     allCallsResponseList.addAll(newList);
     notifyDataSetChanged();

 }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private  Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {





            List<NewAllCallsResponse> filteredList = new ArrayList<>();
            if (constraint == null  || constraint.length() ==0 ){



                filteredList.addAll(allCallsResponseListFull);


                //Toast.makeText(mContext, "List null...", Toast.LENGTH_SHORT).show();
            }else
            {
                String filterpattren =constraint.toString().toLowerCase().trim();

                for (NewAllCallsResponse item : allCallsResponseListFull){

                    if (item.leadName.toLowerCase().contains(filterpattren)){
                        filteredList.add(item);
                    } else if (item.contactNumber.contains(filterpattren)){
                        filteredList.add(item);
                    }else if (item.fathercontact.contains(filterpattren)){
                        filteredList.add(item);
                    }else if (item.mathercon.contains(filterpattren)){
                        filteredList.add(item);
                    }

                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return  results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            allCallsResponseList.clear();
            allCallsResponseList.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };



}
