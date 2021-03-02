package com.example.revelucide.models;

import java.util.ArrayList;
import java.util.Date;

public class Reve {

    private static ArrayList<Reve> reveLog = new ArrayList<>();

    private String titre;
    private String description;
    private Date date;
    private String clareteReve;
    private Boolean lucide;

    public Reve(String titre, String description, Date date, String clareteReve, Boolean lucide) {
        this.titre = titre;
        this.description = description;
        this.date = date;
        this.clareteReve = clareteReve;
        this.lucide = lucide;
    }

    public static ArrayList<Reve> getReveLog() {
        return reveLog;
    }

    public static void addReveLog(Reve reve) {
        Reve.reveLog.add(reve);
    }

    public static void setReveLog(ArrayList<Reve> reve) {
        Reve.reveLog = reve;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getClareteReve() {
        return clareteReve;
    }

    public void setClareteReve(String clareteReve) {
        this.clareteReve = clareteReve;
    }

    public Boolean getLucide() {
        return lucide;
    }

    public void setLucide(Boolean lucide) {
        this.lucide = lucide;
    }

    public String toString() {
        return this.titre;
    } // pour la recherche on a besoin d'un tooString qui renvoie le titre
}
