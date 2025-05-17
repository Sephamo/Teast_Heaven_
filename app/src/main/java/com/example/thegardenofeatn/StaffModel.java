package com.example.thegardenofeatn;

public class StaffModel {
    private String name;
    private String position;
    private String clockIn;
    private String clockOut;
    private String hoursWorked;
    private String status;

    public StaffModel(String name, String position, String clockIn, String clockOut, String hoursWorked, String status) {
        this.name = name;
        this.position = position;
        this.clockIn = clockIn;
        this.clockOut = clockOut;
        this.hoursWorked = hoursWorked;
        this.status = status;
    }


    // Getters
    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getClockIn() {
        return clockIn;
    }

    public String getClockOut() {
        return clockOut;
    }

    public String getHoursWorked() {
        return hoursWorked;
    }

    public String getStatus() {
        return status;
    }

    // Setters (if needed)
    public void setStatus(String status) {
        this.status = status;
    }
}