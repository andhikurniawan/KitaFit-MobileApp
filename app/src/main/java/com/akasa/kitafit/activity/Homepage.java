package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.akasa.kitafit.R;
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

public class Homepage extends AppCompatActivity {
TextView mUsername, mUmur;
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    String UID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        mUsername = findViewById(R.id.username);
        mUmur = findViewById(R.id.umur_user);
        final CircularImageView profil = findViewById(R.id.user);
        user = FirebaseAuth.getInstance().getCurrentUser();
        UID=user.getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");

        databaseReference.child(UID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usermodel user = dataSnapshot.getValue(usermodel.class);
                mUsername.setText(user.getNama_user());
                mUmur.setText(user.getUmur());
                Picasso.get().load(user.getFoto_user()).into(profil);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Homepage.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void GoToProfile(View view) {
        Intent intent = new Intent(Homepage.this, Profile.class);
        startActivity(intent);
    }

    public void GoToReminder(View view) {
        Intent intent = new Intent(Homepage.this, Reminderku.class);
        startActivity(intent);
    }
}