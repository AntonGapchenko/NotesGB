package com.example.notesgb;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class NotesDetailFragment extends Fragment {
    private static final String NOTES_KEY = "NOTES_KEY";
    private EditText notesNameEdit;
    private EditText notesDescriptionEdit;
    private Button saveNoteBtn;
    private NotesEntity notesEntity = null;

    public static NotesDetailFragment newInstance(NotesEntity notesEntity) {
        NotesDetailFragment fragmentNotesDetail = new NotesDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(NOTES_KEY, notesEntity);
        fragmentNotesDetail.setArguments(args);
        return fragmentNotesDetail;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_details, container, false);
        notesNameEdit = view.findViewById(R.id.edit_note_name);
        notesDescriptionEdit = view.findViewById(R.id.edit_note_description);
        saveNoteBtn = view.findViewById(R.id.btn_note_save);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getActivity().setTitle(notesEntity == null ? R.string.create_note : R.string.edit_note);
        saveNoteBtn.setOnClickListener(v -> {
            getController().saveResult(gatherNote());
        });
        fillNote(notesEntity);


    }

    private void fillNote(NotesEntity note) {
        if (note == null) return;
        notesNameEdit.setText(note.getNoteName());
        notesDescriptionEdit.setText(note.getNoteDescription());
    }

    private NotesEntity gatherNote() {
        return new NotesEntity(notesEntity == null ? NotesEntity.generateId() : notesEntity.getId(),
                notesNameEdit.getText().toString(),
                notesDescriptionEdit.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Controller)) {
            throw new RuntimeException("Activity must implement FragmentNotes.Detail.Controller");
        }
        if (getArguments() != null) {
            notesEntity = (NotesEntity) getArguments().getSerializable(NOTES_KEY);
        }
    }

    private Controller getController() {
        return (Controller) getActivity();
    }

    public interface Controller {
        void saveResult(NotesEntity notesEntity);
    }
}
