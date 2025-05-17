package com.example.thegardenofeatn;


import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AuthManager {
    private final FirebaseAuth auth;
    private final DatabaseReference databaseRef;

    public AuthManager() {
        this.auth = FirebaseAuth.getInstance();
        this.databaseRef = FirebaseDatabase.getInstance().getReference("users");
    }

    public void loginUser(String email, String password, AuthCallback callback) {
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onAuthSuccess(email);
                    } else {
                        callback.onAuthFailure(task.getException());
                    }
                });
    }

    public void checkUserRole(String email, RoleCheckCallback callback) {
        String dbKey = email.toLowerCase().replace(".", ",");

        databaseRef.child(dbKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    callback.onRoleChecked(role);
                } else {
                    callback.onRoleNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onDatabaseError(error);
            }
        });
    }

    public void createUserRecord(String email, String defaultRole, DatabaseCallback callback) {
        String dbKey = email.toLowerCase().replace(".", ",");

        HashMap<String, Object> userData = new HashMap<>();
        userData.put("role", defaultRole);
        userData.put("email", email);
        userData.put("createdAt", ServerValue.TIMESTAMP);

        databaseRef.child(dbKey).setValue(userData)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }

    // Chef
    public void verifyChefRole(String email, RoleVerificationCallback callback) {
        String dbKey = email.toLowerCase().replace(".", ",");

        databaseRef.child(dbKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    if ("chef".equalsIgnoreCase(role)) {
                        callback.onRoleVerified(true);
                    } else {
                        callback.onRoleVerified(false);
                    }
                } else {
                    callback.onUserNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onVerificationFailed(error.toException());
            }
        });
    }

    // Manager
    public void verifyManagerRole(String email, RoleVerificationCallback callback) {
        String dbKey = email.toLowerCase().replace(".", ",");

        databaseRef.child(dbKey).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String role = snapshot.child("role").getValue(String.class);
                    if ("manager".equalsIgnoreCase(role)) {
                        callback.onRoleVerified(true);
                    } else {
                        callback.onRoleVerified(false);
                    }
                } else {
                    callback.onUserNotFound();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onVerificationFailed(error.toException());
            }
        });
    }

// Reuse the same RoleVerificationCallback interface from Chef

    public interface RoleVerificationCallback {
        void onRoleVerified(boolean isChef);
        void onUserNotFound();
        void onVerificationFailed(Exception exception);
    }

    public void signOut() {
        auth.signOut();
    }

    // Interface definitions
    public interface AuthCallback {
        void onAuthSuccess(String email);
        void onAuthFailure(Exception exception);
    }

    public interface RoleCheckCallback {
        void onRoleChecked(String role);
        void onRoleNotFound();
        void onDatabaseError(DatabaseError error);
    }

    public interface DatabaseCallback {
        void onSuccess();
        void onFailure(Exception exception);
    }
}
