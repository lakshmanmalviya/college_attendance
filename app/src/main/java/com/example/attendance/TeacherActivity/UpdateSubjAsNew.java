package com.example.attendance.TeacherActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.attendance.databinding.ActivityUpdateSubjAsNewBinding;

public class UpdateSubjAsNew extends AppCompatActivity {
  ActivityUpdateSubjAsNewBinding bnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityUpdateSubjAsNewBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());

    }
}