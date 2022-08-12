package com.example.notnote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class InfoActivity extends AppCompatActivity {
    /*  NIM : 10119288
        Nama : Muhamad Dimas
        Kelas : IF-7
        Tanggal : Rabu, 11 Agustus 2022
        */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        DotsIndicator dotsIndicator = findViewById(R.id.dots_indicator);
        ViewPager2 viewPager2 = findViewById(R.id.viewPager2);
        InfoAdapter InfoAdapter = new InfoAdapter(ObjectInfo.createData());
        viewPager2.setAdapter(InfoAdapter);
        dotsIndicator.setViewPager2(viewPager2);

        //PENTING
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_about);

        //inisialisasi
        bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.nav_home:
                    startActivity(new Intent(getApplicationContext()
                            ,MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_profil:
                    startActivity(new Intent(getApplicationContext()
                            ,Profil.class));
                    overridePendingTransition(0,0);
                    return true;
                case R.id.nav_about:
                    return true;
            }
            return false;
        });
    }

    public void onBackPressed(){
        Toast.makeText(InfoActivity.this, "Please Click the Home Button", Toast.LENGTH_LONG).show();
    }
}