package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.adapter.DaftarOlahragaPKAdapter;
import com.akasa.kitafit.model.OlahragaItem;
import com.akasa.kitafit.model.PolaMakanData;
import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.akasa.kitafit.adapter.ProgramKesehatanAdapter.DESKRIPSI_PROGRAM_KESEHATAN;
import static com.akasa.kitafit.adapter.ProgramKesehatanAdapter.GAMBAR_PROGRAM_KESEHATAN;
import static com.akasa.kitafit.adapter.ProgramKesehatanAdapter.ID_PROGRAM_KESEHATAN;
import static com.akasa.kitafit.adapter.ProgramKesehatanAdapter.KALORI_PROGRAM_KESEHATAN;
import static com.akasa.kitafit.adapter.ProgramKesehatanAdapter.NAMA_PROGRAM_KESEHATAN;

public class DetailProgramKesehatan extends AppCompatActivity {

    private static final String TAG = "DetailProgramKesehatan";
    ImageView backButton, gambarProgram;
    TextView namaProgram, totalKalori, deskripsiProgram;
    RecyclerView polaMakanRecycler, daftarOlahragaRecycler;
    Button mulaiLatihanButton;
    View sarapanview, makanSiangView, makanMalamView;
    String idProgramIntent, gambarProgramIntent, kaloriProgramIntent, namaProgramIntent, deskripsiProgramIntent;
    DatabaseReference makanRef, olahragaRef;
    ArrayList<PolaMakanData> polaMakanList;
    ArrayList<OlahragaItem> olahragaItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_program_kesehatan);
        backButton = findViewById(R.id.back_button);
        gambarProgram = findViewById(R.id.gambar_detail_program_kesehatan);
        namaProgram = findViewById(R.id.nama_program_detail_kesehatan);
        totalKalori = findViewById(R.id.jumlah_kalori_detail_program_kesehatan);
        deskripsiProgram = findViewById(R.id.deskripsi_detail_program_kesehatan);
        daftarOlahragaRecycler = findViewById(R.id.daftar_olahraga_recycler);
        mulaiLatihanButton = findViewById(R.id.mulai_latihan_button);
        sarapanview = findViewById(R.id.sarapan);
        makanSiangView = findViewById(R.id.makanSiang);
        makanMalamView = findViewById(R.id.makanMalam);

        retrieveIntent();
        polaMakanRef();
        settingText();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DetailProgramKesehatan.this, RecyclerView.HORIZONTAL, false);
        daftarOlahragaRecycler.setLayoutManager(linearLayoutManager);

        readDaftarOlahraga();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void readDaftarOlahraga() {
        olahragaItems = new ArrayList<>();
        olahragaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                olahragaItems.clear();
                if (dataSnapshot.exists()){
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        OlahragaItem olahragaItem = ds.getValue(OlahragaItem.class);
                        Log.d(TAG, "onDataChange: "+ds.getValue());
                        olahragaItems.add(olahragaItem);
                    }
                    DaftarOlahragaPKAdapter daftarOlahragaPKAdapter = new DaftarOlahragaPKAdapter(DetailProgramKesehatan.this, olahragaItems);
                    daftarOlahragaRecycler.setAdapter(daftarOlahragaPKAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void polaMakanRef() {
        polaMakanList = new ArrayList<>();
        makanRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                polaMakanList.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    System.out.println("Value : "+ds.getValue());
                    PolaMakanData pmd = ds.getValue(PolaMakanData.class);
                    Log.d(TAG, "gambar "+pmd.getGambar()+"\njudul : "+pmd.getJudul()+"\nlink : "+pmd.getLink());
                    polaMakanList.add(pmd);
                }
                polaMakanSettingData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void polaMakanSettingData() {
        TextView judulMakanSiang = makanSiangView.findViewById(R.id.title_daftar);
        TextView judulMakanMalam = makanMalamView.findViewById(R.id.title_daftar);
        judulMakanSiang.setText("Makan Siang");
        judulMakanMalam.setText("Makan Malam");

        ImageView gambarSarapan = sarapanview.findViewById(R.id.image_daftar);
        ImageView gambarMakanSiang = makanSiangView.findViewById(R.id.image_daftar);
        ImageView gambarMakanMalam = makanMalamView.findViewById(R.id.image_daftar);

        Glide.with(this)
                .load(polaMakanList.get(2).getGambar())
                .centerCrop()
                .into(gambarSarapan);

        Glide.with(this)
                .load(polaMakanList.get(1).getGambar())
                .centerCrop()
                .into(gambarMakanSiang);
        Glide.with(this)
                .load(polaMakanList.get(0).getGambar())
                .centerCrop()
                .into(gambarMakanMalam);

        TextView makananPagi = sarapanview.findViewById(R.id.content_daftar);
        TextView makanSiang = makanSiangView.findViewById(R.id.content_daftar);
        TextView makanMalam = makanMalamView.findViewById(R.id.content_daftar);
        makananPagi.setText(polaMakanList.get(2).getJudul());
        makanSiang.setText(polaMakanList.get(1).getJudul());
        makanMalam.setText(polaMakanList.get(0).getJudul());

        sarapanview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri web = Uri.parse(polaMakanList.get(2).getLink());
                startActivity(new Intent(Intent.ACTION_VIEW, web));
            }
        });
        makanSiangView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri web = Uri.parse(polaMakanList.get(1).getLink());
                startActivity(new Intent(Intent.ACTION_VIEW, web));
            }
        });
        makanMalamView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri web = Uri.parse(polaMakanList.get(0).getLink());
                startActivity(new Intent(Intent.ACTION_VIEW, web));
            }
        });
    }

    private void settingText() {
        Glide.with(getApplicationContext())
                .load(gambarProgramIntent)
                .centerCrop()
                .into(gambarProgram);
        totalKalori.setText(kaloriProgramIntent);
        namaProgram.setText(namaProgramIntent);
        deskripsiProgram.setText(deskripsiProgramIntent);
    }

    private void retrieveIntent() {
        Intent intent = getIntent();
        idProgramIntent = intent.getStringExtra(ID_PROGRAM_KESEHATAN);
        gambarProgramIntent = intent.getStringExtra(GAMBAR_PROGRAM_KESEHATAN);
        kaloriProgramIntent = intent.getStringExtra(KALORI_PROGRAM_KESEHATAN);
        namaProgramIntent = intent.getStringExtra(NAMA_PROGRAM_KESEHATAN);
        deskripsiProgramIntent = intent.getStringExtra(DESKRIPSI_PROGRAM_KESEHATAN);
        olahragaRef = FirebaseDatabase.getInstance().getReference("daftar_olahraga_pk").child(idProgramIntent).child("hari");
        makanRef = FirebaseDatabase.getInstance().getReference("pola_makan").child(idProgramIntent);
    }
}