package com.example.todo;

import java.io.Serializable;
import java.util.Date;

public class Tache implements Serializable {
    private String intitule;
    private String description;
    private float duree;
    private Date dateDebut;
    private Date dateFin;
    private int status;
    private String contexte;
    private String lienWeb;

    public Tache(String intitule, String desc, float duree, Date dateDebut, Date dateFin, int status, String contexte, String lienWeb) {
        this.intitule = intitule;
        this.description = desc;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.contexte = contexte;
        this.lienWeb = lienWeb;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getDuree() {
        return duree;
    }

    public void setDuree(float duree) {
        this.duree = duree;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getContexte() {
        return contexte;
    }

    public void setContexte(String contexte) {
        this.contexte = contexte;
    }

    public String getLienWeb() {
        return lienWeb;
    }

    public void setLienWeb(String lienWeb) {
        this.lienWeb = lienWeb;
    }

    @Override
    public String toString() {
        return intitule + " - " + contexte;
    }
}
