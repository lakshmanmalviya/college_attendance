package com.example.attendance.Modals;

public class SemModal {
    String semName,semId,branchId;

    public SemModal() {
    }

    public SemModal(String semName, String semId) {
        this.semName = semName;
        this.semId = semId;
    }
    public String getSemName() {
        return semName;
    }

    public void setSemName(String semName) {
        this.semName = semName;
    }

    public String getSemId() {
        return semId;
    }

    public void setSemId(String semId) {
        this.semId = semId;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }
}
