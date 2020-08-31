package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.fragment.AktivitasFragment;
import com.akasa.kitafit.fragment.HomeFragment;
import com.akasa.kitafit.fragment.LiniMasaFragment;
import com.akasa.kitafit.fragment.ProfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private long backPressedTime;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()){
                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.nav_linimasa:
                    selectedFragment = new LiniMasaFragment();
                    break;
                case R.id.nav_aktivitas:
                    selectedFragment = new AktivitasFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfilFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            super.onBackPressed();
            finishAffinity();
            System.exit(0);
            return;
        } else {
            Toast.makeText(this, "Tekan Kembali lagi untuk Keluar", Toast.LENGTH_SHORT).show();
        }
       backPressedTime = System.currentTimeMillis();
    }
}