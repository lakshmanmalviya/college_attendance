package com.example.attendance.StudentActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.attendance.Adapters.AttendanceAdapter;
import com.example.attendance.Modals.AttendanceModal;
import com.example.attendance.databinding.ActivityStudentDetailBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;

public class StudentDetail extends AppCompatActivity {

    ActivityStudentDetailBinding bnd;
    AttendanceAdapter attendanceAdapter,attendanceAdapter2;
    ArrayList<AttendanceModal> clist,clist2;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager,gridLayoutManager2;
    final String attendance = "attendance",myAddAttendance = "myAddAttendance",myMinusAttendance = "myMinusAttendance";
    String studentId ="studentId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityStudentDetailBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        studentId= getIntent().getStringExtra(studentId);
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        clist2= new ArrayList<>();
        attendanceAdapter = new AttendanceAdapter(clist,getApplicationContext());
        attendanceAdapter2 = new AttendanceAdapter(clist2,getApplicationContext());
        bnd.studentDetailRec.setAdapter(attendanceAdapter);
        bnd.studentDetailMinusRec.setAdapter(attendanceAdapter2);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        gridLayoutManager2 = new GridLayoutManager(getApplicationContext(),1);
        bnd.studentDetailRec.setLayoutManager(gridLayoutManager);
        bnd.studentDetailMinusRec.setLayoutManager(gridLayoutManager2);
        database.getReference().child(attendance).child(studentId).child(myAddAttendance).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        AttendanceModal attendanceModal = dataSnapshot.getValue(AttendanceModal.class);
                        clist.add(attendanceModal);
                    }
                    calcExactAttendance(clist,"My Positive/final Attendance is ",bnd.positive);
                    attendanceAdapter.notifyDataSetChanged();
                    bnd.positive.setVisibility(View.VISIBLE);
                    bnd.negative.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        database.getReference().child(attendance).child(studentId).child(myMinusAttendance).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist2.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        AttendanceModal attendanceModal = dataSnapshot.getValue(AttendanceModal.class);
                        clist2.add(attendanceModal);
                    }
                    calcExactAttendance(clist2,"My Negative Attendance is ",bnd.negative);
                    attendanceAdapter2.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void calcExactAttendance(ArrayList<AttendanceModal> list, String msg, TextView v){
        int atCount =0;
        for (int i=1; i<list.size(); i++){
          String [] strArr = list.get(i-1).getDayTime().split(" ");
          String  month= strArr[1];
          String  date = strArr[2];
          String [] strArr2 = list.get(i).getDayTime().split(" ");
          String  month2= strArr2[1];
          String  date2 = strArr2[2];
            if (!month.equalsIgnoreCase(month2) || !date.equalsIgnoreCase(date2)){
                atCount++;
            }
        }
        int exactAttedance = (atCount+1)*100/(Integer.parseInt(getIntent().getStringExtra("totalDay")));
        v.setText(msg+exactAttedance+" %");
    }
}