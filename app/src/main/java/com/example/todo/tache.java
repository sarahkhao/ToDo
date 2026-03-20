package com.example.todo;

import java.util.Date;

public class tache {

    private String intitule ;
    private String description;
    private float duree;
    private Date dateDebut;

    private Date dateFin;
    private int status;
    private String contexte;

    public tache (String intitule, String desc, float duree, Date dateDebut, Date dateFin, int status, String contexte){
        this.intitule = intitule;
        this.description = desc;
        this.duree = duree;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.status = status;
        this.contexte = contexte;
    }
}