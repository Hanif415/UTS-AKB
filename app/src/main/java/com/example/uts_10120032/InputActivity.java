package com.example.uts_10120032;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.Random;

// NIM : 10120032
// NAMA : HANIF AMRULLAH ALMUHARAM
// KELAS : IF 1

public class InputActivity extends AppCompatActivity {

    NotesInterface notesInterface;
    EditText edtTitle, edtCategory, edtNote;
    TextView textAdd;
    Intent intent;
    private boolean TAG = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        init();

        intent = getIntent();

        if (intent.getStringExtra("id") != null) {
            textAdd.setText("EDIT NOTE");
            edtTitle.setText(intent.getStringExtra("title"));
            edtCategory.setText(intent.getStringExtra("category"));
            edtNote.setText(intent.getStringExtra("note"));

            TAG = false;
        } else {
            TAG = true;
        }
    }

    private void init() {
        edtTitle = findViewById(R.id.edt_title);
        edtCategory = findViewById(R.id.edt_category);
        edtNote = findViewById(R.id.edt_note);
        textAdd = findViewById(R.id.text_add);

        Button btnSave = findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                create();
            }
        });
    }

    private void create() {
        notesInterface = new NotesImp(this);

        if (TAG) {
            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());

            Notes note = new Notes(
                    generateTextRandom(),
                    edtTitle.getText().toString(),
                    edtNote.getText().toString(),
                    currentDateTimeString,
                    edtCategory.getText().toString()
            );


            if (notesInterface.create(note)) {
                Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
            Notes note = new Notes(
                    intent.getStringExtra("id"),
                    edtTitle.getText().toString(),
                    edtNote.getText().toString(),
                    currentDateTimeString,
                    edtCategory.getText().toString()
            );


            if (notesInterface.update(note)) {
                Toast.makeText(this, "Updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private static String generateTextRandom() {
        byte[] array = new byte[5];
        new Random().nextBytes(array);
        return new String(array, Charset.forName("UTF-8"));
    }
}