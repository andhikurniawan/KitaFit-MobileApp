package com.akasa.kitafit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.akasa.kitafit.R;
import com.github.barteksc.pdfviewer.PDFView;

import static com.akasa.kitafit.activity.TutorialPenggunaanAplikasi.PILIHAN_TUTORIAL;

public class DetailTutorialPenggunaanAplikasi extends AppCompatActivity {
    PDFView pdfView;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_tutorial_penggunaan_aplikasi);
        pdfView = findViewById(R.id.pdfView);
        backButton = findViewById(R.id.back_button);
        Intent intent = getIntent();
        if (intent.getStringExtra(PILIHAN_TUTORIAL).equals("cara_penggunaan")){
            pdfView.fromAsset("cara_penggunaan.pdf")
                    .enableSwipe(true)
                    .load();
        }
       backButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               onBackPressed();
           }
       });
    }
}