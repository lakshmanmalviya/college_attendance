package com.example.attendance.TeacherActivity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.Modals.TCredentialModal;
import com.example.attendance.Modals.TeacherModal;
import com.example.attendance.databinding.ActivityTeacherAuthenticationBinding;
import com.google.android.gms.common.api.internal.DataHolderNotifier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeacherAuthentication extends AppCompatActivity {
    final String allTeacher = "allTeacher";
    ActivityTeacherAuthenticationBinding bnd;
    FirebaseAuth myAuth;
    FirebaseDatabase database;
    String tCredentialId = "tCredentialId", tPossword = "tPossword", tLocation = "tLocation", temTId = "temTId", teacherName = "teacherName", tCredential = "tCredential";
    ProgressDialog dialog;
    DatabaseReference myref;
    ArrayList<TCredentialModal> tlist;
    String tempPwd = "", tempTLocation = "", stPossword = "", stEnroll = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherAuthenticationBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        myAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        dialog = new ProgressDialog(this);
        dialog.setTitle("Signing you ");
        dialog.setMessage("We are signing you");
        bnd.studentLogin.setOnClickListener(v -> {
            bnd.studentTLayout.setVisibility(View.VISIBLE);
            bnd.TLayout.setVisibility(View.GONE);
//            bnd.teacherLogin.setVisibility(View.GONE);
        });
        bnd.teacherLogin.setOnClickListener(v -> {
            bnd.studentTLayout.setVisibility(View.GONE);
            bnd.TLayout.setVisibility(View.VISIBLE);
//          bnd.studentLogin.setVisibility(View.GONE);
        });
        bnd.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.email.getText().toString().trim().isEmpty() || bnd.possword.getText().toString().trim().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Something is  empty", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
//                 signUpTeacher();
                    signInTeacher();
                }
            }
        });
        bnd.studentSubmit.setOnClickListener(v -> {
            getTeacherTempPwd();
            if (bnd.studentPwd.getText().toString().trim().isEmpty() || bnd.studentEnroll.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Something is  empty", Toast.LENGTH_SHORT).show();
            } else {
                stEnroll = bnd.studentEnroll.getText().toString().trim();
                studentLogin();
            }
        });
        if (myAuth.getCurrentUser() != null) {
            Intent intent = new Intent(getApplicationContext(), TeacherOps.class);
            startActivity(intent);
            finish();
        }
    }

    public void signInTeacher() {
        myAuth.signInWithEmailAndPassword(bnd.email.getText().toString().trim(), bnd.possword.getText().toString().trim())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        dialog.dismiss();
                        if (task.isSuccessful())
                        {
                            Map<String, Object> myMap = new HashMap<>();
                            String tid = task.getResult().getUser().getUid();
                            database.getReference().child(allTeacher).child(tid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                            if (dataSnapshot.getKey().equalsIgnoreCase("teacherName")) {
                                                teacherName = dataSnapshot.getValue().toString();
                                                updateTeacherName(teacherName);
                                                Intent intent = new Intent(getApplicationContext(), TeacherOps.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                }
                            });
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateTeacherName(String name) {
        myref = database.getReference().child(tCredential).child(myAuth.getCurrentUser().getUid());
        Map<String, Object> map = new HashMap<>();
        map.put(tCredentialId, myref.getKey());
        map.put("teacherName", name);
        myref.setValue(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Name is updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    public void getTeacherTempPwd() {
        tlist = new ArrayList<>();
        database.getReference().child(tCredential).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    tlist.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        tlist.add(dataSnapshot.getValue(TCredentialModal.class));
                    }
                    boolean isFound = false;
                    for (TCredentialModal modal : tlist) {
                        if (modal.gettPossword() != null && modal.gettPossword().equalsIgnoreCase(bnd.studentPwd.getText().toString().trim())) {
                            stPossword = tempPwd = modal.gettPossword();
                            temTId = modal.gettCredentialId();
                            tempTLocation = modal.gettLocation();
                            Log.d(TAG, "onDataChange: ===>>" + modal.gettPossword() + "        " + modal.getTeacherName());
//                         Toast.makeText(getApplicationContext(), "Your possword is matched", Toast.LENGTH_SHORT).show();
                            bnd.studentSubmit.setVisibility(View.VISIBLE);
                            bnd.studentSubmit.setText("Now Click Again");
                            dialog.dismiss();
                            isFound = true;
                            break;
                        }
                    }
                    if (isFound == false) {
                        Toast.makeText(getApplicationContext(), "Your possword didn't match", Toast.LENGTH_SHORT).show();
                        bnd.studentSubmit.setVisibility(View.VISIBLE);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void studentLogin() {
        bnd.studentSubmit.setVisibility(View.GONE);
        if (bnd.studentPwd.getText().toString().trim().equalsIgnoreCase(tempPwd)) {
//           dialog.dismiss();
            Intent intent = new Intent(getApplicationContext(), TeacherOps.class);
            intent.putExtra("studentlog", "studentlog");
            intent.putExtra("temTId", temTId);
            intent.putExtra("tempTLocation", tempTLocation);
            intent.putExtra("stPossword", stPossword);
            intent.putExtra("stEnroll", stEnroll);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Temprory login succeeded", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Wait we're searching", Toast.LENGTH_SHORT).show();
        }
    }
}