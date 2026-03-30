package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class taskListActivity extends AppCompatActivity {

    private ArrayList<Tache> listTacheMaster; // La vraie liste complète (Base de données)
    private ArrayList<Tache> listTacheAffichee; // La liste filtrée affichée à l'écran
    private TacheAdapter adapter;
    private static final int REQUEST_CODE_CREATE = 1;
    private final String FICHIER_SAUVEGARDE = "mes_taches.dat";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);

        // 1. Charger les données enregistrées
        chargerDonnees();

        listTacheAffichee = new ArrayList<>(listTacheMaster);
        ListView listView = findViewById(R.id.listViewTaches);
        Button btnAdd = findViewById(R.id.btnAjouterTache);
        Spinner spinnerFiltre = findViewById(R.id.spinnerFiltre);
        ImageButton btnSettings = findViewById(R.id.btnSettings);

        // 2. Initialisation de la liste
       // adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listTacheAffichee);
        //listView.setAdapter(adapter);
        adapter = new TacheAdapter(this, listTacheAffichee);
        listView.setAdapter(adapter);

        // 3. Gestion du filtre
        String[] filtres = {"Toutes", "Pas commencées", "En cours", "Terminées"};
        spinnerFiltre.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, filtres));

        spinnerFiltre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                appliquerFiltre(position - 1); // -1 = Toutes, 0 = Pas commencée, 1 = En cours, 2 = Terminée
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Tache task = listTacheAffichee.get(position);
            // Très important : On cherche l'index de cette tâche dans la base de données complète
            int indexInMaster = listTacheMaster.indexOf(task);

            Intent intent = new Intent(taskListActivity.this, taskDescActivity.class);
            intent.putExtra("TACHE_SELECTIONNEE", task);
            intent.putExtra("INDEX_TACHE", indexInMaster); // On passe l'index
            startActivityForResult(intent, 2); // 2 = Code pour ouvrir une description
        });

        btnAdd.setOnClickListener(v -> {
            Intent intent = new Intent(taskListActivity.this, taskCreateActivity.class);
            startActivityForResult(intent, REQUEST_CODE_CREATE);
        });

        btnSettings.setOnClickListener(v -> {
            // On lance l'activité de réglages que nous avons créée tout à l'heure
            Intent intent = new Intent(taskListActivity.this, ColorSettingsActivity.class);
            startActivity(intent);
        });
    }

    private void appliquerFiltre(int statusFiltre) {
        listTacheAffichee.clear();
        if (statusFiltre == -1) {
            listTacheAffichee.addAll(listTacheMaster); // On affiche tout
        } else {
            for (Tache t : listTacheMaster) {
                if (t.getStatus() == statusFiltre) {
                    listTacheAffichee.add(t);
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Spinner spinnerFiltre = findViewById(R.id.spinnerFiltre); // On récupère le filtre actuel

        // 1. Retour de la CRÉATION d'une nouvelle tâche
        if (requestCode == REQUEST_CODE_CREATE && resultCode == RESULT_OK && data != null) {
            Tache nouvelleTache = (Tache) data.getSerializableExtra("NOUVELLE_TACHE");
            listTacheMaster.add(nouvelleTache);
            sauvegarderDonnees();

            spinnerFiltre.setSelection(0);
            appliquerFiltre(-1);
            Toast.makeText(this, "Tâche créée avec succès", Toast.LENGTH_SHORT).show();
        }

        // 2. Retour de la DESCRIPTION (Modification ou Suppression)
        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            String action = data.getStringExtra("ACTION");
            int index = data.getIntExtra("INDEX_TACHE", -1);

            if (index != -1 && action != null) {
                if (action.equals("SUPPRIMER")) {
                    listTacheMaster.remove(index); // On retire la tâche de la liste
                    Toast.makeText(this, "Tâche supprimée", Toast.LENGTH_SHORT).show();
                } else if (action.equals("MODIFIER")) {
                    Tache tacheModifiee = (Tache) data.getSerializableExtra("TACHE_MODIFIEE");
                    listTacheMaster.set(index, tacheModifiee); // On remplace l'ancienne par la nouvelle
                    Toast.makeText(this, "Tâche mise à jour", Toast.LENGTH_SHORT).show();
                }
                sauvegarderDonnees(); // On sauvegarde dans le fichier .dat

                // On réapplique le filtre sélectionné pour que la liste visuelle se mette à jour
                appliquerFiltre(spinnerFiltre.getSelectedItemPosition() - 1);
            }
        }
    }

    // --- METHODES DE SAUVEGARDE EN FICHIER (BONUS) ---
    private void sauvegarderDonnees() {
        try {
            FileOutputStream fos = openFileOutput(FICHIER_SAUVEGARDE, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listTacheMaster);
            oos.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur lors de la sauvegarde", Toast.LENGTH_SHORT).show();
        }
    }

    private void chargerDonnees() {
        try {
            FileInputStream fis = openFileInput(FICHIER_SAUVEGARDE);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listTacheMaster = (ArrayList<Tache>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            // Si le fichier n'existe pas encore (premier lancement)
            listTacheMaster = new ArrayList<>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Cette ligne dit à l'adapter : "Les données ou les préférences ont changé, redessine tout !"
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }
}