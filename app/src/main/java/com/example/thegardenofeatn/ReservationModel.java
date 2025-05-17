package com.example.thegardenofeatn;

public class ReservationModel {
    private String name;
    private String time;
    private String guestNo;
    private String tableNo;
    private String status;

    public ReservationModel(String name, String time, String guestNo, String tableNo, String status) {
        this.name = name;
        this.time = time;
        this.guestNo = guestNo;
        this.tableNo = tableNo;
        this.status = status;
    }

    // Getters
    public String getCustomerName() {
        return name; }
    public String getReservationTime() {
        return time; }
    public String getGuestNo() {
        return guestNo; }
    public String getTableNo() {
        return tableNo; }
    public String getStatus() {
        return status; }

    public void setCustomerName(String name) {
        this.name = name;

    }
    public void setReservationTime(String time) {
        this.time = time;
    }
    public void setGuestNo(String guestNo) {
        this.guestNo = guestNo;
    }
    public void setTableNo(String tableNo) {
        this.tableNo = tableNo;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    // Setters (optional if you want to update)
}
