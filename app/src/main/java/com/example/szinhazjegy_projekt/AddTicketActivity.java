package com.example.szinhazjegy_projekt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddTicketActivity extends AppCompatActivity {

    private EditText eloadasEdit, datumEdit, helyszinEdit, sorEdit, szekEdit, arEdit;
    private Button mentesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        eloadasEdit = findViewById(R.id.eloadasEdit);
        datumEdit = findViewById(R.id.datumEdit);
        helyszinEdit = findViewById(R.id.helyszinEdit);
        sorEdit = findViewById(R.id.sorEdit);
        szekEdit = findViewById(R.id.szekEdit);
        arEdit = findViewById(R.id.arEdit);
        mentesButton = findViewById(R.id.mentesButton);

        mentesButton.setOnClickListener(v -> {
            String eloadas = eloadasEdit.getText().toString();
            String datum = datumEdit.getText().toString();
            String helyszin = helyszinEdit.getText().toString();
            int sor = Integer.parseInt(sorEdit.getText().toString());
            int szek = Integer.parseInt(szekEdit.getText().toString());
            int ar = Integer.parseInt(arEdit.getText().toString());

            Map<String, Object> jegy = new HashMap<>();
            jegy.put("eloadas", eloadas);
            jegy.put("datum", datum);
            jegy.put("helyszin", helyszin);
            jegy.put("sor", sor);
            jegy.put("szek", szek);
            jegy.put("ar", ar);

            FirebaseFirestore.getInstance().collection("jegyek")
                    .add(jegy)
                    .addOnSuccessListener(documentReference -> {
                        Toast.makeText(this, "Jegy hozzáadva!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba történt: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });
    }
}
