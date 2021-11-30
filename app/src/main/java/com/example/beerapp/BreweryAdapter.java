package com.example.beerapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BreweryAdapter extends RecyclerView.Adapter<BreweryAdapter.TodoViewHolder> {

    List<Brewery> breweryList;

    public BreweryAdapter(List<Brewery> breweryList) {
        this.breweryList = breweryList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.brewery_cardview, parent, false);
        TodoViewHolder todoViewHoldervh = new TodoViewHolder(v);
        return todoViewHoldervh;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Brewery brewery = breweryList.get(position);
        holder.brew_title.setText(brewery.title);
        holder.brew_bundesland.setText(brewery.bundesland);
        holder.brew_ort.setText(brewery.ort);

    }

    @Override
    public int getItemCount() {
        return breweryList.size();
    }

    public void addTodo(Brewery todo) {
        breweryList.add(todo);
        notifyItemInserted(breweryList.size()-1);
    }

    public void removeItem(RecyclerView.ViewHolder viewHolder)
    {
        int removePosition = viewHolder.getAdapterPosition();
        breweryList.remove(removePosition);
        notifyItemRemoved(removePosition);
    }

    public static class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView brew_title, brew_bundesland, brew_ort;


        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            brew_title = itemView.findViewById(R.id.brewery_name);
            brew_bundesland = itemView.findViewById(R.id.brewery_bundesland);
            brew_ort = itemView.findViewById(R.id.brewery_ort);
        }
    }
}
