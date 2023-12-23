package com.example.attendance.Modals;

import android.os.Parcelable;

import java.io.Serializable;

public  class StudentModal implements Serializable {
    String studentName,studentRoll,studentAttendance,studentDayCame,totalCollegeDay,studentId,subId,lastDate;
    public StudentModal() {
    }

    public StudentModal(String studentName, String studentRoll, String studentAttendance, String studentDayCame, String totalCollegeDay, String studentId) {
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.studentAttendance = studentAttendance;
        this.studentDayCame = studentDayCame;
        this.totalCollegeDay = totalCollegeDay;
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(String studentAttendance) {
        this.studentAttendance = studentAttendance;
    }

    public String getStudentDayCame() {
        return studentDayCame;
    }

    public void setStudentDayCame(String studentDayCame) {
        this.studentDayCame = studentDayCame;
    }

    public String getTotalCollegeDay() {
        return totalCollegeDay;
    }

    public void setTotalCollegeDay(String totalCollegeDay) {
        this.totalCollegeDay = totalCollegeDay;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public String getSubId() {
        return subId;
    }

    public void setSubId(String subId) {
        this.subId = subId;
    }
}
