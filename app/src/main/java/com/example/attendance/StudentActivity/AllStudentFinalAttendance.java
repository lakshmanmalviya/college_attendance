package com.example.attendance.StudentActivity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.Adapters.AttendanceAdapter;
import com.example.attendance.Adapters.StStudentAdapter;
import com.example.attendance.Modals.AttendanceModal;
import com.example.attendance.Modals.StudentModal;
import com.example.attendance.R;
import com.example.attendance.databinding.ActivityAllStudentFinalAttendanceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllStudentFinalAttendance extends AppCompatActivity {
    ActivityAllStudentFinalAttendanceBinding bnd;
    AttendanceAdapter attendanceAdapter,attendanceAdapter2;
    ArrayList<AttendanceModal> clist,clist2;
    StStudentAdapter studentAdapter;
    ArrayList<StudentModal> studentList;
    ArrayList<StudentModal> tempStudentList;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager;
    final String attendance = "attendance",myAddAttendance = "myAddAttendance",myMinusAttendance = "myMinusAttendance";
    final String allSem = "allSem",allSub = "allSub",allStudent = "allStudent";
    String  semId="semId",subId="subId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd =ActivityAllStudentFinalAttendanceBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        studentList = (ArrayList<StudentModal>) getIntent().getSerializableExtra("slist");
        tempStudentList= new ArrayList<>();
        studentAdapter = new StStudentAdapter(tempStudentList,getApplicationContext());
        bnd.allAttendanceRec.setAdapter(studentAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.allAttendanceRec.setLayoutManager(gridLayoutManager);
    for (StudentModal studentModal : studentList){
        Log.d(TAG, "onCreate:======>>> "+studentModal.getStudentName());
        database.getReference().child(attendance).child(studentModal.getStudentId()).child(myAddAttendance).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        AttendanceModal attendanceModal = dataSnapshot.getValue(AttendanceModal.class);
                        clist.add(attendanceModal);
                        Log.d(TAG, "onCreate:======>>> "+attendanceModal.getDayTime());
                    }
                    int tempAttendance = calcExactAttendance(clist,studentModal.getTotalCollegeDay());
                    studentModal.setStudentAttendance(tempAttendance+"");
                    studentModal.setStudentDayCame("***");
                    tempStudentList.add(studentModal);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Some error occured "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        studentAdapter.notifyDataSetChanged();
        bnd.progressBar4.setVisibility(View.GONE);
      }

    }
    public int calcExactAttendance(ArrayList<AttendanceModal> list,String totalDay){
        int tempNGA = 0;
//        calcNegativeAttendance(list,totalDay);
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
        int exactAttedance = (atCount+1)*100/(Integer.parseInt(totalDay));
        return Math.abs(exactAttedance-tempNGA);
    }
    public int calcNegativeAttendance(ArrayList<AttendanceModal> list,String totalDay){
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
        int exactAttedance = (atCount+1)*100/(Integer.parseInt(totalDay));
        return exactAttedance;
    }
}