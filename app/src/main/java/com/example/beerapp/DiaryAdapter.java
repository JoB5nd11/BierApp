package com.example.beerapp;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.TodoViewHolder> {
    static List<DiaryEntry> entryList;

    public DiaryAdapter(List<DiaryEntry> entryList){
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public TodoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_cardview, parent, false);
        TodoViewHolder todoViewHolder = new TodoViewHolder(view);
        return todoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position){
        Context context = holder.itemView.getContext();
        DiaryEntry entry = entryList.get(position);

        holder.diaryTitle.setText(entry.getTitle());
        holder.diaryDate.setText(entry.getDate().toString());
        holder.diaryText.setText(entry.getText());
        holder.diaryImage.setImageBitmap(entry.getImage());
    }

    @Override
    public int getItemCount(){
        return entryList.size();
    }

    public void addEntry(DiaryEntry entry){
        entryList.add(entry);
        notifyItemInserted(entryList.size() - 1);
    }

    public class TodoViewHolder extends RecyclerView.ViewHolder{
        TextView diaryTitle, diaryDate, diaryText;
        ImageView diaryImage;
        LinearLayoutCompat diaryRowLayout;

        public TodoViewHolder(@NonNull View itemView){
            super(itemView);
            diaryTitle = itemView.findViewById(R.id.diary_title);
            diaryDate = itemView.findViewById(R.id.diary_date);
            diaryText = itemView.findViewById(R.id.diary_text);
            diaryImage = itemView.findViewById(R.id.diary_image);
            diaryRowLayout = itemView.findViewById(R.id.diaryRowLayout);
        }
    }
}
