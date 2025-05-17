package com.example.szinhazjegy_projekt;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditTicketActivity extends AppCompatActivity {

    private EditText eloadasEdit, datumEdit, helyszinEdit, sorEdit, szekEdit, arEdit;
    private Button mentButton;
    private String jegyId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_ticket);

        eloadasEdit = findViewById(R.id.eloadasEdit);
        datumEdit = findViewById(R.id.datumEdit);
        helyszinEdit = findViewById(R.id.helyszinEdit);
        sorEdit = findViewById(R.id.sorEdit);
        szekEdit = findViewById(R.id.szekEdit);
        arEdit = findViewById(R.id.arEdit);
        mentButton = findViewById(R.id.mentButton);

        jegyId = getIntent().getStringExtra("id");
        eloadasEdit.setText(getIntent().getStringExtra("eloadas"));
        datumEdit.setText(getIntent().getStringExtra("datum"));
        helyszinEdit.setText(getIntent().getStringExtra("helyszin"));
        sorEdit.setText(String.valueOf(getIntent().getIntExtra("sor", 0)));
        szekEdit.setText(String.valueOf(getIntent().getIntExtra("szek", 0)));
        arEdit.setText(String.valueOf(getIntent().getIntExtra("ar", 0)));
        Button torlesButton = findViewById(R.id.torlesButton);
        mentButton.setOnClickListener(v -> {
            String eloadas = eloadasEdit.getText().toString();
            String datum = datumEdit.getText().toString();
            String helyszin = helyszinEdit.getText().toString();
            int sor = Integer.parseInt(sorEdit.getText().toString());
            int szek = Integer.parseInt(szekEdit.getText().toString());
            int ar = Integer.parseInt(arEdit.getText().toString());

            FirebaseFirestore.getInstance().collection("jegyek").document(jegyId)
                    .update("eloadas", eloadas,
                            "datum", datum,
                            "helyszin", helyszin,
                            "sor", sor,
                            "szek", szek,
                            "ar", ar)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Jegy frissítve!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

        torlesButton.setOnClickListener(v -> {
            FirebaseFirestore.getInstance().collection("jegyek")
                    .document(jegyId)
                    .delete()
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(this, "Jegy törölve.", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Hiba a törlés során: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

    }
}
