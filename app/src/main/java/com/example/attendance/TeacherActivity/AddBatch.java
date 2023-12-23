package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.databinding.ActivityAddBatchBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddBatch extends AppCompatActivity {
    ActivityAddBatchBinding bnd;
    String allCollege = "allCollege";
    String allBatch = "allBatch";
    String batchName = "batchName";
    String batchId = "batchId";
    String collegeId = "collegeId";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        bnd = ActivityAddBatchBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        bnd.tInsertBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.tBatchName.getText().toString().trim().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    myref = myDatabase.getReference().child(allBatch).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(batchName, bnd.tBatchName.getText().toString());
                    map.put(collegeId, getIntent().getStringExtra(collegeId));
                    map.put(batchId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The batch is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
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