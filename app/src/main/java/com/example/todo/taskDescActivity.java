package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class taskDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_desc);

        // 1. On relie nos variables Java aux éléments de l'interface XML
        TextView txtIntitule = findViewById(R.id.txtIntituleDesc);
        TextView txtStatus = findViewById(R.id.txtStatusDesc);
        TextView txtContexte = findViewById(R.id.txtContexteDesc);
        TextView txtDates = findViewById(R.id.txtDatesDesc);
        TextView txtDesc = findViewById(R.id.txtDescriptionDesc);
        EditText editLienWeb = findViewById(R.id.editLienWeb);
        Button btnOuvrirLien = findViewById(R.id.btnOuvrirLien);
        Button btnRetour = findViewById(R.id.btnRetour);

        // 2. Récupération de la tâche envoyée par taskListActivity
        Tache tache = (Tache) getIntent().getSerializableExtra("TACHE_SELECTIONNEE");

        // 3. Si on a bien reçu une tâche, on affiche ses informations
        if (tache != null) {
            txtIntitule.setText(tache.getIntitule());
            txtStatus.setText("Statut : " + tache.getStatusString());
            txtContexte.setText("Contexte : " + tache.getContexte());
            txtDates.setText("Du " + tache.getDateDebut() + " au " + tache.getDateFin());
            txtDesc.setText(tache.getDescription());
            editLienWeb.setText(tache.getLienWeb());
        }

        // 4. Gestion du bouton d'ouverture du lien Web (TP 4)
        btnOuvrirLien.setOnClickListener(v -> {
            // On récupère le texte dans le champ lien web et on enlève les espaces en trop
            String url = editLienWeb.getText().toString().trim();

            // Si le champ est vide, on affiche une erreur avec un Toast
            if (url.isEmpty()) {
                Toast.makeText(taskDescActivity.this, "Erreur : Aucun lien web défini pour cette tâche.", Toast.LENGTH_LONG).show();
            } else {
                // Sinon, on lance la WebActivity en lui passant l'URL
                Intent intent = new Intent(taskDescActivity.this, WebActivity.class);
                intent.putExtra("URL_WEB", url);
                startActivity(intent);
            }
        });

        btnRetour.setOnClickListener(v -> {
            finish(); // Cette méthode ferme l'activité actuelle et revient à la précédente
        });
    }
}