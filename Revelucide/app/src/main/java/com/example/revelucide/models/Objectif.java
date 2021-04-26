package com.example.revelucide.models;

import java.util.ArrayList;

public class Objectif {

    private String title;
    private ArrayList<Experience> listExperience;
    private ArrayList<String> listAstuce;

    public Objectif(String title) {
        this.title = title;
        this.listExperience = new ArrayList<>();
        this.listAstuce = new ArrayList<>();
    }

    public int getNbExperience() {
        return listExperience.size();
    }

    public String getTitle() {
        return title;
    }

    public ArrayList<Experience> getListExperience() {
        return listExperience;
    }

    public ArrayList<String> getListAstuce() {
        return listAstuce;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setExperience(Experience experience) {
        this.listExperience.add(experience);
    }

    public void setAstuce(String astuce) {
        this.listAstuce.add(astuce);
    }
}
