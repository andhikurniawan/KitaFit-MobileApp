package com.akasa.kitafit.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.PostLiniMasa;
import com.akasa.kitafit.adapter.LiniMasaAdapter;
import com.akasa.kitafit.model.LiniMasaData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class LiniMasaFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    FloatingActionButton floatingActionButton;
    DatabaseReference ref;
    ArrayList<LiniMasaData> list;
    ProgressBar progressBar;
    public LiniMasaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lini_masa, container, false);
        recyclerView = view.findViewById(R.id.linimasa_recycler);
        context = getContext();
        floatingActionButton = view.findViewById(R.id.linimasa_fab);
        progressBar = view.findViewById(R.id.progressbarlingkaran);
        progressBar.setVisibility(View.VISIBLE);
        ref = FirebaseDatabase.getInstance().getReference("lini_masa").child("id_post");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        readData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostLiniMasa.class));
            }
        });
        return view;
    }

    private void readData() {
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list = new ArrayList<>();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        LiniMasaData liniMasaData = ds.getValue(LiniMasaData.class);
                        list.add(liniMasaData);
                    }
                    LiniMasaAdapter liniMasaAdapter = new LiniMasaAdapter(context, list);
                    recyclerView.setAdapter(liniMasaAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}