package com.example.notesgb;


import java.io.Serializable;
import java.util.UUID;

public class NotesEntity implements Serializable {
    private String id;
    private String noteName;
    private String noteDescription;

    public NotesEntity(String id, String noteName, String noteDescription) {
        this.id = id;
        this.noteName = noteName;
        this.noteDescription = noteDescription;
    }

    public static String generateId() {
        return UUID.randomUUID().toString();
    }


    public String getId() {
        return id;
    }

    public String getNoteName() {
        return noteName;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    @Override
    public String toString() {
        return noteName;
    }


}
