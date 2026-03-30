package com.example.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList; // Important pour changer le fond des boutons
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class ColorSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_settings);

        // 1. Appliquer les couleurs enregistrées aux boutons dès l'ouverture
        rafraichirCouleursBoutons();

        // 2. Configurer les clics
        findViewById(R.id.btnColorRed).setOnClickListener(v -> ouvrirSpectre("COULEUR_STATUS_0", "A faire"));
        findViewById(R.id.btnColorBlue).setOnClickListener(v -> ouvrirSpectre("COULEUR_STATUS_1", "Commencer"));
        findViewById(R.id.btnColorGreen).setOnClickListener(v -> ouvrirSpectre("COULEUR_STATUS_2", "Terminé"));
    }

    private void rafraichirCouleursBoutons() {
        SharedPreferences prefs = getSharedPreferences("MesPrefs", MODE_PRIVATE);

        // On récupère les couleurs (ou défaut)
        int col0 = Color.parseColor(prefs.getString("COULEUR_STATUS_0", "#F44336"));
        int col1 = Color.parseColor(prefs.getString("COULEUR_STATUS_1", "#2196F3"));
        int col2 = Color.parseColor(prefs.getString("COULEUR_STATUS_2", "#4CAF50"));

        // On applique aux boutons avec setBackgroundTintList
        ((Button)findViewById(R.id.btnColorRed)).setBackgroundTintList(ColorStateList.valueOf(col0));
        ((Button)findViewById(R.id.btnColorBlue)).setBackgroundTintList(ColorStateList.valueOf(col1));
        ((Button)findViewById(R.id.btnColorGreen)).setBackgroundTintList(ColorStateList.valueOf(col2));
    }

    private void ouvrirSpectre(String key, String titre) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View layout = getLayoutInflater().inflate(R.layout.diagonal_color_picker, null);
        builder.setView(layout);
        builder.setTitle(titre);

        ImageView spectrum = layout.findViewById(R.id.colorSpectrum);
        View preview = layout.findViewById(R.id.colorPreview);

        final int[] colorSelected = {Color.BLACK};

        spectrum.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_MOVE || event.getAction() == MotionEvent.ACTION_DOWN) {
                spectrum.setDrawingCacheEnabled(true);
                Bitmap bitmap = spectrum.getDrawingCache();
                if (bitmap != null && event.getX() >= 0 && event.getX() < bitmap.getWidth() &&
                        event.getY() >= 0 && event.getY() < bitmap.getHeight()) {
                    int pixel = bitmap.getPixel((int) event.getX(), (int) event.getY());
                    if (Color.alpha(pixel) > 0) {
                        colorSelected[0] = pixel;
                        preview.setBackgroundColor(pixel);
                    }
                }
            }
            return true;
        });

        builder.setPositiveButton("Valider", (dialog, which) -> {
            String hexColor = String.format("#%06X", (0xFFFFFF & colorSelected[0]));
            sauvegarderCouleur(key, hexColor);
        });

        builder.show();
    }

    private void sauvegarderCouleur(String key, String codeHex) {
        SharedPreferences prefs = getSharedPreferences("MesPrefs", MODE_PRIVATE);
        prefs.edit().putString(key, codeHex).apply();

        // IMPORTANT : On rafraîchit les boutons sur cette page immédiatement
        rafraichirCouleursBoutons();

        Toast.makeText(this, "Couleur mise à jour", Toast.LENGTH_SHORT).show();
    }
}