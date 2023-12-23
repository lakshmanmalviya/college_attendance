package com.example.attendance.StudentActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.attendance.Adapters.StStudentAdapter;
import com.example.attendance.Adapters.StudentAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.SemModal;
import com.example.attendance.Modals.StudentModal;
import com.example.attendance.R;
import com.example.attendance.TeacherActivity.AddSubject;
import com.example.attendance.TeacherActivity.TeacherShowSubject;
import com.example.attendance.databinding.ActivityStudentSeeAttendanceBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;

public class StudentSeeAttendance extends AppCompatActivity {
    ActivityStudentSeeAttendanceBinding bnd;
    StStudentAdapter studentAdapter;
    ArrayList<StudentModal> clist;
    FirebaseDatabase database;
    GridLayoutManager gridLayoutManager;
    final String allCollege = "allCollege",allBatch = "allBatch",allBranch = "allBranch";
    final String allSem = "allSem",allSub = "allSub",allStudent = "allStudent";
    String collegeId="collegeId",batchId="batchId",branchId="branchId",semId="semId",subId="subId";
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityStudentSeeAttendanceBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        subId= getIntent().getStringExtra(subId);
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        studentAdapter = new StStudentAdapter(clist,getApplicationContext());
        bnd.studentRec.setAdapter(studentAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.studentRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allStudent).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        StudentModal subModal = dataSnapshot.getValue(StudentModal.class);
                        if (subModal.getSubId().equalsIgnoreCase(subId)){
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
        bnd.noticeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            bnd.noticeLayout.setVisibility(View.GONE);
            }
        });
        bnd.allAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AllStudentFinalAttendance.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("slist",clist);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
//
        bnd.studentRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.studentRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(getApplicationContext(), "Long press to see more details", Toast.LENGTH_SHORT).show();
                showMessage(view,"Press long to see more details with dates and all",R.drawable.user);
            }
            @Override
            public void onLongItemClick(View view, int position) {
                    StudentModal studentModal = clist.get(position);
                    Intent intent = new Intent(getApplicationContext(),StudentDetail.class);
                    intent.putExtra("subId",subId);
                    intent.putExtra("totalDay",studentModal.getTotalCollegeDay());
                    intent.putExtra("studentId",studentModal.getStudentId());
                    startActivity(intent);
            }
        }));
    }
    public void showMessage(View v, String msg, int imageView) {
        Toast toast = new Toast(getApplicationContext());
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.toast_msg, v.findViewById(R.id.toastLay));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        TextView toastMg = view.findViewById(R.id.toastMsg);
        toastMg.setText(msg);
        ImageView toastImg = view.findViewById(R.id.toastImg);
        toastImg.setImageResource(imageView);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }
}