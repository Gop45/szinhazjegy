package com.example.szinhazjegy_projekt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
                        t.setPadding(0, 16, 0, 16);
                        t.setTextSize(16);
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

}
