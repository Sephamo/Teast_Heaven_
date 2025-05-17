package com.example.thegardenofeatn;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class staff_activity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private StaffAdapter staffAdapter;
    private List<StaffModel> staffList;

    private Button btnAddStaff;
    private TextView tvCalendarDate; // Assuming this is your calendar TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        recyclerView = findViewById(R.id.recyclerViewStaff);
        btnAddStaff = findViewById(R.id.btnAddStaff);
        // if you have a date TextView

        staffList = new ArrayList<>();
        staffAdapter = new StaffAdapter(this, staffList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(staffAdapter);

        btnAddStaff.setOnClickListener(v -> openAddStaffDialog());

        setupFooterNavigation();

    }

    private void setupFooterNavigation() {
        findViewById(R.id.bottomNavigation).findViewById(R.id.staffFooter).setOnClickListener(v -> {
            // Already on home page, maybe just refresh
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.homeFooter).setOnClickListener(v -> {
            Intent intent = new Intent(staff_activity.this, Manager.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.reservationsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(staff_activity.this, activity_manage_reservations.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.reportsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(staff_activity.this, Track_Sales.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.menuFooter).setOnClickListener(v -> {
            Intent intent = new Intent(staff_activity.this, manage_menu.class);
            startActivity(intent);
        });




    }

    private void openAddStaffDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_add_stuff); // Use your actual layout name
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        EditText etName = dialog.findViewById(R.id.etStaffName);

        Spinner spPosition = dialog.findViewById(R.id.spinnerPosition);
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(
                this, R.array.staff_positions, android.R.layout.simple_spinner_item);
        positionAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPosition.setAdapter(positionAdapter);

        Spinner spClockIn = dialog.findViewById(R.id.spinnerClockIn);
        ArrayAdapter<CharSequence> clockInAdapter = ArrayAdapter.createFromResource(
                this, R.array.clock_in_times, android.R.layout.simple_spinner_item);
        spClockIn.setAdapter(clockInAdapter);


        Spinner spClockOut = dialog.findViewById(R.id.spinnerClockOut);

        ArrayAdapter<CharSequence> clockOutAdapter = ArrayAdapter.createFromResource(
                this, R.array.clock_out_times, android.R.layout.simple_spinner_item);
        spClockOut.setAdapter(clockOutAdapter);

        Spinner spStatus = dialog.findViewById(R.id.spinnerStatus);
        ArrayAdapter<CharSequence> statusAdapter = ArrayAdapter.createFromResource(
                this, R.array.staff_statuses, android.R.layout.simple_spinner_item);
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spStatus.setAdapter(statusAdapter);

        Button btnSave = dialog.findViewById(R.id.btnSave);
        Button btnCancel = dialog.findViewById(R.id.btnCancel);
        ImageButton btnClose = dialog.findViewById(R.id.btnClose);

        // Sample spinner data — replace with your actual data
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.staff_positions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPosition.setAdapter(adapter);
        // Repeat for other spinners...

        btnSave.setOnClickListener(v -> {
            String name = etName.getText().toString().trim();
            String position = spPosition.getSelectedItem().toString();
            String clockIn = spClockIn.getSelectedItem().toString();
            String clockOut = spClockOut.getSelectedItem().toString();
            String status = spStatus.getSelectedItem().toString();

            // Dummy hours calculation — you can customize this
            SimpleDateFormat format = new SimpleDateFormat("hh:mm a", Locale.US);
            try {
                Date inTime = format.parse(clockIn);
                Date outTime = format.parse(clockOut);

                long diffMillis = outTime.getTime() - inTime.getTime();
                long diffHours = TimeUnit.MILLISECONDS.toHours(diffMillis);
                String hoursWorked = String.valueOf(diffHours);


            } catch (ParseException e) {
                e.printStackTrace();
            }
            // Or calculate based on clockIn and clockOut

            if (!name.isEmpty()) {
                String hoursWorked = "0";
                StaffModel newStaff = new StaffModel(name, position, clockIn, clockOut,hoursWorked, status);
                staffList.add(newStaff);
                staffAdapter.notifyItemInserted(staffList.size() - 1);
                dialog.dismiss();
            } else {
                etName.setError("Name required");
            }
        });

        btnCancel.setOnClickListener(v -> dialog.dismiss());
        btnClose.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}