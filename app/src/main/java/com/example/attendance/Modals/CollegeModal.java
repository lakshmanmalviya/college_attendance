package com.example.attendance.Modals;

public class CollegeModal {
    String college,collegeId;

    public CollegeModal() {
    }

    public CollegeModal(String college, String collegeId) {
        this.college = college;
        this.collegeId = collegeId;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }
}
