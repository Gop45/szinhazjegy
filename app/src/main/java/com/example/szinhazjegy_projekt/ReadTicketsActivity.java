package com.example.szinhazjegy_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ReadTicketsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_tickets);
        Button btnHamlet = findViewById(R.id.btnHamlet);
        Button btnJovobeli = findViewById(R.id.btnJovobeli);
        Button btnOlcso = findViewById(R.id.btnOlcso);
        LinearLayout container = findViewById(R.id.jegyListaContainer);

        btnHamlet.setOnClickListener(v -> listazHamlet(container));
        btnJovobeli.setOnClickListener(v -> listazJovobeli(container));
        btnOlcso.setOnClickListener(v -> listazOlcso(container));

    }

    @Override
    protected void onResume() {
        super.onResume();

        LinearLayout container = findViewById(R.id.jegyListaContainer);
        container.removeAllViews();

        FirebaseFirestore.getInstance()
                .collection("jegyek")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        SzinHazJegy jegy = doc.toObject(SzinHazJegy.class);
                        jegy.setId(doc.getId());

                        String jegySzoveg = "🎭 Előadás: " + jegy.getEloadas() + "\n"
                                + "📅 Dátum: " + jegy.getDatum() + "\n"
                                + "📍 Helyszín: " + jegy.getHelyszin() + "\n"
                                + "🪑 Sor: " + jegy.getSor() + ", Szék: " + jegy.getSzek() + "\n"
                                + "💸 Ár: " + jegy.getAr() + " Ft\n";

                        TextView t = new TextView(this);
                        t.setText(jegySzoveg);
                        t.setTextSize(16);
                        t.setPadding(32, 24, 32, 24);
                        t.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
                        t.setTextColor(getResources().getColor(android.R.color.black));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 16, 0, 0);
                        t.setLayoutParams(params);

                        t.setOnClickListener(v -> {
                            Intent intent = new Intent(ReadTicketsActivity.this, EditTicketActivity.class);
                            intent.putExtra("id", jegy.getId());
                            intent.putExtra("eloadas", jegy.getEloadas());
                            intent.putExtra("datum", jegy.getDatum());
                            intent.putExtra("helyszin", jegy.getHelyszin());
                            intent.putExtra("sor", jegy.getSor());
                            intent.putExtra("szek", jegy.getSzek());
                            intent.putExtra("ar", jegy.getAr());
                            startActivity(intent);
                        });

                        container.addView(t);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Hiba a jegyek betöltésekor.", Toast.LENGTH_SHORT).show();
                });
    }

    private void listazHamlet(LinearLayout container) {
        container.removeAllViews();
        FirebaseFirestore.getInstance()
                .collection("jegyek")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        SzinHazJegy jegy = doc.toObject(SzinHazJegy.class);
                        String eloadas = jegy.getEloadas();

                        if (eloadas != null && eloadas.trim().equalsIgnoreCase("Hamlet")) {
                            TextView tv = new TextView(this);
                            tv.setText("🎭 Előadás: " + eloadas + "\n📅 Dátum: " + jegy.getDatum());
                            tv.setTextSize(16);
                            tv.setPadding(32, 24, 32, 24);
                            tv.setBackgroundResource(android.R.drawable.dialog_holo_light_frame); // keretes megjelenés
                            tv.setTextColor(getResources().getColor(android.R.color.black));
                            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                    LinearLayout.LayoutParams.MATCH_PARENT,
                                    LinearLayout.LayoutParams.WRAP_CONTENT);
                            params.setMargins(0, 16, 0, 0);
                            tv.setLayoutParams(params);
                            container.addView(tv);

                        }
                    }
                });
    }


    private void listazJovobeli(LinearLayout container) {
        container.removeAllViews();
        FirebaseFirestore.getInstance()
                .collection("jegyek")
                .whereGreaterThan("datum", "2024-12-31")
                .orderBy("datum")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        SzinHazJegy jegy = doc.toObject(SzinHazJegy.class);
                        TextView tv = new TextView(this);
                        tv.setText("🎭 Előadás: " + jegy.getEloadas() + "\n📅 Dátum: " + jegy.getDatum());
                        tv.setTextSize(16);
                        tv.setPadding(32, 24, 32, 24);
                        tv.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
                        tv.setTextColor(getResources().getColor(android.R.color.black));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 16, 0, 0);
                        tv.setLayoutParams(params);
                        container.addView(tv);

                    }
                });
    }

    private void listazOlcso(LinearLayout container) {
        container.removeAllViews();
        FirebaseFirestore.getInstance()
                .collection("jegyek")
                .orderBy("ar")
                .limit(5)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        SzinHazJegy jegy = doc.toObject(SzinHazJegy.class);
                        TextView tv = new TextView(this);
                        tv.setText("🎭 Előadás: " + jegy.getEloadas() + "\n📅 Dátum: " + jegy.getDatum());
                        tv.setTextSize(16);
                        tv.setPadding(32, 24, 32, 24);
                        tv.setBackgroundResource(android.R.drawable.dialog_holo_light_frame);
                        tv.setTextColor(getResources().getColor(android.R.color.black));

                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT);
                        params.setMargins(0, 16, 0, 0);
                        tv.setLayoutParams(params);
                        container.addView(tv);

                    }
                });
    }


}
