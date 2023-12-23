package com.example.attendance.TeacherActivity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Adapters.StudentAdapter;
import com.example.attendance.Adapters.SubAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.StudentModal;
import com.example.attendance.Modals.SubModal;
import com.example.attendance.databinding.ActivityTeacherShowStudentsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherShowStudents extends AppCompatActivity {
    ActivityTeacherShowStudentsBinding bnd;
    StudentAdapter studentAdapter;
    ArrayList<StudentModal> clist;
    FirebaseDatabase database;
    final String allStudent = "allStudent",allSub="allSub";
    String subId = "subId", studentId = "studentId", tempTId = "tempTId";
    GridLayoutManager gridLayoutManager;
    Map<String, Object> map = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowStudentsBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        subId = getIntent().getStringExtra(subId);//strArr[4] =
        tempTId = getIntent().getStringExtra(tempTId);
        map.put("tempTId", tempTId);
        map.put("subId", subId);
        if (tempTId != null) {
            bnd.isOpenCollege.setVisibility(View.GONE);
        }
        if (tempTId == null) {
            tempTId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        }
        database = FirebaseDatabase.getInstance();
        clist = new ArrayList<>();
        studentAdapter = new StudentAdapter(clist, getApplicationContext(), map);
        bnd.showStudRec.setAdapter(studentAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        bnd.showStudRec.setLayoutManager(gridLayoutManager);
        showTotalPresent();
        bnd.isOpenCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAll();
                bnd.isOpenCollege.setVisibility(View.GONE);
                bnd.hideLayout.setVisibility(View.VISIBLE);
            }
        });
        bnd.totalPresent.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(),TeacherShowAllPresent.class);
            intent.putExtra("subId",subId);
            startActivity(intent);
        });
        database.getReference().child(allStudent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    clist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        StudentModal subModal = dataSnapshot.getValue(StudentModal.class);
                        if (subModal.getSubId().equalsIgnoreCase(subId)) {
                            Log.d(TAG, "onDataChangeName --: ===>>" + subModal.getStudentRoll() + " here " + getIntent().getStringExtra("stEnroll"));
                            if ("studentlog".equals(getIntent().getStringExtra("studentlog"))) {
                                if (getIntent().getStringExtra("stEnroll").toLowerCase().trim().contains(subModal.getStudentRoll().trim().toLowerCase())) {
                                    clist.add(subModal);
                                }
                            } else {
                                clist.add(subModal);
                            }
                        }
                    }
                    studentAdapter.notifyDataSetChanged();
                    bnd.openCollegeLayout.setVisibility(View.VISIBLE);
                    bnd.progressBar5.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bnd.showStudRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showStudRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                StudentModal subModal = clist.get(position);
                if ("stuClick".equals(getIntent().getStringExtra("stuClick"))) {
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("stuClick"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), AddStudents.class);
//                    intent.putExtra("collegeId",collegeId);
//                    intent.putExtra("batchId",batchId);
//                    intent.putExtra("branchId",branchId);
//                    intent.putExtra("semId",semId);
                    intent.putExtra(subId, subModal.getStudentId());
                    startActivity(intent);
                }
                if ("attendance".equals(getIntent().getStringExtra("attendance"))) {
                    Toast.makeText(getApplicationContext(), "" + getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), TeacherShowStudents.class);
//                    intent.putExtra("collegeId",collegeId);
//                    intent.putExtra("batchId",batchId);
//                    intent.putExtra("branchId",branchId);
//                    intent.putExtra("semId",semId);
                    intent.putExtra(studentId, subModal.getStudentId());
                    startActivity(intent);
                }
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }

    public void updateAll() {
//      updating
        for (StudentModal studentModal : clist) {
            Log.d("StudentName ======>>>", studentModal.getStudentName());
            database.getReference().child(allStudent).child(studentModal.getStudentId())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                map.clear();
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    if (dataSnapshot.getKey().equalsIgnoreCase("totalCollegeDay")) {
                                        map.put(dataSnapshot.getKey(), (Integer.parseInt(dataSnapshot.getValue().toString()) + 1) + "");
                                    } else {
                                        map.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
                                    }
                                }
                                int calculate = (Integer.parseInt(map.get("studentDayCame").toString())) * 100 / Integer.parseInt(map.get("totalCollegeDay").toString());
                                Log.d(TAG, "calculate " + "========>>>>>" + calculate);
                                map.put("studentAttendance", calculate + "");
                                updateCollegeDay(studentModal.getStudentId(), map);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });
        }
    }

    public void updateCollegeDay(String sId, Map<String, Object> map) {
        database.getReference().child(allStudent).child(sId)
                .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void showTotalPresent(){
        database.getReference().child(allSub).child(subId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                        Log.d("TAG", "onDataChange: ======>>"+dataSnapshot.getValue());
                        if (dataSnapshot.getKey().equalsIgnoreCase("totalPresent")){

                           bnd.totalPresent.setText("Total present students are "+dataSnapshot.getValue(String.class));
                        }
                    }
                }
//                Toast.makeText(context.getApplicationContext(), "value is "+totalPresent+""+lastDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(getApplicationContext(), "If attendance is completed \n then exit by Exit Button", Toast.LENGTH_SHORT).show();
        super.onBackPressed();
    }
}