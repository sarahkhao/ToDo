package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class taskDescActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_desc);

        TextView txtIntitule = findViewById(R.id.txtIntituleDesc);
        TextView txtDesc = findViewById(R.id.txtDescriptionDesc);
        EditText editLienWeb = findViewById(R.id.editLienWeb); // Champ ajouté pour le TP 4 [cite: 140, 141]
        Button btnOuvrirLien = findViewById(R.id.btnOuvrirLien);

        // Récupération de la tâche passée en paramètre
        Tache tache = (Tache) getIntent().getSerializableExtra("TACHE_SELECTIONNEE");

        if (tache != null) {
            txtIntitule.setText(tache.getIntitule());
            txtDesc.setText(tache.getDescription());
            editLienWeb.setText(tache.getLienWeb());
        }

        // Appeler la nouvelle activité Web lors d'un clic sur le lien [cite: 142]
        btnOuvrirLien.setOnClickListener(v -> {
            String url = editLienWeb.getText().toString();
            Intent intent = new Intent(taskDescActivity.this, WebActivity.class);
            intent.putExtra("URL_WEB", url); // Passer la valeur en extra [cite: 143]
            startActivity(intent);
        });
    }
}