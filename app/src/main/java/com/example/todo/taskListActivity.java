package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class taskListActivity extends AppCompatActivity {

    private ArrayList<Tache> listTache;
    private ArrayAdapter<Tache> adapter;
    private static final int REQUEST_CODE_CREATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list); // Assure-toi de créer ce fichier XML

        listTache = new ArrayList<>();
        ListView listView = findViewById(R.id.listViewTaches);
        Button btnAdd = findViewById(R.id.btnAjouterTache);

        // Initialisation de l'adaptateur
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listTache);
        listView.setAdapter(adapter);

        // Clic sur un item : Afficher un Toast (TP 2) puis ouvrir la description (TP 3)
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tache task = listTache.get(position);
            Toast.makeText(taskListActivity.this, "Tâche : " + task.getIntitule(), Toast.LENGTH_SHORT).show(); // [cite: 55]

            Intent intent = new Intent(taskListActivity.this, taskDescActivity.class);
            intent.putExtra("TACHE_SELECTIONNEE", task);
            startActivity(intent); //
        });

        // Bouton pour créer une tâche
        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(taskListActivity.this, taskCreateActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE); //
        });
    }

    // Récupération des données renvoyées par taskCreateActivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CREATE && resultCode == RESULT_OK && data != null) {
            Tache nouvelleTache = (Tache) data.getSerializableExtra("NOUVELLE_TACHE");
            listTache.add(nouvelleTache);
            adapter.notifyDataSetChanged(); // Met à jour la liste
        }
    }
}