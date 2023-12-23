package com.example.attendance.TeacherActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.Toast;

import com.example.attendance.Adapters.TeacherFragAdapter;
import com.example.attendance.Fragments.TeacherInsertOps;
import com.example.attendance.R;
import com.example.attendance.databinding.ActivityTeacherOpsBinding;
import com.google.android.material.tabs.TabLayout;

public class TeacherOps extends AppCompatActivity {
    ActivityTeacherOpsBinding bnd;
    private String  tempIdSend = "", studentlog = "",tempTLocation = "",stPossword = "",stEnroll="",authority="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherOpsBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        getSupportActionBar().hide();
        bnd.viewPager.setAdapter(new TeacherFragAdapter(getSupportFragmentManager()));
        bnd.tabLayout.setupWithViewPager(bnd.viewPager);
        tempIdSend = getIntent().getStringExtra("temTId");
        studentlog = getIntent().getStringExtra("studentlog");
        tempTLocation = getIntent().getStringExtra("tempTLocation");
        stPossword = getIntent().getStringExtra("stPossword");
        stEnroll = getIntent().getStringExtra("stEnroll");
        authority = getIntent().getStringExtra("authority");
//        Toast.makeText(getApplicationContext(), "tempid = "+getIntent().getStringExtra("temTId"), Toast.LENGTH_SHORT).show();
//      bnd.tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }
    public String getTempIdSend() {
        return tempIdSend;
    }
    public String getStudentlog() {
        return studentlog;
    }
    public String tempTLocation() {
        return tempTLocation;
    }
    public String stPossword() {
        return stPossword;
    }
    public String stEnroll() {
        return stEnroll;
    }
    public String authority() {
        return authority;
    }
}