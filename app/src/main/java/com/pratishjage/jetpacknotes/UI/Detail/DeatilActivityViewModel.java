package com.pratishjage.jetpacknotes.UI.Detail;

import android.app.Application;

import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.Repositories.NoteRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class DeatilActivityViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;

    public DeatilActivityViewModel(@NonNull Application application) {
        super(application);
        noteRepository = NoteRepository.getInstance(application);
    }

    public void updateNote(Note note) {
        noteRepository.updateNote(note);
    }

    public void DeleteNote(Note note) {
        noteRepository.deleteNote(note);
    }
}
