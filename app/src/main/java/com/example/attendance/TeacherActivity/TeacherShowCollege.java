package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Adapters.CollegeAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.CollegeModal;
import com.example.attendance.R;
import com.example.attendance.databinding.ActivityTeacherShowCollegeBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TeacherShowCollege extends AppCompatActivity {
   ActivityTeacherShowCollegeBinding bnd;
   CollegeAdapter collegeAdapter;
   ArrayList<CollegeModal> clist;
   FirebaseDatabase database;
  final String allCollege = "allCollege";
   String collegeId = "collegeId",tempTId="tempTId";
   GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowCollegeBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        collegeAdapter = new CollegeAdapter(clist,getApplicationContext());
        bnd.TshowClgRec.setAdapter(collegeAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.TshowClgRec.setLayoutManager(gridLayoutManager);
        database.getReference().child(allCollege).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        CollegeModal collegeModal = dataSnapshot.getValue(CollegeModal.class);
                        if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
                          if(getIntent().getStringExtra("stPossword").toLowerCase().contains(collegeModal.getCollege().trim().toLowerCase())){
                                clist.add(collegeModal);
                            }
                        }
                        else{

                            clist.add(collegeModal);
                        }
                    }
                    collegeAdapter.notifyDataSetChanged();
                    bnd.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        bnd.TshowClgRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.TshowClgRec, new RecyclerItemClickListener.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    CollegeModal collegeModal = clist.get(position);
                    if ("batchClick".equals(getIntent().getStringExtra("batchClick"))){
//                      Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("batchClick"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), AddBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        startActivity(intent);
                    }
                    if ("branchClick".equals(getIntent().getStringExtra("branchClick"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("branchClick"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("branchClick","branchClick");
                        startActivity(intent);
                    }
                    if ("semClick".equals(getIntent().getStringExtra("semClick"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("semClick"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("semClick","semClick");
                        startActivity(intent);
                    }
                    if ("subClick".equals(getIntent().getStringExtra("subClick"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("subClick"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("subClick","subClick");
                        startActivity(intent);
                    }
                    if ("stuClick".equals(getIntent().getStringExtra("stuClick"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("stuClick"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("stuClick","stuClick");
                        startActivity(intent);
                    }
                    if ("attendance".equals(getIntent().getStringExtra("attendance"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("attendance","attendance");
                        startActivity(intent);
                    }
                    if ("student".equals(getIntent().getStringExtra("student"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("student","student");
                        startActivity(intent);
                    }
                    if ("studentlog".equals(getIntent().getStringExtra("studentlog"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("studentlog","studentlog");
                        intent.putExtra("tempTId",getIntent().getStringExtra("tempTId"));
                        intent.putExtra("tempTLocation",getIntent().getStringExtra("tempTLocation"));
                        intent.putExtra("stPossword",getIntent().getStringExtra("stPossword"));
                        intent.putExtra("stEnroll",getIntent().getStringExtra("stEnroll"));
                        startActivity(intent);
                    }
                    if ("newSem".equals(getIntent().getStringExtra("newSem"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
                        intent.putExtra("newSem","newSem");
                        startActivity(intent);
                    }
                    if ("updateSub".equals(getIntent().getStringExtra("updateSub"))){
//                        Toast.makeText(getApplicationContext(), ""+getIntent().getStringExtra("attendance"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), TeacherShowBatch.class);
                        intent.putExtra(collegeId, collegeModal.getCollegeId());
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