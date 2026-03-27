package com.example.todo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

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
        Button btnAnnuler = findViewById(R.id.btnAnnuler);

        // Configuration du Spinner des statuts
        String[] statuts = {"Pas commencée", "En cours", "Terminée"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, statuts);
        spinnerStatus.setAdapter(adapter);

        // --- GESTION DES CLICS SUR LES DATES ---
        editDateDebut.setOnClickListener(v -> afficherCalendrier(editDateDebut));
        editDateFin.setOnClickListener(v -> afficherCalendrier(editDateFin));

        Tache tacheAModifier = (Tache) getIntent().getSerializableExtra("TACHE_A_MODIFIER");
        if (tacheAModifier != null) {
            editIntitule.setText(tacheAModifier.getIntitule());
            editDesc.setText(tacheAModifier.getDescription());
            editContexte.setText(tacheAModifier.getContexte());
            editDateDebut.setText(tacheAModifier.getDateDebut());
            editDateFin.setText(tacheAModifier.getDateFin());
            editLienWeb.setText(tacheAModifier.getLienWeb());
            spinnerStatus.setSelection(tacheAModifier.getStatus());

            btnSauvegarder.setText("Mettre à jour");
        }
        btnSauvegarder.setOnClickListener(v -> {
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

        btnAnnuler.setOnClickListener(v -> {
            finish(); // Cette méthode ferme l'activité actuelle et revient à la précédente
        });
    }

    // --- NOUVELLE METHODE POUR LE CALENDRIER ---
    private void afficherCalendrier(final EditText editText) {
        // On récupère la date d'aujourd'hui pour ouvrir le calendrier sur le bon jour
        final Calendar calendrier = Calendar.getInstance();
        int annee = calendrier.get(Calendar.YEAR);
        int mois = calendrier.get(Calendar.MONTH);
        int jour = calendrier.get(Calendar.DAY_OF_MONTH);

        // Création de la boîte de dialogue du calendrier
        DatePickerDialog datePickerDialog = new DatePickerDialog(taskCreateActivity.this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    // Action effectuée quand l'utilisateur valide une date
                    // On formate la date proprement : JJ/MM/AAAA
                    String dateSelectionnee = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (monthOfYear + 1), year);
                    editText.setText(dateSelectionnee); // On met le texte dans la case
                },
                annee, mois, jour); // On donne les valeurs initiales

        datePickerDialog.show(); // On affiche le calendrier
    }
}