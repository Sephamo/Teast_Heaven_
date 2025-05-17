package com.example.thegardenofeatn;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class activity_manage_reservations extends AppCompatActivity implements ReservationAdapter.OnReservationChangedListener {

    private List<ReservationModel> reservationsList = new ArrayList<>();
    private ReservationAdapter reservationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservartions);

        // Initialize RecyclerView
        RecyclerView rvReservations = findViewById(R.id.rv_reservations);
        rvReservations.setLayoutManager(new LinearLayoutManager(this));

        // Initialize adapter
        reservationAdapter = new ReservationAdapter(reservationsList, this, this);
        rvReservations.setAdapter(reservationAdapter);

        // Set click listener for "New Reservation" button
        findViewById(R.id.btn_new_reservation).setOnClickListener(v -> {
            showAddReservationDialog();
        });

        // Load initial data if needed
        // loadInitialReservations();
        setupFooterNavigation();
    }

    private void setupFooterNavigation() {
        findViewById(R.id.bottomNavigation).findViewById(R.id.reservationsFooter).setOnClickListener(v -> {
            // Already on home page, maybe just refresh
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.reportsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(activity_manage_reservations.this, Track_Sales.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.staffFooter).setOnClickListener(v -> {
            Intent intent = new Intent(activity_manage_reservations.this, staff_activity.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.homeFooter).setOnClickListener(v -> {
            Intent intent = new Intent(activity_manage_reservations.this, Manager.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.menuFooter).setOnClickListener(v -> {
            Intent intent = new Intent(activity_manage_reservations.this, manage_menu.class);
            startActivity(intent);
        });

    }

    private void showAddReservationDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_reservations);

        // Initialize views
        EditText etName = dialog.findViewById(R.id.etName);
        Spinner spinnerTime = dialog.findViewById(R.id.spinnerTime);
        Spinner spinnerGuestNo = dialog.findViewById(R.id.spinnerGuestNo);
        Spinner spinnerTableNo = dialog.findViewById(R.id.spinnerTableNo);
        Spinner spinnerStatus = dialog.findViewById(R.id.spinnerStatus);

        // Set adapters
        ArrayAdapter<CharSequence> timeAdapter = ArrayAdapter.createFromResource(
                this, R.array.reservation_times, android.R.layout.simple_spinner_item);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTime.setAdapter(timeAdapter);

        ArrayAdapter<CharSequence> guestAdapter = ArrayAdapter.createFromResource(
                this, R.array.guest_numbers, android.R.layout.simple_spinner_item);
        guestAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGuestNo.setAdapter(guestAdapter);

        ArrayAdapter<CharSequence> tableAdapter = ArrayAdapter.createFromResource(
                this, R.array.table_numbers, android.R.layout.simple_spinner_item);
        tableAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTableNo.setAdapter(tableAdapter);

        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                this, R.array.reservation_statuses, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatus.setAdapter(statusAdapter);

        // Save Button Click
        Button btnSave = dialog.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString();
            String time = spinnerTime.getSelectedItem().toString();
            String guests = spinnerGuestNo.getSelectedItem().toString();
            String table = spinnerTableNo.getSelectedItem().toString();
            String status = spinnerStatus.getSelectedItem().toString();

            // Add new reservation through adapter
            ReservationModel newReservation = new ReservationModel(name, time, guests, table, status);
            reservationAdapter.addReservation(newReservation);
            dialog.dismiss();
        });

        // Close Button Click
        dialog.findViewById(R.id.btnClose).setOnClickListener(v -> dialog.dismiss());
        dialog.findViewById(R.id.btnCancel).setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }

    @Override
    public void onReservationChanged(int position, ReservationModel reservation) {
        // Update the reservation in the list
        if (position >= 0 && position < reservationsList.size()) {
            reservationsList.set(position, reservation);
            reservationAdapter.updateReservation(position, reservation);
        }
    }
}