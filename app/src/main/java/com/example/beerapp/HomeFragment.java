package com.example.beerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class HomeFragment extends Fragment {
    private ArrayList<Beer> beerList = new ArrayList<>();

    private TextView beerOfDayBier;
    private TextView beerOfDayHerkunft;
    private RatingBar beerOfDayBewertung;

    private SharedViewModel viewModel;

    private ArrayList <String> quoteList = new ArrayList<>();
    private TextView quoteOfDay;

    private CardView randomBeerCV;
    private TextView randomBeerBier;
    private TextView randomBeerHerkunft;
    private RatingBar randomBeerBewertung;
    private Button randomBeerButton;

    int ratingMax = 5;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        fillQuoteList();
        fillBeerlist();

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getBeerL().observe(getViewLifecycleOwner(), new Observer<ArrayList<Beer>>() {
            @Override
            public void onChanged(ArrayList<Beer> beers) {
                beerList = beers;
            }
        });
        viewModel.setBeerL(beerList);


        randomBeerButton = v.findViewById(R.id.randomBeerButton);
        randomBeerBier = v.findViewById(R.id.randomBeerBier);
        randomBeerHerkunft = v.findViewById(R.id.randomBeerHerkunft);
        randomBeerBewertung = v.findViewById(R.id.randomBeerRatingBar);
        randomBeerCV = v.findViewById(R.id.randomBeerCardview);

        randomBeerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                randomBeerCV.setVisibility(View.VISIBLE);
                Random r = new Random();
                int randomBeerNumber = r.nextInt(beerList.size());
                randomBeerBier.setText(beerList.get(randomBeerNumber).getBier());
                randomBeerHerkunft.setText(beerList.get(randomBeerNumber).getHerkunft());
                randomBeerBewertung.setRating(PercentStringToFloat(beerList.get(randomBeerNumber).getBewertung(), ratingMax));

            }
        });

        Random random = new Random();

        int randomNumberQuote = random.nextInt(quoteList.size());
        quoteOfDay = v.findViewById(R.id.quoteOfDay);
        quoteOfDay.setText(quoteList.get(randomNumberQuote));

        int randomNumberBeer = random.nextInt(beerList.size());
        beerOfDayBier = v.findViewById(R.id.beerOfDayBier);
        beerOfDayHerkunft = v.findViewById(R.id.beerOfDayHerkunft);
        beerOfDayBewertung = v.findViewById(R.id.beerOfDayRatingBar);
        beerOfDayBier.setText(beerList.get(randomNumberBeer).getBier());
        beerOfDayHerkunft.setText(beerList.get(randomNumberBeer).getHerkunft());
        beerOfDayBewertung.setRating(PercentStringToFloat(beerList.get(randomNumberBeer).getBewertung(), ratingMax));

        return v;
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
    private void fillQuoteList() {

        try {
            JSONObject obj1 = new JSONObject(loadJSONfromAssets("quotes.json"));
            JSONArray quoteArray = obj1.getJSONArray("quote");

            for (int i = 0; i < quoteArray.length(); i++) {

                JSONObject quoteDetail = quoteArray.getJSONObject(i);


                quoteList.add(quoteDetail.getString("spruch"));
            }



        } catch (JSONException e) {

            e.printStackTrace();
        }

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

    private float PercentStringToFloat(String percentString, int max){
        if(percentString.length() < 1){
            return 0.0f;
        }

        float result = 0;
        StringBuffer sb = new StringBuffer(percentString);
        //Remove the percent sign
        sb.deleteCharAt(sb.length() - 1);

        result = Float.parseFloat(sb.toString());
        result /= 100;
        result *= max;

        return result;
    }

}

