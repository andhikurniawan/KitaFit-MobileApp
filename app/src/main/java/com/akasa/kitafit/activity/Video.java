package com.akasa.kitafit.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.member;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Video extends AppCompatActivity {

    RecyclerView Mrecyclerview;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseRecyclerOptions<member> options;
    FirebaseRecyclerAdapter<member, ViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Mrecyclerview = findViewById(R.id.recyclerview_video);
        Mrecyclerview.setHasFixedSize(true);
        Mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("video");
    }


    @Override
    protected void onStart () {
        super.onStart();

        options =  new FirebaseRecyclerOptions.Builder<member>()
                .setQuery(reference, member.class).build();

        adapter = new FirebaseRecyclerAdapter<member, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int i, @NonNull member model) {
                holder.setVideo(getApplication(), model.getTitle(), model.getUrl());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
                return new ViewHolder(view);
            }


        };

        adapter.startListening();
        Mrecyclerview.setAdapter(adapter);


    }



}
