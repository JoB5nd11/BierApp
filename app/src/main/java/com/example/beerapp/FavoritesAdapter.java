package com.example.beerapp;

import android.content.Context;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.TodoViewHolder> {

    static List<Beer> favoritesList;
  //  int currentPositionExpanded = -1;

    int ratingMax = 5;


    public FavoritesAdapter(List<Beer> favoritesList) {
        this.favoritesList = favoritesList;
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
        Beer beer = favoritesList.get(position);
        holder.beer_name.setText(beer.getBier());
        holder.beer_origin.setText(beer.getHerkunft());
//        holder.beer_rating.setText(beer.getBewertung());
        holder.ratingBar.setStepSize(0.1f);
        holder.ratingBar.setRating(PercentStringToFloat(beer.getBewertung(), ratingMax));
        holder.beer_favorite.setVisibility(View.GONE);

        LayerDrawable stars = (LayerDrawable) holder.ratingBar.getProgressDrawable();
        stars.setTint(ContextCompat.getColor(context, R.color.orange));


    }

    @Override
    public int getItemCount() {
        return favoritesList.size();
    }

    public void addTodo(Beer todo) {
        favoritesList.add(todo);
        notifyItemInserted(favoritesList.size()-1);
    }

    public void removeItem(RecyclerView.ViewHolder viewHolder)
    {
        int removePosition = viewHolder.getAdapterPosition();
        favoritesList.remove(removePosition);
        notifyItemRemoved(removePosition);
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder {

        TextView beer_name, beer_origin;//, beer_rating;
        RatingBar ratingBar;
        LinearLayoutCompat beerRowLayout;
        RelativeLayout expandableLayout;
        ImageView beer_favorite;
        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            beer_name = itemView.findViewById(R.id.beer_name);
            beer_origin = itemView.findViewById(R.id.beer_origin);
//            beer_rating = itemView.findViewById(R.id.beer_rating);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            beerRowLayout = itemView.findViewById(R.id.beerRowLayout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            beer_favorite = itemView.findViewById(R.id.icFavorite);

        }
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
