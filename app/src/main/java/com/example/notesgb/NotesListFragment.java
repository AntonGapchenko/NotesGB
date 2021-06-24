package com.example.notesgb;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class NotesListFragment extends Fragment {
    private RecyclerView recyclerView;
    private final ArrayList<NotesEntity> notes = new ArrayList<>();
    private final NotesListAdapter adapter = new NotesListAdapter();
    private NotesEntity deletedNote = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes, container, false);
        recyclerView = view.findViewById(R.id.recycler_list);
        return view;
    }

    public void addNote(NotesEntity note) {
        NotesEntity sameNote = findNoteWithId(note.getId());
        if (sameNote != null) {
            notes.remove(sameNote);
        }
        notes.add(note);
        adapter.setData(notes);

    }

    private NotesEntity findNoteWithId(String id) {
        for (NotesEntity note : notes) {
            if (note.getId().equals(id)) {
                return note;
            }

        }
        return null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement FragmentNotesList.Controller");
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter.setOnItemClickListener(getController()::openProfileScreen);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        adapter.setData(notes);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    final ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            deletedNote = notes.get(position);
            notes.remove(position);
            adapter.notifyItemChanged(position);
            adapter.notifyDataSetChanged();
            Snackbar.make(recyclerView, getString(R.string.note) + deletedNote.getNoteName() + getString(R.string.delete), Snackbar.LENGTH_LONG)
                    .setAction(R.string.cancel, v -> {
                        notes.add(position, deletedNote);
                        adapter.notifyItemChanged(position);

                    }).show();
        }
    };

    private Controller getController() {
        return (Controller) getActivity();
    }

    interface Controller {
        void openProfileScreen(NotesEntity notesEntity);
    }
}
