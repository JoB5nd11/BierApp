package com.example.beerapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Brewery {
    private String title;
    private String bundesland;
    private String ort;
    private boolean expandable;
    private Bitmap mapImage = null;


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

    public Bitmap getMapImage(){
        return mapImage;
    }

    public void setMapImage(Bitmap mapImage){
        this.mapImage = mapImage;
    }
}
