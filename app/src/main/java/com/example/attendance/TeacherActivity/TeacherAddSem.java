package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.databinding.ActivityTeacherAddSemBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class TeacherAddSem extends AppCompatActivity {
       ActivityTeacherAddSemBinding bnd;
       final String allSem = "allSem";
       String branchId = "branchId";
       String semName = "semName";
       String semId = "semId";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherAddSemBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        branchId = getIntent().getStringExtra(branchId);
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        bnd.tInsertSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.tSemeName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allSem).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(semName, bnd.tSemeName.getText().toString());
                    map.put("branchId", branchId);
                    map.put(semId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The semester is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
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