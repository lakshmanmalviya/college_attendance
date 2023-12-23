package com.example.attendance.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.databinding.FragmentTeacherDeleteOpsBinding;

public class TeacherDeleteOps extends Fragment {
    FragmentTeacherDeleteOpsBinding bnd;
    public TeacherDeleteOps() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        bnd =  FragmentTeacherDeleteOpsBinding.inflate(inflater, container, false);

        return  bnd.getRoot();
    }
}