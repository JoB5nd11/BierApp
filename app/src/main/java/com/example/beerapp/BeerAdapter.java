package com.example.beerapp;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.TodoViewHolder> {

    List<Beer> beerList;
    int ratingMin = 1, ratingMax = 5;

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
        holder.beer_name.setText(beer.bier);
        holder.beer_origin.setText(beer.herkunft);
//        holder.beer_rating.setText(beer.bewertung);
        holder.ratingBar.setStepSize(0.1f);
        holder.ratingBar.setRating(PercentStringToFloat(beer.bewertung, ratingMin, ratingMax));
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
        TextView beer_name, beer_origin;//, beer_rating;
        RatingBar ratingBar;


        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            beer_name = itemView.findViewById(R.id.beer_name);
            beer_origin = itemView.findViewById(R.id.beer_origin);
//            beer_rating = itemView.findViewById(R.id.beer_rating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }

    private float PercentStringToFloat(String percentString, int min, int max){
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
        System.out.println(result);

        return result;
    }
}
