package com.example.todo;

import java.util.ArrayList;

public class taskListActivity {

    private ArrayList<Tache> listTache ;

    public taskListActivity (ArrayList<Tache> lst ){
        listTache = lst;
    }

    public ArrayList<Tache> getListTache() {

        ArrayList<Tache> getlist = new ArrayList<>(listTache);
        return getlist;
    }

    public void setListTache (ArrayList<Tache> lst){
        listTache = lst;
    }

    public void addTache (Tache task){
        listTache.add(task);
    }
}
