package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Adapters.BranchAdapter;
import com.example.attendance.Adapters.SemAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.BranchModal;
import com.example.attendance.Modals.SemModal;
import com.example.attendance.databinding.ActivityTeacherShowSemBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherShowSem extends AppCompatActivity {
     ActivityTeacherShowSemBinding bnd;
    SemAdapter semAdapter;
    ArrayList<SemModal> clist;
    FirebaseDatabase database;
    final String allSem = "allSem";
    String branchId = "branchId",semId = "semId";
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowSemBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        branchId = getIntent().getStringExtra(branchId);
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        semAdapter = new SemAdapter(clist,getApplicationContext());
        bnd.showSemRec.setAdapter(semAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.showSemRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allSem).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        SemModal batchModal = dataSnapshot.getValue(SemModal.class);
                        if(batchModal.getBranchId().equalsIgnoreCase(branchId)){
                            if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
                                String[] strA= batchModal.getSemName().split("-");
                                String []strB = getIntent().getStringExtra("stPossword").toLowerCase().split("-");
                                if(strB[3].trim().equals(strA[1].trim().toLowerCase())){
                                    clist.add(batchModal);
                                }
                            }
                            else{
                                clist.add(batchModal);
                            }
                        }
                    }
                    semAdapter.notifyDataSetChanged();
                    bnd.progressBar12.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bnd.showSemRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showSemRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SemModal batchModal = clist.get(position);
                if("subClick".equals(getIntent().getStringExtra("subClick"))){
                    Intent intent = new Intent(getApplicationContext(),AddSubject.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    startActivity(intent);
                }
                if("stuClick".equals(getIntent().getStringExtra("stuClick"))){
                    Intent intent = new Intent(getApplicationContext(),TeacherShowSubject.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    intent.putExtra("stuClick","stuClick");
                    startActivity(intent);
                }
                if("attendance".equals(getIntent().getStringExtra("attendance"))){
                    Intent intent = new Intent(getApplicationContext(),TeacherShowSubject.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    intent.putExtra("attendance","attendance");
                    startActivity(intent);
                }
                if("student".equals(getIntent().getStringExtra("student"))){
                    Intent intent = new Intent(getApplicationContext(),TeacherShowSubject.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    intent.putExtra("student","student");
                    startActivity(intent);
                }
                if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowSubject.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    intent.putExtra("studentlog","studentlog");
                    intent.putExtra("tempTId",getIntent().getStringExtra("tempTId"));
                    intent.putExtra("tempTLocation",getIntent().getStringExtra("tempTLocation"));
                    intent.putExtra("stPossword",getIntent().getStringExtra("stPossword"));
                    intent.putExtra("stEnroll",getIntent().getStringExtra("stEnroll"));
                    startActivity(intent);
                }
                if("newSem".equals(getIntent().getStringExtra("newSem"))){
                    Intent intent = new Intent(getApplicationContext(),UpdateSemAsNew.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    intent.putExtra("semName",batchModal.getSemName());
                    startActivity(intent);
                }
                if("updateSub".equals(getIntent().getStringExtra("updateSub"))){
                    Intent intent = new Intent(getApplicationContext(),TeacherShowSubject.class);
                    intent.putExtra("branchId",branchId);
                    intent.putExtra(semId,batchModal.getSemId());
                    intent.putExtra("updateSub","updateSub");
                    startActivity(intent);
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
    }
}