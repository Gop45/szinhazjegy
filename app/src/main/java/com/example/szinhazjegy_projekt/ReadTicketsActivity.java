package com.example.szinhazjegy_projekt;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class ReadTicketsActivity extends AppCompatActivity {

    private TextView jegyListaTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_tickets);

        jegyListaTextView = findViewById(R.id.jegyListaTextView);

        FirebaseFirestore.getInstance()
                .collection("jegyek")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    StringBuilder builder = new StringBuilder();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        SzinHazJegy jegy = doc.toObject(SzinHazJegy.class);
                        jegy.setId(doc.getId()); // 👈 manuálisan állítsuk be az id-t
                        builder.append("Előadás: ").append(jegy.getEloadas()).append("\n")
                                .append("Dátum: ").append(jegy.getDatum()).append("\n")
                                .append("Helyszín: ").append(jegy.getHelyszin()).append("\n")
                                .append("Sor: ").append(jegy.getSor()).append(", Szék: ").append(jegy.getSzek()).append("\n")
                                .append("Ár: ").append(jegy.getAr()).append(" Ft\n\n");
                    }
                    jegyListaTextView.setText(builder.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e("FIREBASE_ERROR", "Lekérdezési hiba: " + e.getMessage(), e);
                    jegyListaTextView.setText("Hiba történt a lekérdezés során: " + e.getMessage());
                });


    }
}
