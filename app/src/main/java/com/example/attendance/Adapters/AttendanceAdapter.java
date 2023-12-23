package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.AttendanceModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceHolder> {
    ArrayList<AttendanceModal> alist;
    Context context;
    public AttendanceAdapter(ArrayList<AttendanceModal> alist, Context context) {
        this.alist = alist;
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_detail_row, parent, false);
        return new AttendanceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceHolder holder, int position) {
        AttendanceModal attendanceModal = alist.get(position);
        if(position==0){
            holder.takenBy.setText("Taken By \n\n"+attendanceModal.getTakenBy());
            holder.dateTime.setText("Date and Time \n\n"+attendanceModal.getDayTime());
        }
        else {
            holder.takenBy.setText(attendanceModal.getTakenBy());
            holder.dateTime.setText(attendanceModal.getDayTime());
        }
    }
    @Override
    public int getItemCount() {
        return alist.size();
    }
    class AttendanceHolder extends RecyclerView.ViewHolder {
        TextView takenBy,dateTime;
        public AttendanceHolder(@NonNull View itemView) {
            super(itemView);
            takenBy = itemView.findViewById(R.id.takenBy);
            dateTime = itemView.findViewById(R.id.dateOfDay);
        }
    }
}
