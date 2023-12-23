package com.example.attendance.Modals;

public class BatchModal {
    String batchName;
    String batchId;
    String collegeId;



    public BatchModal() {
    }

    public BatchModal(String batchName, String batchId) {
        this.batchName = batchName;
        this.batchId = batchId;
    }

    public String getBatchName() {
        return batchName;
    }

    public void setBatchName(String batchName) {
        this.batchName = batchName;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
    public String getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(String collegeId) {
        this.collegeId = collegeId;
    }

}
