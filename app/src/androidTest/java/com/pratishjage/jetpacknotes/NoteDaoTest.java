package com.pratishjage.jetpacknotes;

import android.content.Context;

import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.Database.NoteDao;
import com.pratishjage.jetpacknotes.Database.NoteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import androidx.room.Room;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class NoteDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private NoteDao noteDao;
    private NoteDatabase db;
    String TAG = getClass().getName();
    Context context;

    @Before
    public void createdb() {
        context = InstrumentationRegistry.getTargetContext();
        db = Room.databaseBuilder(context, NoteDatabase.class, "note_db").allowMainThreadQueries().build();
        noteDao = db.noteDao();
    }

    @After
    public void clodedb() {
        db.close();
    }

    @Test
    public void updateNote() throws Exception {
        Note note = new Note("hello");
        Note note1 = new Note("One");
        noteDao.insert(note);
        noteDao.insert(note1);

        List<Note> notes = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        Note toupdatenote = notes.get(0);
        toupdatenote.setmNote("tow");
        noteDao.update(toupdatenote);
        List<Note> noteslist = LiveDataTestUtil.getValue(noteDao.getAllNotes());
        assertEquals(noteslist.get(0).getmNote(), "tow");
    }

}
