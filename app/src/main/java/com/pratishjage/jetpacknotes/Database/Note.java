package com.pratishjage.jetpacknotes.Database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes_table")
public class Note {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "id")
    int id;

    @NonNull
    @ColumnInfo(name = "mNote")
    String mNote;

    public Note(@NonNull String s) {
        this.mNote = s;
    }

    public Note() {
    }

    public String getmNote() {
        return this.mNote;
    }

    @Ignore
    public Note(int id, @NonNull String mNote) {
        this.id = id;
        this.mNote = mNote;
    }

    @Ignore
    public int getId() {
        return id;
    }

    public void setmNote(@NonNull String mNote) {
        this.mNote = mNote;
    }
}
