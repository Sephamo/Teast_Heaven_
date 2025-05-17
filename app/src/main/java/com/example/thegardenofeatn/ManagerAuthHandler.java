package com.example.thegardenofeatn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

// ManagerAuthHandler.java
// ManagerAuthHandler.java
public class ManagerAuthHandler {
    private final AuthManager authManager;
    private final Context context;
    private AlertDialog loginDialog;

    public ManagerAuthHandler(Context context, AuthManager authManager) {
        this.context = context;
        this.authManager = authManager;
    }

    public void showManagerLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_signin, null);

        EditText etEmail = dialogView.findViewById(R.id.login_username);
        EditText etPassword = dialogView.findViewById(R.id.login_password);
        Button btnLogin = dialogView.findViewById(R.id.login_button);

        builder.setView(dialogView);
        loginDialog = builder.create();

        btnLogin.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim().toLowerCase();
            String password = etPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            handleManagerLogin(email, password);
        });

        loginDialog.show();
    }

    private void handleManagerLogin(String email, String password) {
        authManager.loginUser(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onAuthSuccess(String email) {
                verifyManagerRole(email);
            }

            @Override
            public void onAuthFailure(Exception exception) {
                Toast.makeText(context,
                        "Login failed: " + exception.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void verifyManagerRole(String email) {
        authManager.verifyManagerRole(email, new AuthManager.RoleVerificationCallback() {
            @Override
            public void onRoleVerified(boolean isManager) {
                if (isManager) {
                    navigateToManagerDashboard();
                } else {
                    // Auto-create manager account if not found
                    createNewManagerAccount(email);
                }
            }

            @Override
            public void onUserNotFound() {
                // Auto-create manager account
                createNewManagerAccount(email);
            }

            @Override
            public void onVerificationFailed(Exception exception) {
                Toast.makeText(context,
                        "Error verifying role: " + exception.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void createNewManagerAccount(String email) {
        authManager.createUserRecord(email, "manager", new AuthManager.DatabaseCallback() {
            @Override
            public void onSuccess() {
                navigateToManagerDashboard();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(context,
                        "Account setup failed: " + exception.getMessage(),
                        Toast.LENGTH_LONG).show();
                authManager.signOut();
            }
        });
    }

    private void navigateToManagerDashboard() {
        loginDialog.dismiss();
        context.startActivity(new Intent(context, Manager.class));
    }
}
