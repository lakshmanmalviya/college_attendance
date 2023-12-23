package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.SemModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class SemAdapter extends RecyclerView.Adapter<SemAdapter.semHolder> {
   ArrayList<SemModal> semlist;
   Context context;
    public SemAdapter(ArrayList<SemModal> semlist, Context context) {
        this.semlist = semlist;
        this.context = context;
    }

    @NonNull
    @Override
    public semHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_single_row, parent,false);
        return new semHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull semHolder holder, int position) {
       SemModal semModal = semlist.get(position);
       holder.semName.setText(semModal.getSemName());
    }
    @Override
    public int getItemCount() {
        return semlist.size();
    }

    class semHolder extends RecyclerView.ViewHolder {
        TextView semName;
        public semHolder(@NonNull View itemView) {
            super(itemView);
            semName = itemView.findViewById(R.id.collegeNameRow);
        }
    }
}
