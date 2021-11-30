package com.example.beerapp;

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

import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class BeerFragment extends Fragment {

    private ArrayList<Beer> beerList = new ArrayList<>();
    private RecyclerView beerRV;
    private BeerAdapter BeerAdapter;
    private static final Type DATA_TYPE = new TypeToken<ArrayList<Beer>>(){}.getType();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_beers, container, false);

        beerRV = view.findViewById(R.id.beerRv);
        initRecyclerView(beerList);
        fillBeerlist();

        EditText editText = view.findViewById(R.id.beer_search);
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
        ArrayList<Beer> filteredList = new ArrayList<>();

        for(Beer beer : beerList){
            if(beer.getBier().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(beer);
            }
            initRecyclerView(filteredList);
        }
    }

    private void fillBeerlist()
    {

        try {

            JSONObject obj1 = new JSONObject(loadJSONfromAssets("beers.json"));
            JSONArray beerArray = obj1.getJSONArray("beer");

            for (int i = 0; i < beerArray.length(); i++) {

                JSONObject beerDetail = beerArray.getJSONObject(i);
                Beer b = new Beer(beerDetail.getString("bier"),beerDetail.optString("herkunft"),beerDetail.optString("bewertung"), beerDetail.optString("votes"));

                beerList.add(b);
            }

        } catch (JSONException e) {

            e.printStackTrace();
        }

    }


    private void initRecyclerView(ArrayList<Beer> be)
    {
        beerRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        BeerAdapter = new BeerAdapter(be);
        beerRV.setAdapter(BeerAdapter);
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

