package com.akasa.kitafit.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.activity.PostLiniMasa;
import com.akasa.kitafit.activity.Reminderku;
import com.akasa.kitafit.adapter.LiniMasaAdapter;
import com.akasa.kitafit.model.LiniMasaData;
import com.akasa.kitafit.model.UserData;
import com.akasa.kitafit.model.usermodel;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;


public class LiniMasaFragment extends Fragment {

    RecyclerView recyclerView;
    Context context;
    FloatingActionButton floatingActionButton;
    DatabaseReference ref, userRef;
    ArrayList<LiniMasaData> list;
    ProgressBar progressBar;
    CircularImageView userImage;
    TextView userName, userAge;
    ImageView reminder;
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
        userImage = view.findViewById(R.id.user);
        userName = view.findViewById(R.id.namauser);
        userAge = view.findViewById(R.id.umur);
        reminder = view.findViewById(R.id.reminder_icon);
        progressBar = view.findViewById(R.id.progressbarlingkaran);
        progressBar.setVisibility(View.VISIBLE);
        ref = FirebaseDatabase.getInstance().getReference("lini_masa").child("id_post");
        userRef = FirebaseDatabase.getInstance().getReference("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        LinearLayoutManager linearLayoutManager =  new LinearLayoutManager(context, RecyclerView.VERTICAL, true);
        recyclerView.setLayoutManager(linearLayoutManager);
        readData();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), PostLiniMasa.class));
            }
        });
        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, Reminderku.class));
            }
        });
        return view;
    }

    private void readData() {
        list = new ArrayList<>();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    list.clear();
                    for (DataSnapshot ds : dataSnapshot.getChildren()){
                        System.out.println("Value : "+ds.getValue());
                        LiniMasaData liniMasaData = ds.getValue(LiniMasaData.class);
                        list.add(liniMasaData);
                    }
                    LiniMasaAdapter liniMasaAdapter = new LiniMasaAdapter(context, list);
                    liniMasaAdapter.setHasStableIds(true);
                    recyclerView.setAdapter(liniMasaAdapter);
                    progressBar.setVisibility(View.GONE);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(context, "Yuk Tambahkan Aktivitasmu ke Lini Masa!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel usermodel = dataSnapshot.getValue(com.akasa.kitafit.model.usermodel.class);
                if (usermodel.getFoto_user() != null){
                    Glide.with(context)
                            .load(usermodel.getFoto_user())
                            .centerCrop()
                            .into(userImage);
                } else {
                    Glide.with(context)
                            .load(R.drawable.avatar_placeholder)
                            .centerCrop()
                            .into(userImage);
                }
                userName.setText(usermodel.getNama_user());
                if (usermodel.getUmur() != null){
                    userAge.setText(usermodel.getUmur() + " tahun");
                } else {
                    userAge.setText("Silakan set Umur pada menu Edit Profil");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}