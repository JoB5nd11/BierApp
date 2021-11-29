package com.example.beerapp;

import com.google.gson.annotations.SerializedName;

public class Beer {
    String bier;
    String herkunft;
    String bewertung;
    String votes;

    public Beer(String bier, String herkunft, String bewertung, String votes) {
        this.bier = bier;
        this.herkunft = herkunft;
        this.bewertung = bewertung;
        this.votes = votes;
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
