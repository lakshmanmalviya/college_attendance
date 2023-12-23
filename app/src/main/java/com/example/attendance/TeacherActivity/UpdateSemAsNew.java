package com.example.attendance.TeacherActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;

import com.example.attendance.Adapters.SemAdapter;
import com.example.attendance.Modals.SemModal;
import com.example.attendance.R;
import com.example.attendance.databinding.ActivityUpdateSemAsNewBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UpdateSemAsNew extends AppCompatActivity {
   ActivityUpdateSemAsNewBinding bnd;
    SemAdapter semAdapter;
    ArrayList<SemModal> clist;
    FirebaseDatabase database;
    final String allCollege = "allCollege",allBatch = "allBatch",allBranch = "allBranch",allSem = "allSem";
    String collegeId = "collegeId",batchId = "batchId",branchId = "branchId",semId = "semId",semName="semName";
    GridLayoutManager gridLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd =  ActivityUpdateSemAsNewBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        collegeId = getIntent().getStringExtra(collegeId);
        batchId = getIntent().getStringExtra(batchId);
        branchId = getIntent().getStringExtra(branchId);
        semId = getIntent().getStringExtra(semId);
        semName = getIntent().getStringExtra(semName);
        database = FirebaseDatabase.getInstance();
        clist= new ArrayList<>();
        semAdapter = new SemAdapter(clist,getApplicationContext());
        bnd.semName.setText(semName);

    }
}