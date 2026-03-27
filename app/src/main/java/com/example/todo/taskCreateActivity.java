package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Date;

public class taskCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        EditText editIntitule = findViewById(R.id.editIntitule);
        EditText editDesc = findViewById(R.id.editDesc);
        EditText editContexte = findViewById(R.id.editContexte);
        Button btnSauvegarder = findViewById(R.id.btnSauvegarder);

        btnSauvegarder.setOnClickListener(v -> {
            String intitule = editIntitule.getText().toString();
            String desc = editDesc.getText().toString();
            String contexte = editContexte.getText().toString();

            // Création de l'objet (date et durée simplifiées pour l'exemple)
            Tache laTache = new Tache(intitule, desc, 1.0f, new Date(), new Date(), 0, contexte, "");

            // Renvoi à l'activité appelante
            Intent resultIntent = new Intent();
            resultIntent.putExtra("NOUVELLE_TACHE", laTache);
            setResult(RESULT_OK, resultIntent);
            finish(); // Ferme l'activité
        });
    }
}