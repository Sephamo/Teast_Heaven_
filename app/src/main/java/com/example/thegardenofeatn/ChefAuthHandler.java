package com.example.thegardenofeatn;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

// ChefAuthHandler.java
public class ChefAuthHandler {
    private final AuthManager authManager;
    private final Context context;
    private AlertDialog loginDialog;

    public ChefAuthHandler(Context context, AuthManager authManager) {
        this.context = context;
        this.authManager = authManager;
    }

    public void showChefLoginDialog() {
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

            handleChefLogin(email, password);
        });

        loginDialog.show();
    }

    private void handleChefLogin(String email, String password) {
        authManager.loginUser(email, password, new AuthManager.AuthCallback() {
            @Override
            public void onAuthSuccess(String email) {
                verifyChefRole(email);
            }

            @Override
            public void onAuthFailure(Exception exception) {
                Toast.makeText(context,
                        "Login failed: " + exception.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void verifyChefRole(String email) {
        authManager.verifyChefRole(email, new AuthManager.RoleVerificationCallback() {
            @Override
            public void onRoleVerified(boolean isChef) {
                if (isChef) {
                    navigateToChefDashboard();
                } else {
                    showAccessDenied();
                }
            }

            @Override
            public void onUserNotFound() {
                handleNewChefUser(email);
            }

            @Override
            public void onVerificationFailed(Exception exception) {
                showVerificationError(exception);
            }
        });
    }

    private void navigateToChefDashboard() {
        loginDialog.dismiss();
        context.startActivity(new Intent(context, ChefActivity.class));
    }

    private void showAccessDenied() {
        authManager.signOut();
        Toast.makeText(context,
                "Access denied: Chef privileges required",
                Toast.LENGTH_LONG).show();
    }

    private void handleNewChefUser(String email) {
        authManager.createUserRecord(email, "chef", new AuthManager.DatabaseCallback() {
            @Override
            public void onSuccess() {
                navigateToChefDashboard();
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

    private void showVerificationError(Exception exception) {
        Toast.makeText(context,
                "Verification error: " + exception.getMessage(),
                Toast.LENGTH_LONG).show();
    }
}
