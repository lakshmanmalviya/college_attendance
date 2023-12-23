package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.CollegeModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class CollegeAdapter extends RecyclerView.Adapter<CollegeAdapter.collegeHolder> {
    ArrayList<CollegeModal> clist;
    Context context;

    public CollegeAdapter(ArrayList<CollegeModal> clist, Context context) {
        this.clist = clist;
        this.context = context;
    }

    @NonNull
    @Override
    public collegeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_single_row,parent,false);
        return  new collegeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull collegeHolder holder, int position) {
          CollegeModal collegeModal = clist.get(position);
          holder.collegeName.setText(collegeModal.getCollege());
    }

    @Override
    public int getItemCount() {
        return clist.size();
    }

    class collegeHolder extends RecyclerView.ViewHolder {
        TextView collegeName;
        public collegeHolder(@NonNull View itemView) {
            super(itemView);
            collegeName = itemView.findViewById(R.id.collegeNameRow);

        }
    }
}
