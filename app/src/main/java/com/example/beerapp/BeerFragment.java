package com.example.beerapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class BeerFragment extends Fragment {

    private ArrayList<Beer> beerList = new ArrayList<>();
    private RecyclerView beerRV;
    private BeerAdapter BeerAdapter;
    private static final Type DATA_TYPE = new TypeToken<ArrayList<Beer>>(){}.getType();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        MenuInflater inflater3 = getActivity().getMenuInflater();
        inflater3.inflate(R.menu.sort_beer_menu, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.sort_beer1:

                Comparator<Beer> beerComparatorByName = new Comparator<Beer>() {
                    @Override
                    public int compare(Beer b1, Beer b2) {
                        return b1.getBier().toLowerCase(Locale.ROOT).compareTo(b2.getBier().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(beerList, beerComparatorByName);
                BeerAdapter.notifyDataSetChanged();

                return true;
            case R.id.sort_beer2:

                Comparator<Beer> beerComparatorByName1 = new Comparator<Beer>() {
                    @Override
                    public int compare(Beer b1, Beer b2) {
                        return b2.getBier().toLowerCase(Locale.ROOT).compareTo(b1.getBier().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(beerList, beerComparatorByName1);
                BeerAdapter.notifyDataSetChanged();


                return true;
            case R.id.sort_beer3:

                Comparator<Beer> beerComparatorByName3 = new Comparator<Beer>() {
                    @Override
                    public int compare(Beer b1, Beer b2) {
                        return b1.getBewertung().toLowerCase(Locale.ROOT).compareTo(b2.getBewertung().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(beerList, beerComparatorByName3);
                BeerAdapter.notifyDataSetChanged();


                return true;
            case R.id.sort_beer4:

                Comparator<Beer> beerComparatorByName4 = new Comparator<Beer>() {
                    @Override
                    public int compare(Beer b1, Beer b2) {
                        return b2.getBewertung().toLowerCase(Locale.ROOT).compareTo(b1.getBewertung().toLowerCase(Locale.ROOT));
                    }
                };
                Collections.sort(beerList, beerComparatorByName4);
                BeerAdapter.notifyDataSetChanged();


                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

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

        FloatingActionButton fab = view.findViewById(R.id.add_beer);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
//                Toast.makeText(getContext(),"Test",Toast.LENGTH_SHORT).show();
                openAddBeer();
            }
        });




        return view;
    }

    private void openAddBeer(){
        Intent intent = new Intent(getContext(),addBeer2.class);
        startActivity(intent);
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

            JSONObject obj2 = new JSONObject(loadJSONfromFiles("myBeers.json.json"));
            JSONArray beerArray2 = obj2.getJSONArray("mybeers");


            for (int i = 0; i < beerArray2.length(); i++) {
                JSONObject beerDetail2= beerArray2.getJSONObject(i);

                Beer b = new Beer(beerDetail2.getString("bier"),beerDetail2.optString("herkunft"),beerDetail2.optString("bewertung"),beerDetail2.optString("votes") );
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

    private String loadJSONfromFiles(String fileName) {

        String json = null;
        try {
            File file = new File(getContext().getFilesDir().getAbsolutePath(),"myBeers.json");
            if(file.exists()){
                FileInputStream is = new FileInputStream(file);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                json = new String(buffer, "UTF-8");
            }else{

            }

        } catch (IOException ex ) {
            ex.printStackTrace();
            return null;
        }
        return "{\n" +
                "  \"mybeers\": ["+ json + "  ]\n" +
                "}";
    }


}

