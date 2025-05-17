package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ReservationActivity extends AppCompatActivity {

    // variables

    private String selectedDate = "";
    private String selectedTimeSlot = "";
    private int selectedPartySize = 0;

    CalendarView calendarView;
    GridLayout layoutTimeButtons;

    private TimeSlotButtonManager timeSlotButtonManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservation);

        DatabaseReference reservationRef = FirebaseDatabase.getInstance().getReference("reservations");


        // Calendar selection
        calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                Toast.makeText(ReservationActivity.this, "Selected Date: " + selectedDate, Toast.LENGTH_SHORT).show();
            }
        });


        // Confirm Reservation button click
        Button confirmButton = findViewById(R.id.btnConfirmReservation);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedDate.isEmpty() || selectedTimeSlot.isEmpty() || selectedPartySize == 0) {
                    Toast.makeText(ReservationActivity.this, "Please select all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Generate unique ID
                String reservationId = reservationRef.push().getKey();

                if (reservationId != null) {
                    Reservation reservation = new Reservation(reservationId, selectedDate, selectedTimeSlot, selectedPartySize);
                    reservationRef.child(reservationId).setValue(reservation)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(ReservationActivity.this, "Reservation Successful", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ReservationActivity.this, "Reservation Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ReservationActivity.this, "Failed to generate reservation ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_reservation);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_menu) {
                    startActivity(new Intent(ReservationActivity.this, ManuPage.class));
                    return true;
                } else if (itemId == R.id.nav_home) {
                    startActivity(new Intent(ReservationActivity.this, CustomerActivity.class));
                    return true;
                } else if (itemId == R.id.nav_review) {
                    startActivity(new Intent(ReservationActivity.this, Feedback.class));
                    return true;
                }

                return false;
            }
        });

        // number of people button
        PartyButtonManager partyManager = new PartyButtonManager(
                this,
                findViewById(R.id.btnParty1),
                findViewById(R.id.btnParty2),
                findViewById(R.id.btnParty3),
                findViewById(R.id.btnParty4),
                findViewById(R.id.btnParty5plus)
        );

        partyManager.setButtonClickHandlers(new PartyButtonManager.OnPartySelectedListener() {
            @Override
            public void onPartySelected(int partySize) {
                selectedPartySize = partySize;
                Toast.makeText(ReservationActivity.this, "Party Size: " + partySize, Toast.LENGTH_SHORT).show();
            }
        });

        // time slot buttons
        Button time5 = findViewById(R.id.btnTime5);
        Button time6 = findViewById(R.id.btnTime6);
        Button time7 = findViewById(R.id.btnTime7);
        Button time8 = findViewById(R.id.btnTime8);


        timeSlotButtonManager = new TimeSlotButtonManager(this, time5, time6, time7, time8);
        timeSlotButtonManager.loadAvailability(new TimeSlotButtonManager.OnTimeSlotSelectedListener() {
            @Override
            public void onTimeSlotSelected(String timeSlot) {
                selectedTimeSlot = timeSlot;
                Toast.makeText(ReservationActivity.this, "Time Slot: " + timeSlot, Toast.LENGTH_SHORT).show();
            }
        });



    }

}