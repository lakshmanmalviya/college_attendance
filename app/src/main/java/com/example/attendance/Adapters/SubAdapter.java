package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.SubModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class SubAdapter extends RecyclerView.Adapter<SubAdapter.SubHolder> {
    ArrayList<SubModal> sublist;
    Context context;

    public SubAdapter(ArrayList<SubModal> sublist, Context context) {
        this.sublist = sublist;
        this.context = context;
    }

    @NonNull
    @Override
    public SubHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_single_row,parent,false);
        return new SubHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubHolder holder, int position) {
     SubModal subModal = sublist.get(position);
     holder.subName.setText(subModal.getSubName());
    }

    @Override
    public int getItemCount() {
        return sublist.size();
    }

    class SubHolder extends RecyclerView.ViewHolder {
         TextView subName;
        public SubHolder(@NonNull View itemView) {
            super(itemView);
            subName = itemView.findViewById(R.id.collegeNameRow);
        }
    }
}
