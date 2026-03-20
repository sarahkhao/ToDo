package com.example.todo;

import java.util.Date;

public class taskCreateActivity {

    private Tache laTache ;

    public taskCreateActivity(){

    }

    public Tache createTask (String intitule, String desc, float duree, Date dateDebut, Date dateFin, int status, String contexte){
        laTache = new Tache(intitule, desc, duree, dateDebut, dateFin, status, contexte);

        return laTache;
    }
}
