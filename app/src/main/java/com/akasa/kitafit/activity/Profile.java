package com.akasa.kitafit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.akasa.kitafit.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
    }

    public void GoToEdit(View view) {
        Intent intent = new Intent(Profile.this, EditProfile.class);
        startActivity(intent);
    }
}