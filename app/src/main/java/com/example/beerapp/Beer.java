package com.example.beerapp;

import com.google.gson.annotations.SerializedName;

public class Beer {
    private String bier;
    private String herkunft;
    private String bewertung;
    private String votes;
    private boolean expandable;


    public Beer(String bier, String herkunft, String bewertung, String votes) {
        this.bier = bier;
        this.herkunft = herkunft;
        this.bewertung = bewertung;
        this.votes = votes;
        this.expandable = false;
    }

    public boolean isExpandable(){
        return expandable;
    }

    public void setExpandable(boolean expandable){
        this.expandable = expandable;
    }

    public String getBier() {
        return bier;
    }

    public void setBier(String bier) {
        this.bier = bier;
    }

    public String getHerkunft() {
        return herkunft;
    }

    public void setHerkunft(String herkunft) {
        this.herkunft = herkunft;
    }

    public String getBewertung() {
        return bewertung;
    }

    public void setBewertung(String bewertung) {
        this.bewertung = bewertung;
    }

    public String getVotes() {
        return votes;
    }

    public void setVotes(String votes) {
        this.votes = votes;
    }
}
