package com.pratishjage.jetpacknotes.Database;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Query("SELECT * FROM notes_table")
    LiveData<List<Note>> getAllNotes();

    @Query("DELETE FROM notes_table")
    void deleteAllNotes();

    @Delete
    void deleteNote(Note note);

    @Update
    void updateNote(Note note);

    @Update
    void update(Note... word);

}
