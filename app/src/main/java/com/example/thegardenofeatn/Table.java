package com.example.thegardenofeatn;
public class Table {
    private String number;
    private String status;
    private int guests;

    public Table() {

    }

    public Table(String number, String status, int guests) {
        this.number = number;
        this.status = status;
        this.guests = guests;
    }

    // Add getters and setters for all fields
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getGuests() {
        return guests;
    }
    public void setGuests(int guests) {
        this.guests = guests;
    }
}
