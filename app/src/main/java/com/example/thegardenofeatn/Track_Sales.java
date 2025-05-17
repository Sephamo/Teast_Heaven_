package com.example.thegardenofeatn;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.card.MaterialCardView;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class Track_Sales extends AppCompatActivity {

    private ImageButton btnBack;
    private MaterialCardView tabSales, tabCalendar;
    private TextView tvRevenue, tvProductsSold;
    private TextView bar1Value, bar2Value, bar3Value, bar4Value;
    private View bar1, bar2, bar3, bar4;
    private EditText etCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_sales); // make sure the XML filename matches

        etCalendar = findViewById(R.id.etCalendar);

        // Bind views
        btnBack = findViewById(R.id.btnBack);
        tabSales = findViewById(R.id.tabSales);
        tabCalendar = findViewById(R.id.tabCalendar);
        tvRevenue = findViewById(R.id.tvRevenue);
        tvProductsSold = findViewById(R.id.tvProductsSold);

        // Chart bars
        bar1Value = findViewById(R.id.bar1Value);
        bar2Value = findViewById(R.id.bar2Value);
        bar3Value = findViewById(R.id.bar3Value);
        bar4Value = findViewById(R.id.bar4Value);
        bar1 = findViewById(R.id.bar1);
        bar2 = findViewById(R.id.bar2);
        bar3 = findViewById(R.id.bar3);
        bar4 = findViewById(R.id.bar4);


        // Back button logic
        btnBack.setOnClickListener(v -> finish());

        // Tab click listeners
        tabSales.setOnClickListener(v -> {
            tabSales.setCardBackgroundColor(getColor(R.color.green));
            tabCalendar.setCardBackgroundColor(getColor(R.color.blue));
            Toast.makeText(this, "Sales Tab Selected", Toast.LENGTH_SHORT).show();
        });

        tabCalendar.setOnClickListener(v -> {
            tabCalendar.setCardBackgroundColor(getColor(R.color.green));
            tabSales.setCardBackgroundColor(getColor(R.color.blue));
            Toast.makeText(this, "Calendar Tab Selected", Toast.LENGTH_SHORT).show();
        });

        // Set data dynamically (dummy data here)
        tvRevenue.setText("R45,678.90");
        tvProductsSold.setText("2,405");

        // Optional: dynamically change bar heights (requires ConstraintLayout adjustment if used)
        // Example: bar1.getLayoutParams().height = 150; bar1.requestLayout();

        etCalendar = findViewById(R.id.etCalendar);

        etCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        setupFooterNavigation();

    }

    private void setupFooterNavigation() {
        findViewById(R.id.bottomNavigation).findViewById(R.id.reportsFooter).setOnClickListener(v -> {
            // Already on home page, maybe just refresh
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.reservationsFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Track_Sales.this, activity_manage_reservations.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.homeFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Track_Sales.this, Manager.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.staffFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Track_Sales.this, staff_activity.class);
            startActivity(intent);
        });
        findViewById(R.id.bottomNavigation).findViewById(R.id.menuFooter).setOnClickListener(v -> {
            Intent intent = new Intent(Track_Sales.this, manage_menu.class);
            startActivity(intent);
        });



    }

    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Format the date as needed and set it in the EditText
                        String date = String.format(Locale.getDefault(), "%02d/%02d/%d", dayOfMonth, monthOfYear + 1, year);
                        etCalendar.setText(date);
                    }
                },
                year, month, day);

        datePickerDialog.show();
    }
}