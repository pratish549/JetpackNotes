package com.pratishjage.jetpacknotes.UI.List;

import android.app.Application;

import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.Repositories.NoteRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class MainActivityViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> notes;

    public MainActivityViewModel(Application application) {
        super(application);
        noteRepository = NoteRepository.getInstance(application);
        notes = noteRepository.getNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void insertNote(Note note) {
        noteRepository.addNote(note);
    }

    public void deleteNote(Note note) {
        noteRepository.deleteNote(note);
    }

    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }
}
