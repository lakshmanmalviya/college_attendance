package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.BatchModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class BatchAdapter extends RecyclerView.Adapter<BatchAdapter.batchHolder>{
    ArrayList<BatchModal> blist;
    Context context;

    public BatchAdapter(ArrayList<BatchModal> blist, Context context) {
        this.blist = blist;
        this.context = context;
    }

    @NonNull
    @Override
    public batchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  view = LayoutInflater.from(context).inflate(R.layout.all_single_row,parent,false);
        return new batchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull batchHolder holder, int position) {
       BatchModal batchModal = blist.get(position);
       holder.batchName.setText(batchModal.getBatchName());
    }

    @Override
    public int getItemCount() {
        return blist.size();
    }

    class batchHolder extends RecyclerView.ViewHolder{
        TextView batchName;
        public batchHolder(@NonNull View itemView) {
            super(itemView);
            batchName = itemView.findViewById(R.id.collegeNameRow);
        }
    }
}
