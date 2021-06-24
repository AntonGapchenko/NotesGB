package com.example.notesgb;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


public class NotesViewHolder extends RecyclerView.ViewHolder {
    private TextView titleView;
    private TextView descriptionView;
    private CardView cardView;
    private NotesEntity notesEntity;

    public NotesViewHolder(@NonNull ViewGroup parent, @Nullable NotesListAdapter.OnItemClickListener clickListener) {
        super(LayoutInflater.from(parent.getContext()).inflate(R.layout.note_item_view, parent, false));
        cardView = (CardView) itemView;
        titleView = itemView.findViewById(R.id.text_title_view);
        descriptionView = itemView.findViewById(R.id.text_description_view);
        cardView.setOnClickListener(v -> {
            clickListener.onItemClick(notesEntity);

        });

    }

    public void bind(NotesEntity notesEntity) {
        this.notesEntity = notesEntity;
        titleView.setText(notesEntity.getNoteName());
        descriptionView.setText(notesEntity.getNoteDescription());


    }
}
