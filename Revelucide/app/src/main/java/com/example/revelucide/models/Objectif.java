package com.example.revelucide.models;

import java.util.ArrayList;

public class Objectif {

    private static ArrayList<Objectif> listObjectif = new ArrayList<>();

    private String title;
    private ArrayList<String> listExperience;
    private ArrayList<String> listAstuce;

    public Objectif(String title) {
        this.title = title;
        this.listExperience = new ArrayList<>();
        this.listAstuce = new ArrayList<>();
    }

    public static ArrayList<Objectif> getListObjectif() {
        return listObjectif;
    }

    public void addObjectif(String title) {
        Objectif.listObjectif.add(new Objectif(title));
    }

    public static void setListObjectif(ArrayList<Objectif> listObjectif) {
        Objectif.listObjectif = listObjectif;
    }

    public int getNbExperience() {
        return listExperience.size();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<String> getListExperience() {
        return listExperience;
    }

    public ArrayList<String> getListAstuce() {
        return listAstuce;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExperience(String experience) {
        this.listExperience.add(experience);
    }

    public void setAstuce(String astuce) {
        this.listAstuce.add(astuce);
    }

    public String toString() {
        return this.title;
    } // pour la recherche on a besoin d'un tooString qui renvoie le titre
}
