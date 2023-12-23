package com.example.attendance.TeacherActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.attendance.Adapters.SubAdapter;
import com.example.attendance.Classes.RecyclerItemClickListener;
import com.example.attendance.Modals.SubModal;
import com.example.attendance.Modals.TCredentialModal;
import com.example.attendance.StudentActivity.StudentSeeAttendance;
import com.example.attendance.databinding.ActivityTeacherShowSubjectBinding;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
public class TeacherShowSubject extends AppCompatActivity {
    ActivityTeacherShowSubjectBinding bnd;
    private StringBuffer stringBuffer;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    FirebaseAuth myAuth;FirebaseDatabase database;DatabaseReference myref;
    ArrayList<TCredentialModal> tlist;
    SubAdapter subAdapter;
    ArrayList<SubModal> clist;
    GridLayoutManager gridLayoutManager; boolean checkLocation = false,giveLocation = false;
    final String allTeacher ="allTeacher",allSub = "allSub";
    String semId = "semId",subId = "subId",tLocation="tLocation",studentLocation = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherShowSubjectBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        semId = getIntent().getStringExtra(semId);
        tLocation =getIntent().getStringExtra("tempTLocation");
        Log.d("TAG", "onCreate: ======>>>"+getIntent().getStringExtra("tempTLocation")+"    ok   "+tLocation);
        database = FirebaseDatabase.getInstance();
        myAuth = FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        clist= new ArrayList<>();
        subAdapter = new SubAdapter(clist,getApplicationContext());
        bnd.showSubRec.setAdapter(subAdapter);
        gridLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        bnd.showSubRec.setLayoutManager(gridLayoutManager);
        if(getIntent().getStringExtra("studentlog")!=null){
            bnd.locationOfStudent.setVisibility(View.VISIBLE);
            bnd.giveStLocation.setVisibility(View.VISIBLE);
        }
        bnd.giveStLocation.setOnClickListener(v->{
            checkLocationSame();
            getCurrentLocation();
            bnd.progressBar3.setVisibility(View.VISIBLE);
            stringBuffer = new StringBuffer();
        });
        database.getReference().child(allSub).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    clist.clear();
                    for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                        SubModal subModal = dataSnapshot.getValue(SubModal.class);
                        if (subModal.getSemId().equalsIgnoreCase(semId)){

                            if("studentlog".equals(getIntent().getStringExtra("studentlog"))){
                                String[] strA= subModal.getSubName().split("-");
                                if(getIntent().getStringExtra("stPossword").toLowerCase().contains(strA[1].trim().toLowerCase())){
                                    clist.add(subModal);
                                }
                            }
                            else{
                                clist.add(subModal);
                            }
                        }
                    }
                    subAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        bnd.showSubRec.addOnItemTouchListener(new RecyclerItemClickListener(getApplicationContext(), bnd.showSubRec, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                SubModal subModal = clist.get(position);
                if("stuClick".equals(getIntent().getStringExtra("stuClick"))){
                    Intent intent = new Intent(getApplicationContext(),AddStudents.class);
                    intent.putExtra("semId",semId);
                    intent.putExtra(subId,subModal.getSubId());
                    startActivity(intent);
                }
                if("attendance".equals(getIntent().getStringExtra("attendance"))){
                    Intent intent = new Intent(getApplicationContext(),TeacherShowStudents.class);
                    intent.putExtra("semId",semId);
                    intent.putExtra(subId,subModal.getSubId());
                    startActivity(intent);
                }
                if(getIntent().getStringExtra("studentlog")!=null && "studentlog".equals(getIntent().getStringExtra("studentlog"))){
                    if (giveLocation==true){
                    Intent intent = new Intent(getApplicationContext(),TeacherShowStudents.class);
                    intent.putExtra("semId",semId);
                    intent.putExtra("studentlog","studentlog");
                    intent.putExtra("tempTId",getIntent().getStringExtra("tempTId"));
                    intent.putExtra("tempTLocation",getIntent().getStringExtra("tempTLocation"));
                    intent.putExtra("stPossword",getIntent().getStringExtra("stPossword"));
                    intent.putExtra("stEnroll",getIntent().getStringExtra("stEnroll"));
                    intent.putExtra(subId,subModal.getSubId());
                    startActivity(intent);

                    }
                    else {
                        Toast.makeText(getApplicationContext(), "First match your location", Toast.LENGTH_SHORT).show();
                    }
                }
                if("student".equals(getIntent().getStringExtra("student"))){
                    Intent intent = new Intent(getApplicationContext(), StudentSeeAttendance.class);
                    intent.putExtra("semId",semId);
                    intent.putExtra(subId,subModal.getSubId());
                    startActivity(intent);
                }
                if("updateSub".equals(getIntent().getStringExtra("updateSub"))){
                    Intent intent = new Intent(getApplicationContext(), UpdateSubjAsNew.class);
                    intent.putExtra("semId",semId);
                    intent.putExtra(subId,subModal.getSubId());
                    startActivity(intent);
                }
            }
            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
    }
    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(TeacherShowSubject.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(TeacherShowSubject.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    LocationServices.getFusedLocationProviderClient(TeacherShowSubject.this)
                                            .removeLocationUpdates(this);
                                    if (locationResult != null && locationResult.getLocations().size() >0){
                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        stringBuffer.append("Latitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                        bnd.locationOfStudent.setText(stringBuffer);
                                        studentLocation = latitude+"lakshman"+longitude;
                                        bnd.progressBar3.setVisibility(View.GONE);
                                        checkLocation= true;
                                    }
                                }
                            }, Looper.getMainLooper());

                } else {
                    turnOnGPS();
                }

            } else {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }
    private void turnOnGPS() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                .checkLocationSettings(builder.build());

        result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
            @Override
            public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                try {
                    LocationSettingsResponse response = task.getResult(ApiException.class);
                    Toast.makeText(TeacherShowSubject.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(TeacherShowSubject.this, 2);
                            } catch (IntentSender.SendIntentException ex) {
                                ex.printStackTrace();
                            }
                            break;

                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            //Device does not have location
                            break;
                    }
                }
            }
        });

    }
    private boolean isGPSEnabled() {
        LocationManager locationManager = null;
        boolean isEnabled = false;

        if (locationManager == null) {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        }

        isEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        return isEnabled;
    }
    public void checkLocationSame(){
        if (checkLocation==true){
            if(tLocation.isEmpty()||studentLocation.isEmpty()){
                return;
            }
            String [] str = tLocation.split("lakshman|[.]");
            String []str2 = studentLocation.split("lakshman|[.]");
            Log.d("TAG", "checkLocationSame: ====>>" + str[1].substring(0, 3) + str2[1].substring(0, 3));
            if (str[1].substring(0,3).equalsIgnoreCase(str2[1].substring(0,3)) && str[3].substring(0,3).equalsIgnoreCase(str2[3].substring(0,3)) ){
                Toast.makeText(getApplicationContext(), "Location is matched !!!"+str[1].substring(0,3), Toast.LENGTH_SHORT).show();
                giveLocation = true;
            }
            else {
                Toast.makeText(getApplicationContext(), "Your location didn't match", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}