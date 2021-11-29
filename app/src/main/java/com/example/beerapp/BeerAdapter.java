package com.example.beerapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.TodoViewHolder> {

    List<Beer> beerList;

    public BeerAdapter(List<Beer> beerList) {
        this.beerList = beerList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.beer_cardview, parent, false);
        TodoViewHolder todoViewHoldervh = new TodoViewHolder(v);
        return todoViewHoldervh;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Beer beer = beerList.get(position);
        holder.brew_title.setText(beer.bier);
        holder.brew_bundesland.setText(beer.herkunft);
        holder.brew_ort.setText(beer.bewertung);

    }

    @Override
    public int getItemCount() {
        return beerList.size();
    }

    public void addTodo(Beer todo) {
        beerList.add(todo);
        notifyItemInserted(beerList.size()-1);
    }

    public void removeItem(RecyclerView.ViewHolder viewHolder)
    {
        int removePosition = viewHolder.getAdapterPosition();
        beerList.remove(removePosition);
        notifyItemRemoved(removePosition);
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView brew_title, brew_bundesland, brew_ort;


        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            brew_title = itemView.findViewById(R.id.beer_bier);
            brew_bundesland = itemView.findViewById(R.id.beer_herkunft);
            brew_ort = itemView.findViewById(R.id.beer_bewertung);
        }
    }
}
