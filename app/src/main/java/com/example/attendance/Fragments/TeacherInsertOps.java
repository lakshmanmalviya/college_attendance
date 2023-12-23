package com.example.attendance.Fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.attendance.MainActivity;
import com.example.attendance.TeacherActivity.SignUp;
import com.example.attendance.TeacherActivity.TeacherImmediate;
import com.example.attendance.TeacherActivity.TeacherOps;
import com.example.attendance.TeacherActivity.TeacherShowCollege;
import com.example.attendance.databinding.FragmentTeacherInsertOpsBinding;
import com.google.firebase.auth.FirebaseAuth;

public class TeacherInsertOps extends Fragment {
    FragmentTeacherInsertOpsBinding bnd;
    FirebaseAuth myauth;
    public TeacherInsertOps() {  }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         bnd  =  FragmentTeacherInsertOpsBinding.inflate(inflater, container, false);
        TeacherOps activity = (TeacherOps) getActivity();
        String tempTId = activity.getTempIdSend();
        String studentlog = activity.getStudentlog();
        String tempTLocation = activity.tempTLocation();
        String stPossword = activity.stPossword();
        String stEnroll = activity.stEnroll();
        String authority = activity.authority();
        if (authority!=null && !authority.isEmpty() && "authority".equalsIgnoreCase(authority)){
            bnd.authorityLayout.setVisibility(View.VISIBLE);
            bnd.createTeacher.setVisibility(View.VISIBLE);
            bnd.tMarkAttendance.setVisibility(View.GONE);
            bnd.immediateThing.setVisibility(View.GONE);
            bnd.logout.setVisibility(View.GONE);
        }
        if (studentlog!=null && !studentlog.isEmpty() && "studentlog".equalsIgnoreCase(studentlog)){
            bnd.studentLayout.setVisibility(View.GONE);
        }
        myauth = FirebaseAuth.getInstance();
        bnd.createTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SignUp.class));
            }
        });
        bnd.tMarkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (studentlog!=null && !studentlog.isEmpty() && "studentlog".equalsIgnoreCase(studentlog)){
                    Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                    intent.putExtra("studentlog","studentlog");
                    intent.putExtra("tempTId",tempTId);
                    intent.putExtra("tempTLocation",tempTLocation);
                    intent.putExtra("stPossword",stPossword);
                    intent.putExtra("stEnroll",stEnroll);
//                    Toast.makeText(getContext(), ""+tempTId, Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                    intent.putExtra("attendance","attendance");
                    startActivity(intent);
                }
            }
        });
        bnd.TAddBatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                intent.putExtra("batchClick","batchClick");
                startActivity(intent);
            }
        });
        bnd.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myauth.signOut();
                startActivity(new Intent(getContext(), MainActivity.class));
            //    getActivity().finish();
            }
        });
        bnd.noteForTeacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bnd.inseopsLayout.setVisibility(View.VISIBLE);
            }
        });
        bnd.TAddBranch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                intent.putExtra("branchClick","branchClick");
                startActivity(intent);
            }
        });
        bnd.tAddSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                intent.putExtra("semClick","semClick");
                startActivity(intent);
            }
        });
        bnd.tAddSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                intent.putExtra("subClick","subClick");
                startActivity(intent);
            }
        });
        bnd.tAddStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),TeacherShowCollege.class);
                intent.putExtra("stuClick","stuClick");
                startActivity(intent);
            }
        });
        bnd.immediateThing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TeacherImmediate.class);
//              intent.putExtra("attendance","attendance");
                startActivity(intent);
            }
        });
        return  bnd.getRoot();
    }
}