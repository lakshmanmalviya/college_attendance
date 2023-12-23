package com.example.attendance.TeacherActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.attendance.Adapters.BranchAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.BranchModal;
import com.example.attendance.databinding.ActivityTeacherShowBranchBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherShowBranch extends AppCompatActivity {
    ActivityTeacherShowBranchBinding bnd;
    BranchAdapter branchAdapter;
    ArrayList<BranchModal> clist;
    FirebaseDatabase database;
    final String allBranch = "allBranch";
    String batchId = "batchId",branchId = "branchId";
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowBranchBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        batchId = getIntent().getStringExtra(batchId);
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        branchAdapter = new BranchAdapter(clist,getApplicationContext());
        bnd.showBranchRec.setAdapter(branchAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.showBranchRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allBranch).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        BranchModal batchModal = dataSnapshot.getValue(BranchModal.class);
                        if(batchModal.getBatchId().equalsIgnoreCase(batchId)){
                            if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
                                String[] strA= batchModal.getBranchName().split(" ");
                                if(getIntent().getStringExtra("stPossword").toLowerCase().contains(strA[0].trim().toLowerCase())){
                                    clist.add(batchModal);
                                }
                            }
                            else{
                                clist.add(batchModal);
                            }
                        }

                    }
                    branchAdapter.notifyDataSetChanged();
                    bnd.progressBar10.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bnd.showBranchRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showBranchRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                BranchModal batchModal = clist.get(position);
                if("semClick".equals(getIntent().getStringExtra("semClick"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherAddSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    startActivity(intent);
                }
                if("subClick".equals(getIntent().getStringExtra("subClick"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    intent.putExtra("subClick","subClick");
                    startActivity(intent);
                }
                if("stuClick".equals(getIntent().getStringExtra("stuClick"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    intent.putExtra("stuClick","stuClick");
                    startActivity(intent);
                }
                if("attendance".equals(getIntent().getStringExtra("attendance"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    intent.putExtra("attendance","attendance");
                    startActivity(intent);
                }
                if("student".equals(getIntent().getStringExtra("student"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    intent.putExtra("student","student");
                    startActivity(intent);
                }
                if("studentlog".equals(getIntent().getStringExtra("studentlog"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    intent.putExtra("studentlog","studentlog");
                    intent.putExtra("tempTId",getIntent().getStringExtra("tempTId"));
                    intent.putExtra("tempTLocation",getIntent().getStringExtra("tempTLocation"));
                    intent.putExtra("stPossword",getIntent().getStringExtra("stPossword"));
                    intent.putExtra("stEnroll",getIntent().getStringExtra("stEnroll"));
                    startActivity(intent);
                }
                if("newSem".equals(getIntent().getStringExtra("newSem"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
                    intent.putExtra("newSem","newSem");
                    startActivity(intent);
                }
                if("updateSub".equals(getIntent().getStringExtra("updateSub"))) {
                    Intent intent = new Intent(getApplicationContext(), TeacherShowSem.class);
                    intent.putExtra("batchId", batchId);
                    intent.putExtra(branchId, batchModal.getBranchId());
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