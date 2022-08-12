package com.example.notnote;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Profil extends AppCompatActivity {
    /*  NIM : 10119288
        Nama : Muhamad Dimas
        Kelas : IF-7
        Tanggal : Rabu, 11 Agustus 2022
        */
    private FirebaseAuth mauth;
    private Button logout_btn;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        logout_btn=findViewById(R.id.logout_btn);
        mauth=FirebaseAuth.getInstance();
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //PENTING
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_profil);

        //inisialisasi
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext()
                            ,MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_profil:
                    return true;
                case R.id.nav_about:
                    startActivity(new Intent(getApplicationContext()
                            , InfoActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Profil.this,LoginActivity.class));
    }

    public void onBackPressed(){
        Toast.makeText(Profil.this, "Please Click the Home Button", Toast.LENGTH_LONG).show();
    }

}