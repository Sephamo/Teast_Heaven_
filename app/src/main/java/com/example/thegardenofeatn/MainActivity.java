package com.example.thegardenofeatn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DatabaseError;

// MainActivity.java
public class MainActivity extends AppCompatActivity {
    private CardView waiterCard;
    private AlertDialog loginDialog;
    private AuthManager authManager;
    private CardView chefCard;
    private ChefAuthHandler chefAuthHandler;
    private CardView managerCard;
    private ManagerAuthHandler managerAuthHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AuthManager, ChefManager, ManagerAuth
        authManager = new AuthManager();
        chefAuthHandler = new ChefAuthHandler(this, authManager);
        managerAuthHandler = new ManagerAuthHandler(this, authManager);

        // Initialize UI
        waiterCard = findViewById(R.id.waiterCard);
        waiterCard.setOnClickListener(v -> showLoginDialog());

        //Chef card
        chefCard = findViewById(R.id.chefCard);
        chefCard.setOnClickListener(v -> chefAuthHandler.showChefLoginDialog());

        managerCard = findViewById(R.id.managerCard);
        managerCard.setOnClickListener(v -> managerAuthHandler.showManagerLoginDialog());
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_signin, null);

        EditText etEmail = dialogView.findViewById(R.id.login_username);
        EditText etPassword = dialogView.findViewById(R.id.login_password);
        Button btnLogin = dialogView.findViewById(R.id.login_button);

        builder.setView(dialogView);
        loginDialog = builder.create();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim().toLowerCase();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            handleLogin(email, password);
        });

        loginDialog.show();
    }

    private void handleLogin(String email, String password) {
        authManager.loginUser(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onAuthSuccess(String email) {
                checkUserRole(email);
            }

            @Override
            public void onAuthFailure(Exception exception) {
                Toast.makeText(MainActivity.this,
                        "Login failed: " + exception.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void checkUserRole(String email) {
        authManager.checkUserRole(email, new AuthManager.RoleCheckCallback() {
            @Override
            public void onRoleChecked(String role) {
                if ("waiter".equalsIgnoreCase(role)) {
                    startActivity(new Intent(MainActivity.this, Waiter.class));
                    loginDialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this,
                            "Access denied for role: " + role,
                            Toast.LENGTH_LONG).show();
                    authManager.signOut();
                }
            }

            @Override
            public void onRoleNotFound() {
                // Auto-create user record with default 'waiter' role
                authManager.createUserRecord(email, "waiter", new AuthManager.DatabaseCallback() {
                    @Override
                    public void onSuccess() {
                        startActivity(new Intent(MainActivity.this, Waiter.class));
                        loginDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        Toast.makeText(MainActivity.this,
                                "Account setup failed: " + exception.getMessage(),
                                Toast.LENGTH_LONG).show();
                        authManager.signOut();
                    }
                });
            }

            @Override
            public void onDatabaseError(DatabaseError error) {
                Toast.makeText(MainActivity.this,
                        "Database error: " + error.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

