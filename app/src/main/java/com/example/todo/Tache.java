package com.example.todo;

import java.util.Date;

public class Tache {

    private String intitule ;
    private String description;
    private float duree;
    private Date dateDebut;

    private Date dateFin;
    private int status;
    private String contexte;

    public Tache(String intitule, String desc, float duree, Date dateDebut, Date dateFin, int status, String contexte){
        this.intitule = intitule;
        this.description = desc;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.contexte = contexte;
    }

    public void updateTache (String intitule, String desc, float duree, Date dateDebut, Date dateFin, int status, String contexte){
        this.intitule = intitule;
        this.description = desc;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.contexte = contexte;
    }

    public String getIntitule(){
        return this.intitule;
    }

    public String getDescription(){
        return this.description;
    }

    public float getDuree(){
        return this.duree;
    }

    public Date getDateDebut(){
        return this.dateDebut;
    }

    public Date getDateFin(){
        return this.dateFin;
    }

    public int getStatus(){
        return this.status;
    }

    public String getContexte(){
        return this.contexte;
    }

    public void setIntitule(String intitule){
        this.intitule = intitule;
    }

    public void setDescription(String desc){
        this.description = desc;
    }

    public void setDuree(float duree){
        this.duree = duree;
    }

    public void setDateDebut(Date date){
        this.dateDebut = date;
    }

    public void setDateFin(Date date){
        this.dateFin = date;
    }

    public void setStatus(int stat){
        this.status = stat;
    }

    public void setContexte(String contexte){
        this.contexte = contexte;
    }
}