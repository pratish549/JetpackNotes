package com.pratishjage.jetpacknotes.UI;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.button.MaterialButton;
import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

@SuppressLint("ValidFragment")
public class NewNoteDialog extends BottomSheetDialogFragment {
    EditText editNote;
    MaterialButton save_btn;
    public static final String OUTSIDE_NOTE = "outside_note";
    String outsideNote = "";

    public interface Notelistner {
        void noteAdded(Note note);
    }

    Notelistner notelistner;

    public NewNoteDialog(Notelistner notelistner) {
        this.notelistner = notelistner;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.new_note_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editNote = view.findViewById(R.id.editnote);
        save_btn = view.findViewById(R.id.save_btn);
        Bundle bundle = getArguments();
        if (bundle != null) {
            outsideNote = getArguments().getString(OUTSIDE_NOTE, "");

            if (!outsideNote.isEmpty()) {
                editNote.setText(outsideNote);
                editNote.setSelection(outsideNote.length());
            }
        }


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editNote.getText().toString().isEmpty()) {
                    Note note = new Note(editNote.getText().toString());
                    notelistner.noteAdded(note);
                    dismiss();
                }

            }
        });
    }
}
