package com.example.attendance.Modals;

public class BranchModal {
    String branchName,branchId,batchId;

    public BranchModal() {
    }

    public BranchModal(String branchName, String branchId) {
        this.branchName = branchName;
        this.branchId = branchId;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }
}
