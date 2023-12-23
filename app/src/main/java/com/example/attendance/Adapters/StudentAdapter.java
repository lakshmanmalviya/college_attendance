package com.example.attendance.Adapters;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.attendance.Modals.StudentModal;
import com.example.attendance.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentHolder> {
    final String tCredential = "tCredential", allPresent = "allPresent";
    final String allSub = "allSub", allStudent = "allStudent", attendance = "attendance", myAddAttendance = "myAddAttendance", myMinusAttendance = "myMinusAttendance";
    ArrayList<StudentModal> slist;
    StudentModal studentModal;
    Context context;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseAuth myAuth = FirebaseAuth.getInstance();
    DatabaseReference myref;
    MediaPlayer mediaPlayer;
    int lastPosition = -1, teacherCount = 0, totalPresent = 0;
    Map<String, Object> map = new HashMap<>();
    Map<String, Object> map2 = new HashMap<>();
    Map<String, Object> map3;
    String takenBy = "takenBy", dayTime = "dayTime", attendanceDayId = "attendanceDayId", tempTName = "tempTName", lastDate = "";

    public StudentAdapter(ArrayList<StudentModal> slist, Context context, Map<String, Object> map3) {
        this.slist = slist;
        this.context = context;
        this.map3 = map3;
        mediaPlayer = MediaPlayer.create(context, R.raw.click);
        getTeacherName();
        getTotalPresent();
    }

    @NonNull
    @Override
    public StudentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_row, parent, false);
        return new StudentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentHolder holder, int position) {
        studentModal = slist.get(position);
        if (position == 0) {
            holder.studentName.setText("Student Name \n\n" + studentModal.getStudentName());
            holder.studentRoll.setText("EnrollNum \n\n" + studentModal.getStudentRoll());
            holder.studentAttendance.setText("Attendance  \n\n" + studentModal.getStudentAttendance() + " %");
            holder.studentDayCame.setText("DayCame \n\n" + studentModal.getStudentDayCame() + " Day Came");
            holder.totalCollegeDay.setText("TotalCollegeDay \n\n" + studentModal.getTotalCollegeDay() + " TotalDay");
        } else {
            holder.studentName.setText(studentModal.getStudentName());
            holder.studentRoll.setText(studentModal.getStudentRoll());
            holder.studentAttendance.setText(studentModal.getStudentAttendance() + " %");
            holder.studentDayCame.setText(studentModal.getStudentDayCame() + " Day Came");
            holder.totalCollegeDay.setText(studentModal.getTotalCollegeDay() + " TotalDay");
        }
        if (position > lastPosition) {
            lastPosition = position;
            holder.itemView.setAnimation(getAnimation());
        }

        holder.plus.setOnClickListener(v -> {
            studentModal = slist.get(holder.getAbsoluteAdapterPosition());
            mediaPlayer.start();
            addAttendance(studentModal.getStudentId());
            showMessage(v, studentModal.getStudentName() + "'s Attendance is marked", R.drawable.plus);
            myAddAttendance(studentModal.getStudentId());
            addInAllPresent(studentModal.getStudentId());
            updateTotal(lastDate,studentModal.getStudentId());
        });
        holder.minus.setOnClickListener(v -> {
            studentModal = slist.get(holder.getAbsoluteAdapterPosition());
            mediaPlayer.start();
            minusAttendance(studentModal.getStudentId());
            showMessage(v, studentModal.getStudentName() + "'s Attendance is reduced by one", R.drawable.minus);
            myMinusAttendance(studentModal.getStudentId());
        });
    }

    @Override
    public int getItemCount() {
        return slist.size();
    }

    public void addAttendance(String studentId) {
        database.getReference().child(allStudent).child(studentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            map.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equalsIgnoreCase("studentDayCame")) {
                                    map.put(dataSnapshot.getKey(), (Integer.parseInt(dataSnapshot.getValue().toString()) + 1) + "");
                                } else {
                                    map.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
                                }
                                Log.d(TAG, "studnet :===>> " + dataSnapshot.getKey() + dataSnapshot.getValue().toString());
                            }
                            int calculate = (Integer.parseInt(map.get("studentDayCame").toString()) * 100 / Integer.parseInt(map.get("totalCollegeDay").toString()));
                            map.put("studentAttendance", calculate + "");
                            addAttendanceUpdate(studentId, map);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void minusAttendance(String studentId) {
        database.getReference().child(allStudent).child(studentId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            map.clear();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equalsIgnoreCase("studentDayCame")) {
                                    map.put(dataSnapshot.getKey(), (Integer.parseInt(dataSnapshot.getValue().toString()) - 1) + "");
                                } else {
                                    map.put(dataSnapshot.getKey(), dataSnapshot.getValue().toString());
                                }
                            }

                            int calculate = (Integer.parseInt(map.get("studentDayCame").toString())) * 100 / Integer.parseInt(map.get("totalCollegeDay").toString());
                            map.put("studentAttendance", calculate + "");
                            addAttendanceUpdate(studentId, map);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });
    }

    public void showMessage(View v, String msg, int imageView) {
        Toast toast = new Toast(context);
        View view = LayoutInflater.from(context).inflate(R.layout.toast_msg, v.findViewById(R.id.toastLay));
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        TextView toastMg = view.findViewById(R.id.toastMsg);
        toastMg.setText(msg);
        ImageView toastImg = view.findViewById(R.id.toastImg);
        toastImg.setImageResource(imageView);
        toast.setGravity(Gravity.TOP, 0, 0);
        toast.show();
    }

    public void addAttendanceUpdate(String studentId, Map<String, Object> map) {
        database.getReference().child(allStudent).child(studentId)
                .updateChildren(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context.getApplicationContext(), "Error while updating  ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void myAddAttendance(String sId) {
        map2.clear();
        myref = database.getReference().child(attendance).child(sId).child(myAddAttendance).push();
        map2.put(dayTime, new Date() + "");
        map2.put(takenBy, getTeacherName());
        map2.put(attendanceDayId, myref.getKey());
        myref.setValue(map2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error while inserting the date and all  ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void myMinusAttendance(String sId) {
        map2.clear();
        myref = database.getReference().child(attendance).child(sId).child(myMinusAttendance).push();
        map2.put(dayTime, new Date() + "");
        map2.put(takenBy, getTeacherName());
        map2.put(attendanceDayId, myref.getKey());
        myref.setValue(map2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error while inserting the date and all  ", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getTeacherName() {
        if (teacherCount == 0) {
            if (myAuth.getCurrentUser() != null) {
                database.getReference().child(tCredential).child(myAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equalsIgnoreCase("teacherName")) {
                                    tempTName = dataSnapshot.getValue(String.class);
                                    teacherCount = 1;
                                    break;
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(context, "Error " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            } else {

                database.getReference().child(tCredential).child(map3.get("tempTId").toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                if (dataSnapshot.getKey().equalsIgnoreCase("teacherName")) {
//                             Toast.makeText(context.getApplicationContext(), "tName "+dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                                    tempTName = dataSnapshot.getValue().toString();
                                    teacherCount = 1;
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
        return tempTName;
    }

    public Animation getAnimation() {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        animation.setDuration(500);
        return animation;
    }

    public void updateTotal(String lastDate,String studentId) {
        String lDate[] = lastDate.split(" ");
        String str = new Date() + "";
        String tDate[] = str.split(" ");
        if (!(lDate[1] + lDate[2]).equalsIgnoreCase(tDate[1] + tDate[2])) {
            updateLastAsToday();
            removeAllPresent(studentId);
        } else {
            incrementTotalByOne(totalPresent);
        }
    }

    public void getTotalPresent() {
        database.getReference().child(allSub).child(map3.get("subId").toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Log.d("TAG", "onDataChange: ======>>" + dataSnapshot.getValue());
                        if (dataSnapshot.getKey().equalsIgnoreCase("totalPresent")) {
                            totalPresent = Integer.parseInt(dataSnapshot.getValue(String.class));
                        }
                        if (dataSnapshot.getKey().equalsIgnoreCase("lastDate")) {
                            lastDate = dataSnapshot.getValue(String.class);
                        }
                    }
                }
//                Toast.makeText(context.getApplicationContext(), "value is "+totalPresent+""+lastDate, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(context, "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updateLastAsToday() {
        database.getReference().child(allSub).child(map3.get("subId").toString()).child("lastDate").setValue(new Date() + "").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
            }
        });
        database.getReference().child(allSub).child(map3.get("subId").toString()).child("totalPresent").setValue(1 + "").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "updateLastDateAsToday", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void incrementTotalByOne(int totalPr) {
        totalPr = totalPr + 1;
        database.getReference().child(allSub).child(map3.get("subId").toString()).child("totalPresent").setValue(totalPr + "").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Incremented", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeAllPresent(String studentId) {
        database.getReference().child(allSub).child(map3.get("subId").toString()).child("allPresent").removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
              addInAllPresent(studentId);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Problem in network" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void addInAllPresent(String studentId) {
         myref = database.getReference().child(allSub).child(map3.get("subId").toString()).child(allPresent).push();
           myref.setValue(studentId).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error .." + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    class StudentHolder extends RecyclerView.ViewHolder {
        TextView studentName, studentRoll, studentAttendance, studentDayCame, totalCollegeDay;
        ImageView plus, minus;

        public StudentHolder(@NonNull View itemView) {
            super(itemView);
            studentName = itemView.findViewById(R.id.stuNameRow);
            studentRoll = itemView.findViewById(R.id.enrollRow);
            studentAttendance = itemView.findViewById(R.id.attendanceRow);
            studentDayCame = itemView.findViewById(R.id.cameDayRow);
            totalCollegeDay = itemView.findViewById(R.id.totalDayRow);
            plus = itemView.findViewById(R.id.plus);
            minus = itemView.findViewById(R.id.minus);
        }
    }
}
