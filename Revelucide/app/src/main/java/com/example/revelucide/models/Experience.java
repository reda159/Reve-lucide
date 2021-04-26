package com.example.revelucide.models;

public class Experience {

    private String commentaire;
    private Reve reveAssocier;

    public Experience(String commentaire, Reve reveAssocier) {
        this.commentaire = commentaire;
        this.reveAssocier = reveAssocier;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public Reve getReveAssocier() {
        return reveAssocier;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public void setReveAssocier(Reve reveAssocier) {
        this.reveAssocier = reveAssocier;
    }
}
