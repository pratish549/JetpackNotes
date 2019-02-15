package com.pratishjage.jetpacknotes.UI.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.pratishjage.jetpacknotes.Adapters.NoteListAdapter;
import com.pratishjage.jetpacknotes.AppConstants;
import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.R;
import com.pratishjage.jetpacknotes.UI.Detail.DetailActivity;
import com.pratishjage.jetpacknotes.UI.NewNoteDialog;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NewNoteDialog.Notelistner {
    RecyclerView recyclerView;
    private MainActivityViewModel activityViewModel;

    MaterialButton addNoteBtn;
    private Paint p = new Paint();
    Intent outsideIntent;
    String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        addNoteBtn = findViewById(R.id.save_btn);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        final NoteListAdapter noteListAdapter = new NoteListAdapter(this);
        recyclerView.setAdapter(noteListAdapter);
        activityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        activityViewModel.getNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                noteListAdapter.setNotes(notes);
            }
        });

        addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new NewNoteDialog(MainActivity.this).show(getSupportFragmentManager(), "dialog");
            }
        });

        outsideIntent = getIntent();
        if (outsideIntent != null) {
            if (outsideIntent.getAction().equals(Intent.ACTION_SEND)) {
                Log.d(TAG, "onCreate: EXTRA TEXT : " + outsideIntent.getStringExtra(Intent.EXTRA_TEXT));

                NewNoteDialog newNoteDialog = new NewNoteDialog(this);

                Bundle bundle = new Bundle();
                bundle.putString(NewNoteDialog.OUTSIDE_NOTE, outsideIntent.getStringExtra(Intent.EXTRA_TEXT));
                newNoteDialog.setArguments(bundle);
                newNoteDialog.show(getSupportFragmentManager(), "dialog");
            }
        }

        findViewById(R.id.imageView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityViewModel.deleteAllNotes();
            }
        });


        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        Note myNote = noteListAdapter.getNoteAtPosition(position);
                        // Delete the note
                        activityViewModel.deleteNote(myNote);
                    }

                    @Override
                    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                            View itemView = viewHolder.itemView;
                            if (dX > 0) {
                                p.setColor(Color.parseColor("#e4e3e5"));
                                RectF background = new RectF((float) itemView.getLeft(), (float) itemView.getTop(), dX, (float) itemView.getBottom());
                                c.drawRect(background, p);
                            } else {
                                p.setColor(Color.parseColor("#e4e3e5"));
                                RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                                c.drawRect(background, p);
                            }
                        }
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
        );

        helper.attachToRecyclerView(recyclerView);
        noteListAdapter.setOnItemClickListener(new NoteListAdapter.ClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Note note = noteListAdapter.getNoteAtPosition(position);

                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(AppConstants.NOTE_ID, note.getId());
                intent.putExtra(AppConstants.NOTE_MSG, note.getmNote());
                startActivity(intent);
            }
        });
    }

    @Override
    public void noteAdded(Note note) {
        activityViewModel.insertNote(note);
    }
}
