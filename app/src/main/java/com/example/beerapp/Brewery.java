package com.example.beerapp;

public class Brewery {
    private String title;
    private String bundesland;
    private String ort;
    private boolean expandable;


    public Brewery(String title, String bundesland, String ort) {
        this.title = title;
        this.bundesland = bundesland;
        this.ort = ort;
        this.expandable = false;
    }

    public boolean isExpandable(){
        return expandable;
    }

    public void setExpandable(boolean expandable){
        this.expandable = expandable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBundesland() {
        return bundesland;
    }

    public void setBundesland(String bundesland) {
        this.bundesland = bundesland;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }
}
