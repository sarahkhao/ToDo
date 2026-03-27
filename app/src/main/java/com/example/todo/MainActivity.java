package com.example.todo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Redirige directement vers la liste des tâches
        Intent intent = new Intent(MainActivity.this, taskListActivity.class);
        startActivity(intent);

        // Ferme MainActivity pour qu'on ne puisse pas y retourner en faisant "Retour"
        finish();
    }
}