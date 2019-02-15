package com.pratishjage.jetpacknotes.UI.Detail;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.button.MaterialButton;
import com.pratishjage.jetpacknotes.AppConstants;
import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class DetailActivity extends AppCompatActivity {
    String TAG = getClass().getSimpleName();
    EditText note_txt;
    MaterialButton save_btn;
    private DeatilActivityViewModel viewModel;
    int noteId;
    String receivedNote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        note_txt = findViewById(R.id.note_edt);
        save_btn = findViewById(R.id.save_btn);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            receivedNote = bundle.getString(AppConstants.NOTE_MSG, "");
            noteId = bundle.getInt(AppConstants.NOTE_ID, -1);
            if (!receivedNote.isEmpty()) {
                Log.d(TAG, "onCreate: " + receivedNote);
                note_txt.setText(receivedNote);
                note_txt.setSelection(receivedNote.length());
                note_txt.requestFocus();
            }
        }

        viewModel = ViewModelProviders.of(this).get(DeatilActivityViewModel.class);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String note = note_txt.getText().toString();
                if (!note.isEmpty()) {
                    Note note1 = new Note(noteId, note);
                    viewModel.updateNote(note1);
                    onBackPressed();
                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.deatil_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.action_delete) {
            Note note = new Note(noteId, receivedNote);
            viewModel.DeleteNote(note);
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }
}
