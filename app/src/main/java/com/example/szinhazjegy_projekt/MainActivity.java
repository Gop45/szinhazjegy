package com.example.szinhazjegy_projekt;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import android.Manifest;


public class MainActivity extends AppCompatActivity {

    private TextView welcomeText;
    private Button loginButton, registerButton, logoutButton, aboutButton;
    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button jegyekListazasaButton = findViewById(R.id.jegyekListazasaButton);
        jegyekListazasaButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ReadTicketsActivity.class);
            startActivity(intent);
        });


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "jegy_ertesites",
                    "Jegy Értesítések",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "jegy_ertesites")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Színházjegy alkalmazás")
                    .setContentText("Köszöntünk az alkalmazásban!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1001, builder.build());
        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        } else {
            Toast.makeText(this, "Helymeghatározás már engedélyezve", Toast.LENGTH_SHORT).show();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 3);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    "jegy_ertesites",
                    "Jegy Értesítések",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "jegy_ertesites")
                    .setSmallIcon(R.drawable.ic_launcher_foreground)
                    .setContentTitle("Színházjegy alkalmazás")
                    .setContentText("Köszöntünk az alkalmazásban!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(1001, builder.build());
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long triggerTime = System.currentTimeMillis() + 10000; //

        alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Helymeghatározás engedélyezve!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Helymeghatározás megtagadva", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            Toast.makeText(this, "Üdv újra, " + currentUser.getEmail(), Toast.LENGTH_SHORT).show();

            Button jegyekListazasaButton = findViewById(R.id.jegyekListazasaButton);
            jegyekListazasaButton.setVisibility(View.VISIBLE);

            Button ujJegyButton = findViewById(R.id.ujJegyButton);
            ujJegyButton.setVisibility(View.VISIBLE);
            ujJegyButton.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddTicketActivity.class);
                startActivity(intent);
            });
        }
    }



}