package com.example.beerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

public class addDiaryEntry extends AppCompatActivity {
    private static final int PICK_IMAGE = 1;
    private Bitmap diaryImage = null;
    private Uri imageUri;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_add_diary_entry);

        EditText editTitle = this.findViewById(R.id.add_diary_title);
        EditText editText = this.findViewById(R.id.add_diary_text);
        Button btn = this.findViewById(R.id.add_entry_button);
        this.fab = this.findViewById((R.id.add_diary_photo_btn));

        btn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                addNewDiaryEntry(editTitle.getText().toString(), editText.getText().toString());
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setType("image/*");
                gallery.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(gallery, "Select Picture"), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK){
            imageUri = data.getData();
            try{
                diaryImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void addNewDiaryEntry(String title, String text){
        try{
            Gson gson = new Gson();
            DiaryEntry diaryEntry = new DiaryEntry(getCurrentDate(), title, text, null);
            String json = gson.toJson(diaryEntry);
            json = removeLastCharacter(json);
            json += ",\"image\":\"" + getStringFromBitmap(this.diaryImage) + "\"}";

            File file = new File(getBaseContext().getFilesDir().getAbsoluteFile(), "myDiary.json");
            //nur zum Aufräumen des Diary weil man halt nix löschen kann xD
//            FileOutputStream fos = new FileOutputStream(file, false);
            FileOutputStream fos = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(fos);

            osw.append(json + "\n");
            osw.close();

            System.out.println("New JSON: \n" + json);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    private String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getStringFromBitmap(Bitmap bitmapPicture){
        final int COMPRESSION_QUALITY = 100;
        String encodedImage;
        ByteArrayOutputStream byteArrayBitmapStream = new ByteArrayOutputStream();
        bitmapPicture.compress(Bitmap.CompressFormat.PNG, COMPRESSION_QUALITY, byteArrayBitmapStream);
        byte[] b = byteArrayBitmapStream.toByteArray();
        encodedImage = Base64.getEncoder().encodeToString(b);
        return encodedImage;
    }

    private String removeLastCharacter(String text){
        StringBuffer sb = new StringBuffer(text);
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    //for debug purposes
    private void testPrintJsonFile(){
        try (BufferedReader br = new BufferedReader(new FileReader(getBaseContext().getFilesDir().getAbsoluteFile() + "/myDiary.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
