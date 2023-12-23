package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.databinding.ActivityAddBatchBinding;
import com.example.attendance.databinding.ActivityAddBranchBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddBranch extends AppCompatActivity {
    ActivityAddBranchBinding bnd;
    final String allCollege = "allCollege";
    final String allBatch = "allBatch";
    final String allBranch = "allBranch";
    String batchName = "batchName";
    String batchId = "batchId";
    String branchName = "branchName";
    String branchId = "branchId";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityAddBranchBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        batchId = getIntent().getStringExtra(batchId);
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        bnd.tInsertBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.tBranchName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allBranch).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(branchName, bnd.tBranchName.getText().toString());
                    map.put(branchId,myref.getKey());
                    map.put("batchId",batchId);
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The branch is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
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