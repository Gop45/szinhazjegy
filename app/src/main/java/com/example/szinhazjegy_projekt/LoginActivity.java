package com.example.szinhazjegy_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmailEditText, loginPasswordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        loginEmailEditText = findViewById(R.id.loginEmailEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(v -> {
            Animation spin = AnimationUtils.loadAnimation(this, R.anim.test_spin);
            loginButton.startAnimation(spin);

            String email = loginEmailEditText.getText().toString().trim();
            String password = loginPasswordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Minden mező kitöltése kötelező", Toast.LENGTH_SHORT).show();
                return;
            }

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Sikeres bejelentkezés!", Toast.LENGTH_SHORT).show();
                            new android.os.Handler().postDelayed(() -> {
                                startActivity(new Intent(this, MainActivity.class));
                                finish();
                            }, 300);
                        } else {
                            Toast.makeText(this, "Hiba: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}

