package com.example.attendance.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.StudentModal;
import com.example.attendance.R;

import java.util.ArrayList;

public class StStudentAdapter extends RecyclerView.Adapter<StStudentAdapter.StStudentHolder> {
   ArrayList<StudentModal> slist;
   Context context;
   int lastPostion=-1;
    public StStudentAdapter(ArrayList<StudentModal> slist, Context context) {
        this.slist = slist;
        this.context = context;
    }
    @NonNull
    @Override
    public StStudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.st_student_row,parent,false);
        return new StStudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StStudentHolder holder,  int position) {
        StudentModal studentModal = slist.get(position);
        if (position==0){
            holder.studentName.setText("Student Name \n\n"+studentModal.getStudentName());
            holder.studentRoll.setText("EnrollNum \n\n"+studentModal.getStudentRoll());
            holder.studentAttendance.setText("Attendance  \n\n"+studentModal.getStudentAttendance()+" %");
            holder.studentDayCame.setText("DayCame \n\n"+studentModal.getStudentDayCame()+" Day Came");
            holder.totalCollegeDay.setText("TotalCollegeDay \n\n"+studentModal.getTotalCollegeDay()+" TotalDay");
        }
        else{
            holder.studentName.setText(studentModal.getStudentName());
            holder.studentRoll.setText(studentModal.getStudentRoll());
            holder.studentAttendance.setText(studentModal.getStudentAttendance()+" %");
            holder.studentDayCame.setText(studentModal.getStudentDayCame()+" Day Came");
            holder.totalCollegeDay.setText(studentModal.getTotalCollegeDay()+" TotalDay");
        }
        if (position>lastPostion){
            lastPostion=position;
          holder.itemView.setAnimation(getAnimation());
        }
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    class StStudentHolder extends RecyclerView.ViewHolder {
        TextView studentName,studentRoll,studentAttendance,studentDayCame,totalCollegeDay;
        public StStudentHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.stStudentNameRow);
            studentRoll = itemView.findViewById(R.id.stEnrollRow);
            studentAttendance = itemView.findViewById(R.id.stAttendanceRow);
            studentDayCame = itemView.findViewById(R.id.stCameDayRow);
            totalCollegeDay = itemView.findViewById(R.id.stTotalDayRow);
        }
    }
    public Animation getAnimation(){
        Animation animation =  AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animation.setDuration(700);
        return animation;
    }
}
