package com.example.thegardenofeatn;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.View;
import android.widget.Button;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TimeSlotButtonManager {
    private final Button[] buttons;
    private final int defaultColor;
    private final int clickedColor;
    private final int unavailableColor;
    private Button selectedButton;

    public TimeSlotButtonManager(Context context, Button... buttons) {
        this.buttons = buttons;
        this.defaultColor = context.getResources().getColor(R.color.whiteColor);
        this.clickedColor = context.getResources().getColor(R.color.clickedColor);
        this.unavailableColor = context.getResources().getColor(R.color.unavailableColor); // Add this color to colors.xml
    }

    public void loadAvailability(final OnTimeSlotSelectedListener listener) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("availableTimeSlots");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (Button button : buttons) {
                    String time = button.getText().toString();
                    boolean available = snapshot.child(time).getValue(Boolean.class) != null
                            && snapshot.child(time).getValue(Boolean.class);

                    if (available) {
                        button.setEnabled(true);
                        button.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                resetColors();
                                button.setBackgroundTintList(ColorStateList.valueOf(clickedColor));
                                selectedButton = button;
                                listener.onTimeSlotSelected(time);
                            }
                        });
                    } else {
                        button.setEnabled(false);
                        button.setBackgroundTintList(ColorStateList.valueOf(unavailableColor));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Handle error if needed
            }
        });
    }

    private void resetColors() {
        for (Button button : buttons) {
            if (button.isEnabled()) {
                button.setBackgroundTintList(ColorStateList.valueOf(defaultColor));
            }
        }
    }

    public interface OnTimeSlotSelectedListener {
        void onTimeSlotSelected(String timeSlot);
    }
}



