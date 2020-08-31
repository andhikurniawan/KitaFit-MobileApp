package com.akasa.kitafit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.akasa.kitafit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
    }

    public void Click1(View view) {
        EditText text1 = (EditText) findViewById(R.id.edit1);

//instansiasi database firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();

//Referensi database yang dituju
        DatabaseReference myRef = database.getReference().child("user");

//memberi nilai pada referensi yang dituju
        myRef.setValue(text1.getText().toString()); }
    }
