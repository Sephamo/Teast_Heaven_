package com.example.thegardenofeatn;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FeedbackManager {

    private final DatabaseReference feedbackRef;

    public FeedbackManager() {
        // Initialize Firebase reference to "feedback" node
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        feedbackRef = database.getReference("feedback");
    }

    public void submitFeedback(final Context context, FeedbackCustomer feedback) {
        // Generate a new unique key under "feedback"
        String feedbackId = feedbackRef.push().getKey();

        if (feedbackId != null) {
            // Save feedback under the generated ID
            feedbackRef.child(feedbackId).setValue(feedback, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(com.google.firebase.database.DatabaseError error, DatabaseReference ref) {
                    if (error == null) {
                        Toast.makeText(context, "Feedback submitted successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Failed to submit feedback: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            Toast.makeText(context, "Something went wrong. Try again.", Toast.LENGTH_SHORT).show();
        }
    }
}

