package com.example.attendance.TeacherActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Modals.TCredentialModal;
import com.example.attendance.R;
import com.example.attendance.databinding.ActivitySignUpBinding;
import com.example.attendance.databinding.ActivityTeacherAuthenticationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
  ActivitySignUpBinding bnd;
    final String allTeacher = "allTeacher";
    FirebaseAuth myAuth;
    FirebaseDatabase database;
    ProgressDialog dialog;
    DatabaseReference myref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        myAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Creating Account");
        dialog.setMessage("We are creating your account");
        bnd.signUpSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.singUpEmail.getText().toString().trim().isEmpty() || bnd.signUPwd.getText().toString().trim().isEmpty()|| bnd.signUpTeacherName.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "please provide asked details", Toast.LENGTH_SHORT).show();
                }
                else{
                    dialog.show();
                    signUpTeacher();
                }
            }
        });
    }
    public void signUpTeacher() {
        myAuth.createUserWithEmailAndPassword(bnd.singUpEmail.getText().toString().trim(), bnd.signUPwd.getText().toString().trim())
                .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String id = task.getResult().getUser().getUid();
                            Log.d("ID ======>>>>", id);
                            storeTeacherDetail(id);
                            dialog.dismiss();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "SignUp is not Succeeded", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Account creation is failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void storeTeacherDetail(String id) {
        myref = database.getReference().child(allTeacher).child(id);
        Map<String, Object> map = new HashMap<>();
        map.put("teacherName", bnd.signUpTeacherName.getText().toString().trim());
        map.put("teacherEmpId", bnd.singUpTeacherEmpId.getText().toString().trim());
        map.put("deptName", bnd.singUpTeacherDeptName.getText().toString().trim());
        map.put("teacherSubName", bnd.signupTeacherSubName.getText().toString().trim());
        map.put("teacherId", myref.getKey());
        map.put("email", bnd.singUpEmail.getText().toString().trim());
        map.put("possword", bnd.signUPwd.getText().toString().trim());
        myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Storing teacher details", Toast.LENGTH_SHORT).show();
//              startActivity(new Intent(getApplicationContext(),TeacherOps.class));
                bnd.singUpEmail.setText("");
                bnd.signUPwd.setText("");
                bnd.signUpTeacherName.setText("");

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error while storing  " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}