package com.example.beerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class addBeer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbeer);

        Button btn = this.findViewById(R.id.add_beer_button);
        EditText et1 = this.findViewById(R.id.add_beer_titel);
        EditText et2 = this.findViewById(R.id.add_beer_herkunft);
        EditText et3 = this.findViewById(R.id.add_beer_rating);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Added: "+et1.getText().toString() +""+ et2.getText().toString() +""+ et3.getText().toString(),Toast.LENGTH_SHORT).show();
                addBeer(et1.getText().toString(), et2.getText().toString(), et3.getText().toString());
            }
        });


    }

    private void addBeer(String titel, String herkunft, String bewertung){
        try {

            Gson gson = new Gson();
            Beer b = new Beer(titel, herkunft, bewertung, "1");

            String json = gson.toJson(b);

            File file = new File(getBaseContext().getFilesDir().getAbsolutePath(),"myBeers.json");
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            osw.append(json+",\n");

            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



