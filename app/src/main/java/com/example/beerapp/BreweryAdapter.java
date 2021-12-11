package com.example.beerapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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
        holder.brewery_map.setImageBitmap(brewery.getMapImage());

        boolean isExpandable = breweryList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);
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
        ImageView brewery_map;
        LinearLayoutCompat breweryRowLayout;
        RelativeLayout expandableLayout;

        public TodoViewHolder(@NonNull View itemView) {
            super(itemView);
            brew_title = itemView.findViewById(R.id.brewery_name);
            brew_bundesland = itemView.findViewById(R.id.brewery_bundesland);
            brew_ort = itemView.findViewById(R.id.brewery_ort);
            brewery_map = itemView.findViewById(R.id.mapView);
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

                    String ort_url = brewery.getOrt()
                            .replace("ä", "ae")
                            .replace("ö", "oe")
                            .replace("ü", "ue")
                            .replace("ß", "ss")
                            .replace("-", "_")
                            .replace(" ", "_");

                    brewery.setMapImage(getBitmapFromURL(
                    "https://image.maps.ls.hereapi.com/mia/1.6/mapview?apiKey=IYMkJ8JlwzgsI6Q-dDvpgmvmrDBX6Ll3Wv_h18WEX6Q&co=germany&ci="
                        + ort_url.toLowerCase()
                        + "&z=8&w=500&h=250"
                    ));

                    brewery.setExpandable(!brewery.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }

        private Bitmap getBitmapFromURL(String src){
            try{
                URL url = new URL(src);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap resBitmap = BitmapFactory.decodeStream(input);
                return resBitmap;
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }
    }
}
