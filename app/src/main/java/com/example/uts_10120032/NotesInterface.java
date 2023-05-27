package com.example.uts_10120032;

import android.database.Cursor;

// NIM : 10120032
// NAMA : HANIF AMRULLAH ALMUHARAM
// KELAS : IF 1

public interface NotesInterface {
    public Cursor read();
    public boolean create(Notes note);
    public boolean update(Notes note);
    public boolean delete(String id);
}
