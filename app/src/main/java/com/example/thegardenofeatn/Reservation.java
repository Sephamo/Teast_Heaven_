package com.example.thegardenofeatn;

public class Reservation {
    public String reservationId;
    public String date;
    public String timeSlot;
    public int partySize;

    // Default constructor required for Firebase
    public Reservation() {
    }

    public Reservation(String reservationId, String date, String timeSlot, int partySize) {
        this.reservationId = reservationId;
        this.date = date;
        this.timeSlot = timeSlot;
        this.partySize = partySize;
    }
}

