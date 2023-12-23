package com.example.attendance.TeacherActivity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Adapters.StStudentAdapter;
import com.example.attendance.Adapters.StudentAdapter;
import com.example.attendance.Modals.StudentModal;
import com.example.attendance.R;
import com.example.attendance.databinding.ActivityTeacherShowAllPresentBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherShowAllPresent extends AppCompatActivity {
  ActivityTeacherShowAllPresentBinding bnd;
    StStudentAdapter studentAdapter;
    ArrayList<StudentModal> clist;
    ArrayList<String> strList = new ArrayList<>();
    FirebaseDatabase database;
    final String allStudent = "allStudent",allPresent="allPresent",allSub="allSub";
    String subId = "subId", studentId = "studentId", tempTId = "tempTId";
    GridLayoutManager gridLayoutManager;
    Map<String, Object> map = new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowAllPresentBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        subId = getIntent().getStringExtra(subId);
        map.put("subId", subId);
        database = FirebaseDatabase.getInstance();
        clist = new ArrayList<>();
        studentAdapter = new StStudentAdapter(clist, getApplicationContext());
        bnd.showPresentRec.setAdapter(studentAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        bnd.showPresentRec.setLayoutManager(gridLayoutManager);


        database.getReference().child(allSub).child(subId).child(allPresent).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                strList.clear();
                if (snapshot.exists()){
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        strList.add(dataSnapshot.getValue(String.class));
                        Log.d(TAG, "onDataChange: ====>>"+dataSnapshot.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error occured"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        database.getReference().child(allStudent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    clist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StudentModal subModal = dataSnapshot.getValue(StudentModal.class);
                        if (subModal.getSubId().equalsIgnoreCase(subId) && strList.contains(subModal.getStudentId())) {
                            clist.add(subModal);
                        }
                    }
                    studentAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}