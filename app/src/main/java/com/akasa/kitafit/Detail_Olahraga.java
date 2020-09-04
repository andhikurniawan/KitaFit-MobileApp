package com.akasa.kitafit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detail_Olahraga extends AppCompatActivity {

    TextView t1, deskripsi, durasi, fokus_area, kalori;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__olahraga);
        id = getIntent().getStringExtra("pid");

        t1 = findViewById(R.id.namaolahraga);
        deskripsi = findViewById(R.id.deskripsi_olahraga);
        durasi = findViewById(R.id.durasi);
        fokus_area = findViewById(R.id.fokus);
        kalori = findViewById(R.id.kalori);

    getDetailOlahraga(id);
    }

    private void getDetailOlahraga(String id) {
        DatabaseReference olgaRef = FirebaseDatabase.getInstance().getReference().child("daftar_olahraga").child(id);
        olgaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.exists()) {
                    OlahragaItem olahraga = dataSnapshot.getValue(OlahragaItem.class);


                    t1.setText(olahraga.getNama_olahraga());
                    deskripsi.setText(olahraga.getDeskripsi());
                    durasi.setText(olahraga.getDurasi());
                    fokus_area.setText(olahraga.getFokus_area());
                    kalori.setText("~ " +olahraga.getKalori());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}