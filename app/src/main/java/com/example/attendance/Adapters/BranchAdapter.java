package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.BranchModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class BranchAdapter extends RecyclerView.Adapter<BranchAdapter.branchHolder> {
    ArrayList<BranchModal> blist;
    Context context;
    public BranchAdapter(ArrayList<BranchModal> blist, Context context) {
        this.blist = blist;
        this.context = context;
    }

    @NonNull
    @Override
    public branchHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_single_row,parent,false);
        return new branchHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull branchHolder holder, int position) {
      BranchModal branchModal = blist.get(position);
      holder.branchName.setText(branchModal.getBranchName());
    }

    @Override
    public int getItemCount() {
        return blist.size();
    }

    class  branchHolder extends RecyclerView.ViewHolder {
        TextView branchName;
        public branchHolder(@NonNull View itemView) {
            super(itemView);
            branchName = itemView.findViewById(R.id.collegeNameRow);
        }
    }
}
