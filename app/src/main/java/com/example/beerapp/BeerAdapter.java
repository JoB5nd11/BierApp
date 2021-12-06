package com.example.beerapp;


import static androidx.core.graphics.drawable.IconCompat.*;
import static com.example.beerapp.R.*;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.LayerDrawable;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BeerAdapter extends RecyclerView.Adapter<BeerAdapter.TodoViewHolder> {

    static List<Beer> beerList;
    int ratingMin = 1, ratingMax = 5;

    public BeerAdapter(List<Beer> beerList) {
        this.beerList = beerList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.beer_cardview, parent, false);
        TodoViewHolder todoViewHoldervh = new TodoViewHolder(v);
        return todoViewHoldervh;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        Context context = holder.itemView.getContext();
        Beer beer = beerList.get(position);
        holder.beer_name.setText(beer.getBier());
        holder.beer_origin.setText(beer.getHerkunft());
//        holder.beer_rating.setText(beer.getBewertung());
        holder.ratingBar.setStepSize(0.1f);
        holder.ratingBar.setRating(PercentStringToFloat(beer.getBewertung(), ratingMin, ratingMax));

        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.setTint(ContextCompat.getColor(context, color.orange));
        //stars.setTint(getResources().getColor(R.color.orange));

        boolean isExpandable = beerList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
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

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView beer_name, beer_origin;//, beer_rating;
        RatingBar ratingBar;
        LinearLayoutCompat beerRowLayout;
        RelativeLayout expandableLayout;


        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            beer_name = itemView.findViewById(id.beer_name);
            beer_origin = itemView.findViewById(id.beer_origin);
//            beer_rating = itemView.findViewById(R.id.beer_rating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            beerRowLayout = itemView.findViewById(id.beerRowLayout);
            expandableLayout = itemView.findViewById(id.expandable_layout);

            beerRowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Beer beer = beerList.get(getAdapterPosition());
                    beer.setExpandable(!beer.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
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

        return result;
    }
}
