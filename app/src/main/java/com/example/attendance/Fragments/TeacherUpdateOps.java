package com.example.attendance.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.attendance.R;
import com.example.attendance.TeacherActivity.TeacherShowCollege;
import com.example.attendance.databinding.FragmentTeacherUpdateOpsBinding;

public class TeacherUpdateOps extends Fragment {
    FragmentTeacherUpdateOpsBinding bnd;
  public TeacherUpdateOps() { }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      bnd  = FragmentTeacherUpdateOpsBinding.inflate(inflater, container, false);
     bnd.newSem.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         Intent intent = new Intent(getContext(), TeacherShowCollege.class);
         intent.putExtra("newSem","newSem");
//       Toast.makeText(getContext(), ""+tempTId, Toast.LENGTH_SHORT).show();
         startActivity(intent);
       }
     });
     bnd.newSubject.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
         Intent intent = new Intent(getContext(), TeacherShowCollege.class);
         intent.putExtra("updateSub","updateSub");
//       Toast.makeText(getContext(), ""+tempTId, Toast.LENGTH_SHORT).show();
         startActivity(intent);
       }
     });


      return  bnd.getRoot();
    }
}