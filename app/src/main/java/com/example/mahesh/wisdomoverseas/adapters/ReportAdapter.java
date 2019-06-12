package com.example.mahesh.wisdomoverseas.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mahesh.wisdomoverseas.R;
import com.example.mahesh.wisdomoverseas.models.responses.ReportResponse;

import java.util.List;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportHolder> {



    @NonNull
    @Override
    public ReportHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        /*View v;
        v= LayoutInflater.from(mcntx).inflate(R.layout.)*/
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportHolder reportHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ReportHolder extends RecyclerView.ViewHolder{

        public ReportHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
