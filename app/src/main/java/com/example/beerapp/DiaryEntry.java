package com.example.beerapp;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Date;

public class DiaryEntry {
    private String date;
    private String title;
    private String text;
    private Bitmap image;

    public DiaryEntry(String date, String title, String text, Bitmap image){
        this.date = date;
        this.title = title;
        this.text = text;
        this.image = image;
    }

    public String getDate(){
        return this.date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getTitle(){
        return this.title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getText(){
        return this.text;
    }

    public void setText(){
        this.text = text;
    }

    public Bitmap getImage(){
        return this.image;
    }

    public void setImage(){
        this.image = image;
    }
}
