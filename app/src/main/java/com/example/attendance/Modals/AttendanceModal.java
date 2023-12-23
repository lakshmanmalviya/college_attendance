package com.example.attendance.Modals;

public class AttendanceModal {
    String takenBy,dayTime,attendanceDayId;

    public AttendanceModal() {

    }

    public AttendanceModal(String takenBy, String dayTime, String attendanceDayId) {
        this.takenBy = takenBy;
        this.dayTime = dayTime;
        this.attendanceDayId = attendanceDayId;
    }

    public String getTakenBy() {
        return takenBy;
    }

    public void setTakenBy(String takenBy) {
        this.takenBy = takenBy;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getAttendanceDayId() {
        return attendanceDayId;
    }

    public void setAttendanceDayId(String attendanceDayId) {
        this.attendanceDayId = attendanceDayId;
    }
}
