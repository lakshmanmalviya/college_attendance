package com.example.attendance.TeacherActivity;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.attendance.MainActivity;
import com.example.attendance.Modals.TCredentialModal;
import com.example.attendance.databinding.ActivityTeacherImmediateBinding;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TeacherImmediate extends AppCompatActivity {
   ActivityTeacherImmediateBinding bnd;
    private StringBuffer stringBuffer;
    FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    FirebaseAuth myAuth;
    FirebaseDatabase database;
    final String allTeacher ="allTeacher";
    String teacherName ="teacherName",tCredential ="tCredential";
    String tCredentialId="tCredentialId",tPossword="tPossword",tLocation="tLocation",yourPWd="";
    DatabaseReference myref;
    ArrayList<TCredentialModal> tlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bnd = ActivityTeacherImmediateBinding.inflate(getLayoutInflater());
        setContentView(bnd.getRoot());
        database= FirebaseDatabase.getInstance();
        myAuth = FirebaseAuth.getInstance();
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);
        bnd.tvTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bnd.college.getText().toString().trim().isEmpty()||bnd.batch.getText().toString().trim().isEmpty()||bnd.semester.getText().toString().trim().isEmpty()
                ||bnd.branch.getText().toString().trim().isEmpty()||bnd.subject.getText().toString().trim().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please set all the things ", Toast.LENGTH_SHORT).show();
                    return;
                }
                yourPWd = bnd.college.getText().toString().trim()+"-"+bnd.batch.getText().toString().trim()+"-"+bnd.branch.getText().toString().trim()+"-"+bnd.semester.getText().toString().trim()+"-"+
                        bnd.subject.getText().toString().trim()+"-"+bnd.stPwd.getText().toString().trim();
                bnd.yourPwd.setVisibility(View.VISIBLE);
                bnd.yourPwd.setText("Possword :  "+yourPWd);
                bnd.progressBar2.setVisibility(View.VISIBLE);
//              bnd.tvTest.setVisibility(View.GONE);
                stringBuffer = new StringBuffer("");
                stringBuffer.append("Updated as \n");
                getCurrentLocation();
            }
        });
        bnd.reset.setOnClickListener(v->{
            bnd.stPwd.setText("     ");
            bnd.location.setText("    ");
            yourPWd="";
            bnd.yourPwd.setText(yourPWd);
            bnd.college.setText("");
            bnd.batch.setText("");
            bnd.branch.setText("");
            bnd.semester.setText("");
            bnd.subject.setText("");
            updateImmediateThing();
            bnd.stPwd.setText("");
            bnd.location.setText("Location will show here");
        });
    }
    public void updateImmediateThing(){
        if(bnd.stPwd.getText().toString().isEmpty()){
            Toast.makeText(getApplicationContext(), "Please set some possword", Toast.LENGTH_SHORT).show();
            return;
        }
        myref= database.getReference().child(tCredential).child(myAuth.getCurrentUser().getUid());
         Map<String,Object> map = new HashMap<>();
//       map.put(tCredentialId,myref.getKey());
         map.put(tPossword,yourPWd);
         map.put(tLocation,bnd.location.getText().toString());
         myref.updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Credential is updated", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
    private void getCurrentLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(TeacherImmediate.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (isGPSEnabled()) {
                    LocationServices.getFusedLocationProviderClient(TeacherImmediate.this)
                            .requestLocationUpdates(locationRequest, new LocationCallback() {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult) {
                                    super.onLocationResult(locationResult);
                                    LocationServices.getFusedLocationProviderClient(TeacherImmediate.this)
                                            .removeLocationUpdates(this);

                                    if (locationResult != null && locationResult.getLocations().size() >0){
                                        int index = locationResult.getLocations().size() - 1;
                                        double latitude = locationResult.getLocations().get(index).getLatitude();
                                        double longitude = locationResult.getLocations().get(index).getLongitude();
                                        stringBuffer.append("\nLatitude: "+ latitude + "\n" + "Longitude: "+ longitude);
                                        bnd.location.setText(latitude+"lakshman"+longitude);
                                        bnd.tvTest.setText(stringBuffer);
                                        bnd.tvTest.setVisibility(View.VISIBLE);
                                        bnd.progressBar2.setVisibility(View.GONE);
                                        updateImmediateThing();
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
                    Toast.makeText(TeacherImmediate.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                } catch (ApiException e) {

                    switch (e.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                            try {
                                ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                                resolvableApiException.startResolutionForResult(TeacherImmediate.this, 2);
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
}