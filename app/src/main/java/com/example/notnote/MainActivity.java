package com.example.notnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/*  NIM : 10119288
    Nama : Muhamad Dimas
    Kelas : IF-7
    Tanggal : Rabu, 11 Agustus 2022
    */
public class MainActivity extends AppCompatActivity {
    private CatatanAdapter catatanAdapter;
    private DBcatatan database;
    private LinearLayout linearEditNotes, linearDeleteNotes;
    private Button btnBatalPopup;
    private FirebaseAuth mauth;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mauth=FirebaseAuth.getInstance();

        //PENTING
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);

        //inisialisasi
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
                    return true;
                case R.id.nav_profil:
                    startActivity(new Intent(getApplicationContext()
                            ,Profil.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_about:
                    startActivity(new Intent(getApplicationContext()
                            , InfoActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        //db inisialisasi
        database = new DBcatatan(this);

        RecyclerView rvNotes = findViewById(R.id.rvNotes);
        FloatingActionButton fabAddNotes = findViewById(R.id.fabAddNotes);

        rvNotes.setLayoutManager(new LinearLayoutManager(this));
        catatanAdapter = new CatatanAdapter(this, database.getAllNotes());
        rvNotes.setAdapter(catatanAdapter);
        catatanAdapter.swapCursor(database.getAllNotes());

        fabAddNotes.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, TambahCatatanActivity.class)));

        catatanAdapter.setOnClickListenerNotes(id -> {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            @SuppressLint("InflateParams") View view = inflater.inflate(R.layout.popup_catatan, null);
            linearEditNotes = view.findViewById(R.id.linearEditNotes);
            linearDeleteNotes = view.findViewById(R.id.linearDeleteNotes);
            btnBatalPopup = view.findViewById(R.id.btnBatalPopup);

            Dialog popupNotes = new Dialog(MainActivity.this);
            popupNotes.setContentView(view);
            popupNotes.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            popupNotes.setOnShowListener(dialog -> {
                linearEditNotes.setOnClickListener(v -> {
                    Intent editNotes = new Intent(MainActivity.this, TambahCatatanActivity.class);
                    editNotes.putExtra(DBcatatan.id_notes, id);
                    startActivity(editNotes);
                    popupNotes.dismiss();
                });
                linearDeleteNotes.setOnClickListener(v -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Konfirmasi");
                    builder.setMessage("Apakah anda yakin ingin menghapus data ini?");
                    builder.setPositiveButton("Ya", (dialog1, which) -> {
                        database.deleteNotes(id);
                        popupNotes.dismiss();
                        catatanAdapter.swapCursor(database.getAllNotes());
                    }).setNegativeButton("Tidak", (dialog1, which) -> popupNotes.dismiss());
                    AlertDialog popupKonfirmasi = builder.create();
                    popupKonfirmasi.show();
                });
                btnBatalPopup.setOnClickListener(v -> popupNotes.dismiss());
            });
            popupNotes.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        catatanAdapter.swapCursor(database.getAllNotes());
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser=mauth.getCurrentUser();
        if (currentUser==null){
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
        }
    }

    public void onBackPressed(){
        Toast.makeText(MainActivity.this, "Please Click the Home Button", Toast.LENGTH_LONG).show();
    }

}