package com.example.uts_10120032;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

// NIM : 10120032
// NAMA : HANIF AMRULLAH ALMUHARAM
// KELAS : IF 1

public class NotesFragment extends Fragment implements NotesAdapter.ItemClickListener{

    private RecyclerView rv_notes;
    private List<Notes> notesList;
    private NotesInterface notesInterface;
    private NotesAdapter notesAdapter;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        read();
    }

    @Override
    public void onResume() {
        super.onResume();
        read();
    }

    private void init(View view) {
        rv_notes = view.findViewById(R.id.rv_notes);
        rv_notes.setLayoutManager(new LinearLayoutManager(getActivity()));

        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), InputActivity.class));
            }
        });
    }

    private void read() {
        notesList = new ArrayList<>();
        notesInterface = new NotesImp(getActivity());

        Cursor cursor = notesInterface.read();
        if (cursor.moveToFirst()) {
            do {
                Notes note = new Notes(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                );
                notesList.add(note);
            } while (cursor.moveToNext());
        }

        notesAdapter = new NotesAdapter(getActivity(), notesList);
        notesAdapter.setClickListener(this);
        rv_notes.setAdapter(notesAdapter);
    }

    @Override
    public void onItemClick(View view, int position) {
        Notes note = notesList.get(position);

        String[] action = {"Edit", "Delete"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Action");
        builder.setItems(action, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case 0:
                        Intent intent = new Intent(getActivity(), InputActivity.class);
                        intent.putExtra("id", note.getId());
                        intent.putExtra("title", note.getTitle());
                        intent.putExtra("note", note.getNote());
                        intent.putExtra("category", note.getCategory());
                        startActivity(intent);
                        break;
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Are you sure?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (notesInterface.delete(note.getId())) {
                                    Toast.makeText(getActivity(), "Successfully deleted", Toast.LENGTH_SHORT).show();
                                    read();
                                }
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        builder.show();
                        break;
                }
            }
        });
        builder.show();
    }
}