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

public class Profile extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
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
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("user");
        mUsername = findViewById(R.id.userprofil_nama);
        firebaseAuth = FirebaseAuth.getInstance();
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