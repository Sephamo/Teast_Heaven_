package com.example.thegardenofeatn;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Feedback extends AppCompatActivity {
    private RatingBar ratingBar;
    private EditText experienceEditText, feedbackEditText;
    private CheckBox serviceCheckBox, foodQualityCheckBox, ambienceCheckBox;
    private Button submitFeedbackButton;

    private FeedbackManager feedbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_feedback);

        // Initialize views
        ratingBar = findViewById(R.id.ratingBar);
        experienceEditText = findViewById(R.id.experienceEditText);
        feedbackEditText = findViewById(R.id.feedbackEditText);
        serviceCheckBox = findViewById(R.id.serviceCheckBox);
        foodQualityCheckBox = findViewById(R.id.foodQualityCheckBox);
        ambienceCheckBox = findViewById(R.id.ambienceCheckBox);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        // Initialize manager
        feedbackManager = new FeedbackManager();

        // Set button click listener
        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        float rating = ratingBar.getRating();
        String experience = experienceEditText.getText().toString().trim();
        String feedbackText = feedbackEditText.getText().toString().trim();
        boolean service = serviceCheckBox.isChecked();
        boolean foodQuality = foodQualityCheckBox.isChecked();
        boolean ambience = ambienceCheckBox.isChecked();

        // Create FeedbackCustomer object
        FeedbackCustomer feedback = new FeedbackCustomer(rating, experience, feedbackText, service, foodQuality, ambience);


        // Submit to Firebase
        feedbackManager.submitFeedback(Feedback.this, feedback);

// Bottom navigation
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.nav_review);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.nav_home) {
                    startActivity(new Intent(Feedback.this, CustomerActivity.class));
                    return true;
                } else if (itemId == R.id.nav_reservation) {
                    startActivity(new Intent(Feedback.this, ReservationActivity.class));
                    return true;
                } else if (itemId == R.id.nav_menu) {
                    startActivity(new Intent(Feedback.this, ManuPage.class));
                    return true;
                }

                return false;
            }
        });

    }
}