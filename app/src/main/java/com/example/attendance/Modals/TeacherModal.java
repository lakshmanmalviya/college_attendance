package com.example.attendance.Modals;
public class TeacherModal {
    String teacherName,teacherEmpId,deptName,teacherSubName,teacherId,email,possword;
   public TeacherModal(){
   }

    public TeacherModal(String email, String possword) {
        this.email = email;
        this.possword = possword;
    }

    public TeacherModal(String teacherName, String teacherEmpId, String deptName, String teacherSubName, String email, String possword) {
        this.teacherName = teacherName;
        this.teacherEmpId = teacherEmpId;
        this.deptName = deptName;
        this.teacherSubName = teacherSubName;
        this.email = email;
        this.possword = possword;
    }

    public TeacherModal(String teacherName, String teacherEmpId, String deptName, String teacherSubName, String teacherId, String email, String possword) {
        this.teacherName = teacherName;
        this.teacherEmpId = teacherEmpId;
        this.deptName = deptName;
        this.teacherSubName = teacherSubName;
        this.teacherId = teacherId;
        this.email = email;
        this.possword = possword;
    }
}
