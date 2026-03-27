package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

public class taskCreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);

        EditText editIntitule = findViewById(R.id.editIntitule);
        EditText editDesc = findViewById(R.id.editDesc);
        EditText editContexte = findViewById(R.id.editContexte);
        EditText editDateDebut = findViewById(R.id.editDateDebut);
        EditText editDateFin = findViewById(R.id.editDateFin);
        EditText editLienWeb = findViewById(R.id.editLienWebCreate);
        Spinner spinnerStatus = findViewById(R.id.spinnerStatus);
        Button btnSauvegarder = findViewById(R.id.btnSauvegarder);

        // Configuration du Spinner des statuts
        String[] statuts = {"Pas commencée", "En cours", "Terminée"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statuts);
        spinnerStatus.setAdapter(adapter);

        btnSauvegarder.setOnClickListener(v -> {
            // Récupération de l'index du spinner (0, 1 ou 2) qui correspond parfaitement à notre logique de statut int
            int status = spinnerStatus.getSelectedItemPosition();

            Tache laTache = new Tache(
                    editIntitule.getText().toString(),
                    editDesc.getText().toString(),
                    1.0f,
                    editDateDebut.getText().toString(),
                    editDateFin.getText().toString(),
                    status,
                    editContexte.getText().toString(),
                    editLienWeb.getText().toString()
            );

            Intent resultIntent = new Intent();
            resultIntent.putExtra("NOUVELLE_TACHE", laTache);
            setResult(RESULT_OK, resultIntent);
            finish();
        });
    }
}