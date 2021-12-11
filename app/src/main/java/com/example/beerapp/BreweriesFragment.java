package com.example.beerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class BreweriesFragment extends Fragment{
    private ArrayList<Brewery> breweryList = new ArrayList<>();
    private RecyclerView BreweryRV;
    private BreweryAdapter BreweryAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuInflater inflater3 = getActivity().getMenuInflater();
        inflater3.inflate(R.menu.sort_brewery_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.sort_brewery1:

                Comparator<Brewery> breweryComparatorByName = new Comparator<Brewery>() {
                    @Override
                    public int compare(Brewery b1, Brewery b2) {
                        return b1.getTitle().toLowerCase(Locale.ROOT).compareTo(b2.getTitle().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(breweryList, breweryComparatorByName);
                BreweryAdapter.notifyDataSetChanged();

                return true;
            case R.id.sort_brewery2:

                Comparator<Brewery> breweryComparatorByName1 = new Comparator<Brewery>() {
                    @Override
                    public int compare(Brewery b1, Brewery b2) {
                        return b2.getTitle().toLowerCase(Locale.ROOT).compareTo(b1.getTitle().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(breweryList, breweryComparatorByName1);
                BreweryAdapter.notifyDataSetChanged();


                return true;

            case R.id.sort_brewery3:

                Comparator<Brewery> breweryComparatorByName2 = new Comparator<Brewery>() {
                    @Override
                    public int compare(Brewery b1, Brewery b2) {
                        return b1.getBundesland().toLowerCase(Locale.ROOT).compareTo(b2.getBundesland().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(breweryList, breweryComparatorByName2);
                BreweryAdapter.notifyDataSetChanged();

                return true;

            case R.id.sort_brewery4:


            Comparator<Brewery> breweryComparatorByName3 = new Comparator<Brewery>() {
                @Override
                public int compare(Brewery b2, Brewery b1) {
                    return b1.getBundesland().toLowerCase(Locale.ROOT).compareTo(b2.getBundesland().toLowerCase(Locale.ROOT));
                }
            };
            Collections.sort(breweryList, breweryComparatorByName3);
            BreweryAdapter.notifyDataSetChanged();

            return true;

            default:

                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_breweries, container, false);

        BreweryRV = view.findViewById(R.id.breweryRv);
        initRecyclerView(breweryList);
        fillBreweryist();

        EditText editText = view.findViewById(R.id.brewery_search);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    filter(s.toString());
            }
        });

        Button searchBtn = view.findViewById(R.id.search_button);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        FloatingActionButton fab = view.findViewById(R.id.add_brewery);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Hää
//                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                openAddBrewery();
            }
        });

        return view;
    }

    private void openAddBrewery(){
        Intent intent = new Intent(getContext(), addBrewery.class);
        startActivity(intent);
    }

    private void filter(String text){
        ArrayList<Brewery> filteredList = new ArrayList<>();

        for(Brewery brewery : breweryList){
            if(brewery.getTitle().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(brewery);
            }
            initRecyclerView(filteredList);
        }
    }

    private void fillBreweryist()
    {

        try {
            JSONObject obj = new JSONObject(loadJSONfromAssets("breweries.json"));
            JSONArray breweryArray = obj.getJSONArray("breweries");

            for (int i = 0; i < breweryArray.length(); i++) {
                JSONObject breweryDetail = breweryArray.getJSONObject(i);

                Brewery b = new Brewery(breweryDetail.getString("title"),breweryDetail.optString("bundesland"),breweryDetail.optString("ort"));
                breweryList.add(b);
            }


            JSONObject obj2 = new JSONObject(loadJSONfromFiles("myBreweries.json"));
            JSONArray breweryArray2 = obj2.getJSONArray("mybreweries");

            for (int i = 0; i < breweryArray2.length(); i++) {
                JSONObject breweryDetail2 = breweryArray2.getJSONObject(i);

                Brewery b = new Brewery(breweryDetail2.getString("title"),breweryDetail2.optString("bundesland"),breweryDetail2.optString("ort"));
                breweryList.add(b);

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }
    }


    private void initRecyclerView(ArrayList<Brewery> br)
    {
        BreweryRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        BreweryAdapter = new BreweryAdapter(br);
        BreweryRV.setAdapter(BreweryAdapter);
    }

    private String loadJSONfromAssets(String fileName) {

        String json = null;
        try {
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


    private String loadJSONfromFiles(String fileName) {

        String json = null;
        try {
            File file = new File(getContext().getFilesDir().getAbsolutePath(),"myBreweries.json");
            if(file.exists()) {
                FileInputStream is = new FileInputStream(file);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }else{}

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return "{\n" +
                "  \"mybreweries\": ["+ json + "  ]\n" +
                "}";
    }
}

