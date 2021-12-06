package com.example.beerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class BreweryAdapter extends RecyclerView.Adapter<BreweryAdapter.TodoViewHolder> {

    static List<Brewery> breweryList;
    int currentPositionExpanded = -1;
    ImageView mapView;

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
        holder.brew_title.setText(brewery.getTitle());
        holder.brew_bundesland.setText(brewery.getBundesland());
        holder.brew_ort.setText(brewery.getOrt());

        boolean isExpandable = breweryList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

        if(isExpandable){
            brewery.generateMapImage();
            try{
                System.out.println("Height: " + brewery.getMapImage().getByteCount());
                mapView.setImageBitmap(brewery.getMapImage());
            }catch(NullPointerException e){
                System.out.println("No Map could be loaded");
            }
        }
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

    public class TodoViewHolder extends RecyclerView.ViewHolder {
        TextView brew_title, brew_bundesland, brew_ort;
        LinearLayoutCompat breweryRowLayout;
        RelativeLayout expandableLayout;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            brew_title = itemView.findViewById(R.id.brewery_name);
            brew_bundesland = itemView.findViewById(R.id.brewery_bundesland);
            brew_ort = itemView.findViewById(R.id.brewery_ort);
            breweryRowLayout = itemView.findViewById(R.id.breweryRowLayout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            mapView = itemView.findViewById(R.id.mapView);

            breweryRowLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentPositionExpanded > 0){
                        breweryList.get(currentPositionExpanded).setExpandable(false);
                        expandableLayout.setVisibility(View.GONE);
                    }

                    Brewery brewery = breweryList.get(getAdapterPosition());
                    brewery.setExpandable(!brewery.isExpandable());
                    notifyItemChanged(getAdapterPosition());
            }
            });
        }
    }
}
