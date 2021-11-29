package com.example.beerapp;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class BreweriesFragment extends Fragment {

    private ArrayList<Brewery> breweryList = new ArrayList<>();
    private RecyclerView BreweryRV;
    private BreweryAdapter BreweryAdapter;

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

        return view;
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


}

