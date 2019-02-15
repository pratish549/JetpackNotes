package com.pratishjage.jetpacknotes.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pratishjage.jetpacknotes.Database.Note;
import com.pratishjage.jetpacknotes.R;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {

    private final LayoutInflater mInflater;
    private List<Note> mNotes;
    private static ClickListener clickListener;

    public interface ClickListener {
        void onItemClick(View v, int position);
    }

    public NoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public NoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NoteViewHolder holder, int position) {
        if (mNotes != null) {
            Note current = mNotes.get(position);
            holder.noteItemView.setText(current.getmNote());
        } else {
            // Covers the case of data not being ready yet.
            holder.noteItemView.setText("No Note");
        }
    }

    public void setNotes(List<Note> Notes) {
        mNotes = Notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotes != null)
            return mNotes.size();
        else return 0;
    }

    public Note getNoteAtPosition(int position) {
        return mNotes.get(position);
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        private final TextView noteItemView;

        private NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.textview);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickListener.onItemClick(view, getAdapterPosition());
                }
            });
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        NoteListAdapter.clickListener = clickListener;
    }
}