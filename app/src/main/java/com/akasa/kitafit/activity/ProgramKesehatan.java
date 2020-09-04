package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.adapter.ProgramKesehatanAdapter;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProgramKesehatan extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageView backButton;
    ProgressBar progressBar;
    DatabaseReference ref;
    Context context;
    ArrayList<ProgramKesehatanData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_kesehatan);
        backButton = findViewById(R.id.back_button);
        recyclerView = findViewById(R.id.program_kesehatan_recycler);
        progressBar = findViewById(R.id.progressbarlingkaran);
        context = getApplicationContext();
        ref = FirebaseDatabase.getInstance().getReference("program_kesehatan");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        readData();
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void readData() {
        list = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        ProgramKesehatanData pkd = ds.getValue(ProgramKesehatanData.class);
                        list.add(pkd);
                    }
                    ProgramKesehatanAdapter programKesehatanAdapter = new ProgramKesehatanAdapter(context, list);
                    recyclerView.setAdapter(programKesehatanAdapter);
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    Toast.makeText(context, "Program Kesehatan Sedang Kosong~", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}