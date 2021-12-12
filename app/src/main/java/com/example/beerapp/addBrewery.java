package com.example.beerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class addBrewery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbrewery);

        Button btn = this.findViewById(R.id.add_brewery_button);
        EditText et1 = this.findViewById(R.id.add_brewery_titel);
        EditText et2 = this.findViewById(R.id.add_brewery_bl);
        EditText et3 = this.findViewById(R.id.add_brewery_ort);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getBaseContext(),"Added: \n"
                                                     + et1.getText().toString() + "\n"
                                                     + et2.getText().toString() + "\n"
                                                     + et3.getText().toString(), Toast.LENGTH_SHORT).show();
                addBrewery(et1.getText().toString(), et2.getText().toString(), et3.getText().toString());
            }
        });


    }

    private void addBrewery(String titel, String bundesland, String ort){
        try {

            Gson gson = new Gson();
            Brewery b = new Brewery(titel, bundesland, ort);

            String json = gson.toJson(b);

            File file = new File(getBaseContext().getFilesDir().getAbsolutePath(),"myBreweries.json");
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            osw.append(json+",\n");

            osw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}



