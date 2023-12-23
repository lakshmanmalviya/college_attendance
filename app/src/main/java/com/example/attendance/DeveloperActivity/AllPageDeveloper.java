package com.example.attendance.DeveloperActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.R;
import com.example.attendance.databinding.ActivityAllPageDeveloperBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AllPageDeveloper extends AppCompatActivity {
   ActivityAllPageDeveloperBinding bnd;
    String allCollege = "allCollege",college = "college",collegeId = "collegeId";
    FirebaseDatabase myDatabase;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd  = ActivityAllPageDeveloperBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        myDatabase = FirebaseDatabase.getInstance();
        bnd.dInsertCollege.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.DcollegeName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "something is empty", Toast.LENGTH_SHORT).show();
                }
                else{
                    myref = myDatabase.getReference().child(allCollege).push();
                    Map<String,Object> map = new HashMap<>();
                    map.put(college, bnd.DcollegeName.getText().toString());
                    map.put(collegeId,myref.getKey());
                    myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getApplicationContext(), "The college is inserted 😍 🤩 ", Toast.LENGTH_SHORT).show();
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