package com.akasa.kitafit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.akasa.kitafit.R;

public class TutorialPenggunaanAplikasi extends AppCompatActivity {

    public static final String PILIHAN_TUTORIAL = "com.akasa.kitafit.pilihan_tutorial";

    Button caraPenggunaan, caraAktivitas;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_penggunaan_aplikasi);
        caraPenggunaan = findViewById(R.id.caraPenggunaanAplikasiButton);
        caraAktivitas = findViewById(R.id.caraPost);
        backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        caraPenggunaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialPenggunaanAplikasi.this, DetailTutorialPenggunaanAplikasi.class);
                intent.putExtra(PILIHAN_TUTORIAL, "cara_penggunaan");
                startActivity(intent);
            }
        });
        caraAktivitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TutorialPenggunaanAplikasi.this, DetailTutorialPenggunaanAplikasi.class);
                intent.putExtra(PILIHAN_TUTORIAL, "cara_aktivitas");
                startActivity(intent);
            }
        });
    }
}