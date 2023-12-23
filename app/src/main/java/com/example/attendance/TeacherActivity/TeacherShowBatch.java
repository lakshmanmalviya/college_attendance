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

import com.example.attendance.Adapters.BatchAdapter;
import com.example.attendance.Adapters.CollegeAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.BatchModal;
import com.example.attendance.Modals.CollegeModal;
import com.example.attendance.databinding.ActivityTeacherShowBatchBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherShowBatch extends AppCompatActivity {
    ActivityTeacherShowBatchBinding bnd;
    BatchAdapter batchAdapter;
    ArrayList<BatchModal> clist;
    FirebaseDatabase database;
    final String allCollege = "allCollege",allBatch = "allBatch";
    String collegeId = "collegeId",batchId = "batchId";
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowBatchBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        collegeId = getIntent().getStringExtra(collegeId);
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        batchAdapter = new BatchAdapter(clist,getApplicationContext());
        bnd.showBatchRec.setAdapter(batchAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.showBatchRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allBatch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        BatchModal batchModal = dataSnapshot.getValue(BatchModal.class);

                        if(batchModal.getCollegeId().equalsIgnoreCase(collegeId)){

                        if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
                            String[] strA= batchModal.getBatchName().split("-");
                            if(getIntent().getStringExtra("stPossword").toLowerCase().contains(strA[1].trim().toLowerCase())){
                                clist.add(batchModal);
                            }
                        }
                        else{
                            clist.add(batchModal);
                          }
                        }
                    }
                    batchAdapter.notifyDataSetChanged();
                    bnd.progressBar9.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bnd.showBatchRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showBatchRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BatchModal batchModal = clist.get(position);
                if("branchClick".equals(getIntent().getStringExtra("branchClick"))){
                    Intent intent = new Intent(getApplicationContext(),AddBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    startActivity(intent);
                }
                if("semClick".equals(getIntent().getStringExtra("semClick"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("semClick"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("semClick","semClick");
                    startActivity(intent);
                }
                if("subClick".equals(getIntent().getStringExtra("subClick"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("subClick"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("subClick","subClick");
                    startActivity(intent);
                }
                if("stuClick".equals(getIntent().getStringExtra("stuClick"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("stuClick"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("stuClick","stuClick");
                    startActivity(intent);
                }
                if("attendance".equals(getIntent().getStringExtra("attendance"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("attendance","attendance");
                    startActivity(intent);
                }
                if("student".equals(getIntent().getStringExtra("student"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("student","student");
                    startActivity(intent);
                }
                if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("studentlog","studentlog");
                    intent.putExtra("tempTId",getIntent().getStringExtra("tempTId"));
                    intent.putExtra("tempTLocation",getIntent().getStringExtra("tempTLocation"));
                    intent.putExtra("stPossword",getIntent().getStringExtra("stPossword"));
                    intent.putExtra("stEnroll",getIntent().getStringExtra("stEnroll"));
                    startActivity(intent);
                }
                if("newSem".equals(getIntent().getStringExtra("newSem"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
                    intent.putExtra("newSem","newSem");
                    startActivity(intent);
                }
                if("updateSub".equals(getIntent().getStringExtra("updateSub"))){
//                    Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),TeacherShowBranch.class);
                    intent.putExtra("collegeId",collegeId);
                    intent.putExtra(batchId,batchModal.getBatchId());
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