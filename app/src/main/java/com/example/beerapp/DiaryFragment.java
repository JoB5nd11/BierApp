package com.example.beerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;

public class DiaryFragment extends Fragment {
    private ArrayList<DiaryEntry> entryList = new ArrayList<>();
    private RecyclerView diaryRV;
    private DiaryAdapter diaryAdapter;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);

        diaryRV = view.findViewById(R.id.diaryRv);
        initRecyclerView(entryList);
        fillEntryList();

        FloatingActionButton fab = view.findViewById(R.id.add_diary_entry);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddEntry();
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onResume() {
        entryList.clear();
        fillEntryList();
        initRecyclerView(entryList);
        diaryRV.scrollToPosition(diaryAdapter.getItemCount() - 1);
        super.onResume();
    }

    private void initRecyclerView(ArrayList<DiaryEntry> de){
        diaryRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        diaryAdapter = new DiaryAdapter(de);
        diaryRV.setAdapter(diaryAdapter);
    }

    private void openAddEntry(){
        Intent intent = new Intent(getContext(), addDiaryEntry.class);
        startActivity(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void fillEntryList(){
        JSONObject obj;

        try (BufferedReader br = new BufferedReader(new FileReader(getActivity().getFilesDir().getAbsoluteFile() + "/myDiary.json"))) {
            String line;
            while ((line = br.readLine()) != null) {
                obj = new JSONObject(line);
                DiaryEntry entry = new DiaryEntry(obj.optString("date"),
                                                  obj.optString("title"),
                                                  obj.optString("text"),
                                                  getBitmapFromString(obj.optString("image")));
                entryList.add(entry);
            }
        }catch(IOException | JSONException e){
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private Bitmap getBitmapFromString(String stringImg){
        byte[] decodedString = Base64.getDecoder().decode(stringImg);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}

