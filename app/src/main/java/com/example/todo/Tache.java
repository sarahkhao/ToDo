package com.example.todo;

import java.io.Serializable;
import java.util.Date;

public class Tache implements Serializable {
    private String intitule;
    private String description;
    private float duree;
    private String dateDebut; // Changé en String pour simplifier la saisie dans ce TP
    private String dateFin;   // Changé en String
    private int status;       // 0 = Pas commencée, 1 = En cours, 2 = Terminée
    private String contexte;
    private String lienWeb;

    public Tache(String intitule, String desc, float duree, String dateDebut, String dateFin, int status, String contexte, String lienWeb) {
        this.intitule = intitule;
        this.description = desc;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.contexte = contexte;
        this.lienWeb = lienWeb;
    }

    public String getIntitule() { return intitule; }
    public String getDescription() { return description; }
    public String getDateDebut() { return dateDebut; }
    public String getDateFin() { return dateFin; }
    public int getStatus() { return status; }
    public String getContexte() { return contexte; }
    public String getLienWeb() { return lienWeb; }

    public void setIntitule(String intitule) { this.intitule = intitule; }
    public void setDescription(String description) { this.description = description; }
    public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
    public void setDateFin(String dateFin) { this.dateFin = dateFin; }
    public void setStatus(int status) { this.status = status; }
    public void setContexte(String contexte) { this.contexte = contexte; }
    public void setLienWeb(String lienWeb){ this.lienWeb = lienWeb; }

    // Méthode pratique pour l'affichage
    public String getStatusString() {
        switch (status) {
            case 1: return "En cours";
            case 2: return "Terminée";
            default: return "Pas commencée";
        }
    }

    @Override
    public String toString() {
        return intitule + " [" + getStatusString() + "] - " + contexte;
    }
}