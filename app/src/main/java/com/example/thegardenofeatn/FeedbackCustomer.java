package com.example.thegardenofeatn;

import java.util.Map;
import java.util.HashMap;

public class FeedbackCustomer {
    private float rating;
    private String experience;
    private String feedbackText;
    private Map<String, Boolean> categories;

    // Default constructor for Firebase deserialization
    public FeedbackCustomer() {
        // This constructor is necessary for Firebase deserialization to work
    }

    // Parameterized constructor to set values
    public FeedbackCustomer(float rating, String experience, String feedbackText, boolean service, boolean foodQuality, boolean ambience) {
        this.rating = rating;
        this.experience = experience;
        this.feedbackText = feedbackText;
        // Create and initialize the categories map
        this.categories = new HashMap<>();
        this.categories.put("service", service);
        this.categories.put("foodQuality", foodQuality);
        this.categories.put("ambience", ambience);
    }

    public float getRating() {
        return rating;
    }

    public String getExperience() {
        return experience;
    }

    public String getFeedbackText() {
        return feedbackText;
    }

    public Map<String, Boolean> getCategories() {
        return categories;
    }
}


