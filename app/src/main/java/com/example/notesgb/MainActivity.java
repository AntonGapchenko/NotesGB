package com.example.notesgb;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements NotesListFragment.Controller, NotesDetailFragment.Controller {
    private final String NOTE_FRAGMENT_TAG = "NOTE_FRAGMENT_TAG";
    private boolean isLandscape = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isLandscape = findViewById(R.id.detail_container) != null;
        showListNote();
        initNavigationView();


    }

    private void showListNote() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, new NotesListFragment(), NOTE_FRAGMENT_TAG)
                .commit();
    }


    @Override
    public void openProfileScreen(NotesEntity notesEntity) {
        getSupportFragmentManager().beginTransaction()
                .replace(isLandscape ? R.id.detail_container : R.id.fragment_container, NotesDetailFragment.newInstance(notesEntity))
                .addToBackStack(null)
                .commit();
    }


    @Override
    public void saveResult(NotesEntity notesEntity) {
        getSupportFragmentManager().popBackStack();
        NotesListFragment notesListFragment = (NotesListFragment) getSupportFragmentManager()
                .findFragmentByTag(NOTE_FRAGMENT_TAG);
        if (notesListFragment != null) notesListFragment.addNote(notesEntity);

    }


    public void initNavigationView() {
        BottomNavigationView botNavView = findViewById(R.id.bottom_navigation_view);
        botNavView.setOnNavigationItemSelectedListener(item -> {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(NOTE_FRAGMENT_TAG);
            if (fragment == null) {
                fragment = new NotesDetailFragment();
            }
            switch (item.getItemId()) {
                case ItemClass.ItemId.LIST_NOTE_ID:
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment, NOTE_FRAGMENT_TAG)
                            .commit();
                    break;
                case ItemClass.ItemId.EDIT_NOTE_ID:
                    getSupportFragmentManager().beginTransaction()
                            .replace(isLandscape ? R.id.detail_container : R.id.fragment_container, new NotesDetailFragment())
                            .addToBackStack(null)
                            .commit();
                    break;

            }

            return false;
        });

    }


}