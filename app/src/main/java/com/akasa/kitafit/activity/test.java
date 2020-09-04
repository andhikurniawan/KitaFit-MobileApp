package com.akasa.kitafit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.akasa.kitafit.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class test extends AppCompatActivity {

    EditText editText;
    Button buttonEdt;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        editText = findViewById(R.id.edit1);
        buttonEdt = findViewById(R.id.btn1);
        reference = FirebaseDatabase.getInstance().getReference().child("test_andhi");

        buttonEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reference.setValue(editText.getText().toString());
            }
        });
    }
}
