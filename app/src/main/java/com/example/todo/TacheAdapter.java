package com.example.todo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class TacheAdapter extends ArrayAdapter<Tache> {

    private Context mContext;
    private List<Tache> mListTaches;

    public TacheAdapter(Context context, List<Tache> taches) {
        super(context, 0, taches);
        this.mContext = context;
        this.mListTaches = taches;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1. Récupérer la tâche actuelle
        Tache tache = getItem(position);

        // 2. Gonfler le layout de la ligne (item)
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.activity_item, parent, false);
        }

        // 3. Récupérer les vues du fichier XML
        View indicator = convertView.findViewById(R.id.indicatorStatus);
        TextView txtTitre = convertView.findViewById(R.id.txtTitreItem);
        TextView txtContexte = convertView.findViewById(R.id.txtContexteItem);

        // 4. Remplir les données de base
        if (tache != null) {
            txtTitre.setText(tache.getIntitule());
            txtContexte.setText(tache.getContexte());

            // 5. GESTION DYNAMIQUE DES COULEURS (Modification visuelle)
            // On récupère la couleur personnalisée choisie dans les réglages
            SharedPreferences prefs = mContext.getSharedPreferences("MesPrefs", Context.MODE_PRIVATE);

            int couleur;
            switch (tache.getStatus()) {
                case 0: // Pas commencée
                    String col0 = prefs.getString("COULEUR_STATUS_0", "#F44336"); // Rouge par défaut
                    couleur = Color.parseColor(col0);
                    break;
                case 1: // En cours
                    String col1 = prefs.getString("COULEUR_STATUS_1", "#2196F3"); // Bleu par défaut
                    couleur = Color.parseColor(col1);
                    break;
                case 2: // Terminée
                    String col2 = prefs.getString("COULEUR_STATUS_2", "#4CAF50"); // Vert par défaut
                    couleur = Color.parseColor(col2);
                    break;
                default:
                    couleur = Color.GRAY;
                    break;
            }
            indicator.setBackgroundColor(couleur);
        }

        return convertView;
    }

    // Méthode utilitaire pour rafraîchir la liste après une modification dans l'Activity
    public void updateData(List<Tache> nouvellesTaches) {
        this.mListTaches.clear();
        this.mListTaches.addAll(nouvellesTaches);
        notifyDataSetChanged();
    }
}