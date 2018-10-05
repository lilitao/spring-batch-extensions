package org.springframework.batch.item;

public class UploadBean {

    private int rowNumber;

    private String client;

    private String polNo;
    private String certNo;
    private String status;
    private String plan;
    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getPolNo() {
        return polNo;
    }

    public void setPolNo(String polNo) {
        this.polNo = polNo;
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public int getRowNumber() {
        return rowNumber;
    }

    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }
}
