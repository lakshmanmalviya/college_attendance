package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.databinding.ActivityAddSubjectBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddSubject extends AppCompatActivity {
    ActivityAddSubjectBinding bnd;
    final String allSub = "allSub";
    String semId = "semId",subName = "subName",subId = "subId",lastDate="lastDate",totalPresent="totalPresent";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd= ActivityAddSubjectBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        semId = getIntent().getStringExtra(semId);
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        bnd.tInsertSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.tSubName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allSub).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(subName, bnd.tSubName.getText().toString());
                    map.put(lastDate, new Date()+"");
                    map.put(totalPresent, 0+"");
                    map.put(subId,myref.getKey());
                    map.put("semId",semId);
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The subject is inserted 😍 🤩", Toast.LENGTH_SHORT).show();
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