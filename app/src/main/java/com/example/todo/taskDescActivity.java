package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class taskDescActivity extends AppCompatActivity {

    private Tache tacheActuelle;
    private int indexTache;

    private TextView txtIntitule, txtStatus, txtContexte, txtDates, txtDesc;
    private EditText editLienWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_desc);

        txtIntitule = findViewById(R.id.txtIntituleDesc);
        txtStatus = findViewById(R.id.txtStatusDesc);
        txtContexte = findViewById(R.id.txtContexteDesc);
        txtDates = findViewById(R.id.txtDatesDesc);
        txtDesc = findViewById(R.id.txtDescriptionDesc);
        editLienWeb = findViewById(R.id.editLienWeb);
        Button btnOuvrirLien = findViewById(R.id.btnOuvrirLien);

        // Récupération des nouveaux boutons
        Button btnModifier = findViewById(R.id.btnModifier);
        Button btnSupprimer = findViewById(R.id.btnSupprimer);

        // On récupère la tâche ET son index
        tacheActuelle = (Tache) getIntent().getSerializableExtra("TACHE_SELECTIONNEE");
        indexTache = getIntent().getIntExtra("INDEX_TACHE", -1);

        afficherDetailsTache();

        btnOuvrirLien.setOnClickListener(v -> {
            String url = editLienWeb.getText().toString().trim();
            if (url.isEmpty()) {
                Toast.makeText(taskDescActivity.this, "Erreur : Aucun lien web défini.", Toast.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(taskDescActivity.this, WebActivity.class);
                intent.putExtra("URL_WEB", url);
                startActivity(intent);
            }
        });

        // --- ACTION : SUPPRIMER LA TÂCHE ---
        btnSupprimer.setOnClickListener(v -> {
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ACTION", "SUPPRIMER");
            returnIntent.putExtra("INDEX_TACHE", indexTache); // On renvoie l'index pour supprimer la bonne
            setResult(RESULT_OK, returnIntent);
            finish(); // Ferme la description et retourne à la liste
        });

        // --- ACTION : MODIFIER LA TÂCHE ---
        btnModifier.setOnClickListener(v -> {
            Intent intent = new Intent(taskDescActivity.this, taskCreateActivity.class);
            intent.putExtra("TACHE_A_MODIFIER", tacheActuelle); // On envoie la tâche actuelle au formulaire
            startActivityForResult(intent, 2); // Code 2 = Modification
        });
    }

    // Méthode séparée pour mettre à jour les textes à l'écran
    private void afficherDetailsTache() {
        if (tacheActuelle != null) {
            txtIntitule.setText(tacheActuelle.getIntitule());
            txtStatus.setText("Statut : " + tacheActuelle.getStatusString());
            txtContexte.setText("Contexte : " + tacheActuelle.getContexte());
            txtDates.setText("Du " + tacheActuelle.getDateDebut() + " au " + tacheActuelle.getDateFin());
            txtDesc.setText(tacheActuelle.getDescription());
            editLienWeb.setText(tacheActuelle.getLienWeb());
        }
    }

    // On réceptionne la tâche une fois qu'elle a été modifiée dans le formulaire
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            tacheActuelle = (Tache) data.getSerializableExtra("NOUVELLE_TACHE");
            afficherDetailsTache(); // Met à jour l'écran de description

            // On prévient taskListActivity qu'il y a eu une modification pour qu'elle sauvegarde
            Intent returnIntent = new Intent();
            returnIntent.putExtra("ACTION", "MODIFIER");
            returnIntent.putExtra("INDEX_TACHE", indexTache);
            returnIntent.putExtra("TACHE_MODIFIEE", tacheActuelle);
            setResult(RESULT_OK, returnIntent);
        }
    }
}