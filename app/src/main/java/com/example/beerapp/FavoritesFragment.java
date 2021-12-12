package com.example.beerapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FavoritesFragment extends Fragment {

    private ArrayList <Beer> favoritesList = new ArrayList<>();
    private FavoritesAdapter FavoritesAdapter;
    private RecyclerView favoritesRV;

    private SharedViewModel viewModel;

    private TextView favorites;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

       favorites = view.findViewById(R.id.textViewFavorites);

        viewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        viewModel.getFavoritesL().observe(getViewLifecycleOwner(), new Observer<ArrayList<Beer>>() {
            @Override
            public void onChanged(ArrayList<Beer> beers) {
                favoritesList = beers;
            }
        });
        favoritesList = viewModel.getFavoritesL().getValue();

        favoritesRV = view.findViewById(R.id.favoritesRv);
        if (favoritesList != null) {
            initRecyclerView(favoritesList);
        } else {
            favorites.setText("Keine Favoriten");
        }

        return view;
    }

    private void initRecyclerView(ArrayList<Beer> be)
    {
        favoritesRV.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        FavoritesAdapter = new FavoritesAdapter(be);
        favoritesRV.setAdapter(FavoritesAdapter);
    }
}

