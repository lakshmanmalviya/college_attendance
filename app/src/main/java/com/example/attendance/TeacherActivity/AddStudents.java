package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.databinding.ActivityAddStudentsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddStudents extends AppCompatActivity {
   ActivityAddStudentsBinding bnd;
    final String allStudent = "allStudent";
    String subName = "subName";
    String subId = "subId";
    String studentName = "studentName";
    String studentRoll = "studentRoll";
    String studentAttendance = "studentAttendance";
    String studentDayCame = "studentDayCame";
    String totalCollegeDay = "totalCollegeDay";
    String studentId = "studentId";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAddStudentsBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        subId = getIntent().getStringExtra(subId);
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        bnd.tInsertStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.tStudentName.getText().toString().trim().isEmpty() ||bnd.tStudentRoll.getText().toString().trim().isEmpty()||bnd.tStudentDayCame.getText().toString().trim().isEmpty() ||bnd.tStudentPercent.getText().toString().trim().isEmpty() ||bnd.totalCollegeDay.getText().toString().trim().isEmpty()  ){
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allStudent).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(studentName, bnd.tStudentName.getText().toString());
                    map.put(studentRoll, bnd.tStudentRoll.getText().toString());
                    map.put(studentAttendance, bnd.tStudentPercent.getText().toString());
                    map.put(studentDayCame, bnd.tStudentDayCame.getText().toString());
                    map.put(totalCollegeDay, bnd.totalCollegeDay.getText().toString());
                    map.put(studentId,myref.getKey());
                    map.put("subId",subId);
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The student is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), "Failed"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}