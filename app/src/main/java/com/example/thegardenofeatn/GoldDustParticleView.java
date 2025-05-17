package com.example.thegardenofeatn;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

public class GoldDustParticleView extends View {

    private ArrayList<Particle> particles;
    private Random random;
    private Paint particlePaint;
    private boolean isEmitting = false;
    private Handler handler = new Handler();
    private int maxParticles = 30;

    // Gold color variations
    private final int[] goldColors = {
            Color.parseColor("#D4AF37"), // Gold
            Color.parseColor("#CFB53B"), // Old Gold
            Color.parseColor("#F5D580"), // Light Gold
            Color.parseColor("#E6BE8A")  // Golden Sand
    };

    public GoldDustParticleView(Context context) {
        super(context);
        init();
    }

    public GoldDustParticleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        particles = new ArrayList<>();
        random = new Random();
        particlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        particlePaint.setStyle(Paint.Style.FILL);
    }

    public void startEmitting(float x, float y, float width, float height) {
        isEmitting = true;
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isEmitting) {
                    for (int i = 0; i < 10; i++) {
                        float angle = (float) (Math.random() * 2 * Math.PI);
                        float radius = (float) (Math.random() * (Math.min(width, height) / 2));

                        // Emit particles from border
                        float startX = x + width / 2 + (float) Math.cos(angle) * width / 2;
                        float startY = y + height / 2 + (float) Math.sin(angle) * height / 2;

                        // Speed and direction
                        float speed = 2f + (float) Math.random() * 4f;
                        float dx = (float) Math.cos(angle) * speed;
                        float dy = (float) Math.sin(angle) * speed;

                        // Add particle
                        particles.add(new Particle(startX, startY, dx, dy));
                    }
                    invalidate();
                    handler.postDelayed(this, 50); // Continue emission
                }
            }
        });
    }

    public void stopEmitting() {
        isEmitting = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Update and draw particles
        for (int i = particles.size() - 1; i >= 0; i--) {
            Particle p = particles.get(i);
            p.update();

            if (p.alpha <= 0) {
                particles.remove(i);
                continue;
            }

            // Set particle color and alpha for glowing effect
            particlePaint.setColor(p.color);
            particlePaint.setAlpha((int) (p.alpha * 255));

            // Radial gradient for glowing effect
            RadialGradient gradient = new RadialGradient(p.x, p.y, p.size, p.color, Color.TRANSPARENT, Shader.TileMode.CLAMP);
            particlePaint.setShader(gradient);

            canvas.drawCircle(p.x, p.y, p.size, particlePaint);
            particlePaint.setShader(null);
        }

        if (!particles.isEmpty() || isEmitting) {
            invalidate();
        }
    }

    private class Particle {
        float x, y;       // Position
        float vx, vy;     // Velocity
        float size;       // Size of particle
        int color;        // Color
        float alpha = 1f; // Transparency
        int lifespan;     // Lifespan of particle
        int age = 0;      // Current age

        Particle(float x, float y, float vx, float vy) {
            this.x = x;
            this.y = y;
            this.vx = vx;
            this.vy = vy;
            this.size = random.nextFloat() * 6 + 2;
            this.color = goldColors[random.nextInt(goldColors.length)];
            this.lifespan = random.nextInt(20) + 30;
        }

        void update() {
            // Update position
            x += vx;
            y += vy;

            // Add random movement
            vx += (random.nextFloat() - 0.5f) * 0.3f;
            vy += (random.nextFloat() - 0.5f) * 0.3f + 0.05f; // Slight upward drift

            // Slow down over time
            vx *= 0.99f;
            vy *= 0.99f;

            // Age the particle
            age++;
            alpha = 1f - ((float) age / lifespan); // Fade out as it ages
            size *= 0.99f; // Shrink slightly over time
        }
    }
}


