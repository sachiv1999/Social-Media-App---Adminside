package com.example.socialmedia;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPage extends AppCompatActivity {
    private EditText emailLogin, passwordLogin;
    private Button loginBtn,forgotPasswordBtn;
    private ProgressDialog progressDialog;



    private FirebaseAuth firebaseAuth;
    private final String allowedEmail = "manavpandya42@gmail.com";  // Replace with your actual admin email


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        firebaseAuth = FirebaseAuth.getInstance();

        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);
        loginBtn = findViewById(R.id.loginBtn);
        forgotPasswordBtn = findViewById(R.id.forgotPassword);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");

        loginBtn.setOnClickListener(v -> loginUser());
        forgotPasswordBtn.setOnClickListener(v -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                startActivity(new Intent(LoginPage.this, ResetPasswordActivity.class));
            } else {
                Toast.makeText(LoginPage.this, "Please login first to reset your password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loginUser() {
        String email = emailLogin.getText().toString().trim();
        String password = passwordLogin.getText().toString().trim();

        if (TextUtils.isEmpty(email)) {
            emailLogin.setError("Email is required");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordLogin.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            passwordLogin.setError("Password must be at least 6 characters");
            return;
        }

        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginPage.this, task -> {
                    progressDialog.dismiss();

                    if (task.isSuccessful()) {
                        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                        if (currentUser != null && currentUser.getEmail() != null &&
                                currentUser.getEmail().equalsIgnoreCase(allowedEmail)) {

                            Toast.makeText(LoginPage.this, "Login successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginPage.this, HomeActivity.class));
                            finish();

                        } else {
                            firebaseAuth.signOut(); // 🚫 Sign out unapproved user
                            Toast.makeText(LoginPage.this, "Access denied: Email not authorized", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(LoginPage.this, "Authentication failed: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}