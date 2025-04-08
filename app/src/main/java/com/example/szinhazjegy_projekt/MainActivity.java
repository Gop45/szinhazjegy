package com.example.szinhazjegy_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button loginButton, registerButton, logoutButton, aboutButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        welcomeText = findViewById(R.id.welcomeText);
        loginButton = findViewById(R.id.openLoginButton);
        registerButton = findViewById(R.id.openRegisterButton);
        logoutButton = findViewById(R.id.logoutButton);
        aboutButton = findViewById(R.id.openAboutButton);

        if (user != null) {
            String email = user.getEmail();
            welcomeText.setText("Üdv, " + email + "!");
            logoutButton.setVisibility(View.VISIBLE);
            loginButton.setVisibility(View.GONE);
            registerButton.setVisibility(View.GONE);
        } else {
            welcomeText.setText("Üdvözlünk az alkalmazásban!");
            logoutButton.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);
            registerButton.setVisibility(View.VISIBLE);
        }

        loginButton.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
        });

        registerButton.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            recreate();
        });
        aboutButton.setOnClickListener(v -> {
            startActivity(new Intent(this, AboutActivity.class));
        });


    }
}