package com.pratishjage.jetpacknotes.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Note.class}, version = 1,exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NoteDao noteDao();
    private static NoteDatabase instance;

    public static NoteDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (NoteDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, NoteDatabase.class, "notedb").fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
