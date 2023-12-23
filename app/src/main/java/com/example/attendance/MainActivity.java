package com.example.attendance;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.attendance.DeveloperActivity.AllPageDeveloper;
import com.example.attendance.TeacherActivity.TeacherAuthentication;
import com.example.attendance.TeacherActivity.TeacherImmediate;
import com.example.attendance.TeacherActivity.TeacherOps;
import com.example.attendance.TeacherActivity.TeacherShowCollege;
import com.example.attendance.databinding.ActivityMainBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        bnd.developer.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), AllPageDeveloper.class));
//            Toast.makeText(getApplicationContext(), "Don't act too smart", Toast.LENGTH_SHORT).show();
        });
        bnd.authority.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TeacherOps.class);
            intent.putExtra("authority", "authority");
            startActivity(intent);
//            Toast.makeText(getApplicationContext(), "Don't act too smart", Toast.LENGTH_SHORT).show();
        });
        bnd.teacher.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), TeacherAuthentication.class)));
        bnd.student.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), TeacherShowCollege.class);
            intent.putExtra("student", "student");
            startActivity(intent);
        });
    }
}