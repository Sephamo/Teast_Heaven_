package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class landing_page extends AppCompatActivity {

    private Button bookNowButton;
    private GoldDustParticleView goldDustView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        bookNowButton = findViewById(R.id.bookNowButton);
        goldDustView = findViewById(R.id.goldDustView);

        bookNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(landing_page.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        // Start emitting gold dust continuously from the border of the button
        bookNowButton.post(new Runnable() {
            @Override
            public void run() {
                float centerX = bookNowButton.getX() + bookNowButton.getWidth() / 2f;
                float centerY = bookNowButton.getY() + bookNowButton.getHeight() / 2f;
                float width = bookNowButton.getWidth();
                float height = bookNowButton.getHeight();
                goldDustView.startEmitting(centerX, centerY, width, height);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (goldDustView != null) {
            goldDustView.stopEmitting();
        }
    }
}



