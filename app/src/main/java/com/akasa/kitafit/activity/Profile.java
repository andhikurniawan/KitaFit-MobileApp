package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.adapter.HistoryAdapter;
import com.akasa.kitafit.model.ProgramKesehatanData;
import com.akasa.kitafit.model.usermodel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    RecyclerView mRecyclerView;
    LayoutInflater inflater;
    View dialogView;
    private List<ProgramKesehatanData> listData;
    HistoryAdapter adapter;
    String UID;
    TextView mUsername;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        user = FirebaseAuth.getInstance().getCurrentUser();
        UID=user.getUid();
        final CircularImageView profil = findViewById(R.id.imgprofil_user);
        mRecyclerView= findViewById(R.id.recyclerView_history);
        mUsername = findViewById(R.id.userprofil_nama);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        showListData();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        DatabaseReference databaseReference = firebaseDatabase.getReference("user");
        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                mUsername.setText(user.getNama_user());
                Picasso.get().load(user.getFoto_user()).into(profil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showListData(){
        DatabaseReference  mRef = FirebaseDatabase.getInstance().getReference("program_kesehatan");
        final DatabaseReference pkref = FirebaseDatabase.getInstance().getReference("history_program");
    mRef.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        if(dataSnapshot.exists()){
            listData= new ArrayList<>();
            for(final DataSnapshot ds : dataSnapshot.getChildren()){
                pkref.child("myRZNQVtrqXe8qH9ML98IvLgd8J3").child("id_program_kesehatan").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot ds2 : dataSnapshot.getChildren()){
                            if(ds.child("id_program_kesehatan").getValue().toString().equals(ds2.getKey())){
                                System.out.println("Value "+ds.getValue());
                                ProgramKesehatanData pk =ds.getValue(ProgramKesehatanData.class);
                                listData.add(pk);
                            }
                        }
                        adapter = new HistoryAdapter(Profile.this,listData);
                        mRecyclerView.setAdapter(adapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
    }

    public void GoToEdit(View view) {
        Intent intent = new Intent(Profile.this, EditProfile.class);
        startActivity(intent);
    }

    public void Logout(View view) {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(Profile.this, Login.class));
    }
}