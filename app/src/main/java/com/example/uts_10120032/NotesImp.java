package com.example.uts_10120032;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

// NIM : 10120032
// NAMA : HANIF AMRULLAH ALMUHARAM
// KELAS : IF 1

public class NotesImp extends SQLiteOpenHelper implements NotesInterface {

    public NotesImp(@Nullable Context context) {
        super(context, "db_notes", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE tbl_notes (id TEXT, title TEXT, note TEXT, date TEXT, category TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE tbl_notes");
    }

    @Override
    public Cursor read() {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.rawQuery("SELECT * FROM tbl_notes ORDER BY date DESC", null);
    }

    @Override
    public boolean create(Notes note) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("INSERT INTO tbl_notes VALUES('" + note.getId() + "', '" + note.getTitle() + "', '" + note.getNote() + "', '" + note.getDate() + "', '" + note.getCategory() + "')");
        return true;
    }

    @Override
    public boolean update(Notes note) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("UPDATE tbl_notes SET title='" + note.getTitle() + "', note='" + note.getTitle() + "', date='" + note.getDate() + "',  category ='" + note.getCategory() + "' WHERE id='" + note.getId() + "'");
        return true;
    }

    @Override
    public boolean delete(String id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM tbl_notes WHERE id='" + id + "'");
        return true;
    }
}
