package com.example.notnote;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.util.Calendar;
/*  NIM : 10119288
    Nama : Muhamad Dimas
    Kelas : IF-7
    Tanggal : Rabu, 11 Agustus 2022
    */

public class TambahCatatanActivity extends AppCompatActivity {
    private EditText etNama, etTanggalBuat, etIsiCatatan;
    private DBcatatan database;
    private long id;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_catatan);

        intent = getIntent();
        id = intent.getLongExtra(DBcatatan.id_notes,0);

        if(intent.hasExtra(DBcatatan.id_notes)){
            setTitle("Edit Catatn");
        }else{
            setTitle("Tambah Biodata");
        }

        etNama = findViewById(R.id.etJudul);
        etTanggalBuat = findViewById(R.id.etTanggalBuat);
        etIsiCatatan = findViewById(R.id.etIsiCatatan);

        Button btnSimpan = findViewById(R.id.btnSimpan);
        Button btnBatal = findViewById(R.id.btnBatal);

        database = new DBcatatan(this);

        //SET TANGGAL LAHIR
        etTanggalBuat.setOnClickListener(v -> {
            Calendar getCalendar = Calendar.getInstance();
            DatePickerDialog datePickerDialog = new DatePickerDialog(TambahCatatanActivity.this, (view, year, month, dayOfMonth) -> {
                Calendar setCalendar = Calendar.getInstance();
                setCalendar.set(Calendar.YEAR, year);
                setCalendar.set(Calendar.MONTH, month);
                setCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String setCurrentDate = DateFormat.getDateInstance().format(setCalendar.getTime());
                etTanggalBuat.setText(setCurrentDate);
            },getCalendar.get(Calendar.YEAR), getCalendar.get(Calendar.MONTH), getCalendar.get(Calendar.DAY_OF_MONTH));
            datePickerDialog.show();
        });

        //SET BTNSIMPAN
        btnSimpan.setOnClickListener(v -> prosesSimpan());

        //set btnBatal
        btnBatal.setOnClickListener(v -> finish());
        getNotes();
    }

    private void getNotes(){
        Cursor cursor = database.getNotes(id);
        if(cursor.moveToFirst()){
            String nama = cursor.getString(cursor.getColumnIndexOrThrow(DBcatatan.judul));
            String tanggalbuat = cursor.getString(cursor.getColumnIndexOrThrow(DBcatatan.tanggal_buat));
            String isi_catatan = cursor.getString(cursor.getColumnIndexOrThrow(DBcatatan.isi_catatan));

            etNama.setText(nama);
            etTanggalBuat.setText(tanggalbuat);
            etIsiCatatan.setText(isi_catatan);
        }
    }

    private void prosesSimpan(){
        String nama = etNama.getText().toString().trim();
        String tanggalbuat = etTanggalBuat.getText().toString().trim();
        String isicatatan = etIsiCatatan.getText().toString().trim();

        if (nama.isEmpty()){
            etNama.setError("Judul tidak boleh kosong");
        }else if(tanggalbuat.isEmpty()){
            etTanggalBuat.setError("Tanggal tidak boleh kosong");
        }else if (isicatatan.isEmpty()){
            etIsiCatatan.setError("Isi catatan tidak boleh kosong");
        }else{
            ContentValues values = new ContentValues();
            values.put(DBcatatan.judul, nama);
            values.put(DBcatatan.tanggal_buat, tanggalbuat);
            values.put(DBcatatan.isi_catatan, isicatatan);
            if (intent.hasExtra(DBcatatan.id_notes)){
                database.updateNotes(values, id);
            }else{
                database.insertNotes(values);
            }
            finish();
        }
    }
}