package com.example.mahesh.wisdomoverseas.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.activities.SearchActivity;
import com.example.mahesh.wisdomoverseas.activities.SearchActivity2;
import com.example.mahesh.wisdomoverseas.models.Newresponses.IncomingResponse;

import java.util.List;

public class IncomingcallAdapter  extends  RecyclerView.Adapter<IncomingcallAdapter.IncomingViewHolder>{

    private LayoutInflater layoutInflater;
    private Context mContext;
    List<IncomingResponse> incomingResponseList;
    android.widget.SearchView searchView;


    public IncomingcallAdapter(Context mContext, List<IncomingResponse> incomingResponseList) {
        this.mContext = mContext;
        this.incomingResponseList = incomingResponseList;
    }

    @NonNull
    @Override
    public IncomingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.incominglist,viewGroup,false);
        return new IncomingViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final IncomingViewHolder incomingViewHolder, final int i) {

        TextView number, date,calltype;
        number = incomingViewHolder.number;
        date = incomingViewHolder .date;
        calltype = incomingViewHolder.calltype;
        number.setText(incomingResponseList.get(i).getNumber());
        date.setText(incomingResponseList.get(i).getDate());
        calltype.setText(incomingResponseList.get(i).getDir());
        //Log.e("call status",""+incomingResponseList.get(i).getDir());
        incomingViewHolder.mImgsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SearchActivity2.class);
                //searchView.setOnQueryTextListener();

                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Phonenumber",incomingResponseList.get(i).getNumber());

                mContext.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
       // Toast.makeText(mContext, "calllistsize"+incomingResponseList.size(), Toast.LENGTH_SHORT).show();
        return incomingResponseList.size();
    }

    public  class  IncomingViewHolder extends RecyclerView.ViewHolder {

  TextView number,date,calltype;
  ImageView mImgsearch;

         public IncomingViewHolder(@NonNull View itemView) {
             super(itemView);

             number = itemView.findViewById(R.id.tvnumber);
             date = itemView.findViewById(R.id.tvdate);
             mImgsearch = itemView.findViewById(R.id.incomingimgsearch);
             calltype = itemView.findViewById(R.id.tvcalltype);
         }
     }
}
