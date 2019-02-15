package com.pratishjage.jetpacknotes.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.Database.NoteDao;
import com.pratishjage.jetpacknotes.Database.NoteDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {
    private NoteDao mnoteDao;
    private LiveData<List<Note>> notes;
    private static NoteRepository instance;

    public static NoteRepository getInstance(Application application) {

        if (instance == null) {
            synchronized (NoteRepository.class) {
                if (instance == null) {
                    instance = new NoteRepository(application);
                }
            }
        }
        return instance;
    }

    public NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getInstance(application);
        mnoteDao = db.noteDao();
        notes = mnoteDao.getAllNotes();
    }

    public LiveData<List<Note>> getNotes() {
        return notes;
    }

    public void addNote(Note note) {
        new insertAsyncTask(mnoteDao).execute(note);
    }

    public void deleteNote(Note note) {
        new deleteNoteAsyncTask(mnoteDao).execute(note);
    }

    public void deleteAllNotes() {
        new deletAllnotesAsync(mnoteDao).execute();
    }

    public void updateNote(Note note) {
        new updateNoteAsync(mnoteDao).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao noteDao;

        public insertAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class deleteNoteAsyncTask extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        public deleteNoteAsyncTask(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.deleteNote(notes[0]);
            return null;
        }
    }

    private static class deletAllnotesAsync extends AsyncTask<Void, Void, Void> {
        private NoteDao mNoteDao;

        public deletAllnotesAsync(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mNoteDao.deleteAllNotes();
            return null;
        }
    }

    private static class updateNoteAsync extends AsyncTask<Note, Void, Void> {
        private NoteDao mNoteDao;

        public updateNoteAsync(NoteDao mNoteDao) {
            this.mNoteDao = mNoteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mNoteDao.updateNote(notes[0]);
            return null;
        }
    }
}
