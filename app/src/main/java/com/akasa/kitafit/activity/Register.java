package com.akasa.kitafit.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akasa.kitafit.R;
import com.akasa.kitafit.model.UserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    EditText mNama, mEmail, mPassword, mKonfirmPass;
    Button mRegistBtn;
    FirebaseAuth fauth;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String nama,email, password,konfirmpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mNama=findViewById(R.id.username_regis);
        mEmail=findViewById(R.id.email_regis);
        mPassword=findViewById(R.id.password_regis);
        mKonfirmPass=findViewById(R.id.konpassword_regis);
        mRegistBtn=findViewById(R.id.btn_register);

        fauth=FirebaseAuth.getInstance();
//
//        if(fauth.getCurrentUser()!=null){
//            startActivity(new Intent(getApplicationContext(), Homepage.class));
//            finish();
//        }

        mRegistBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = mNama.getText().toString().trim();
                email = mEmail.getText().toString().trim();
                password = mPassword.getText().toString().trim();
                konfirmpass = mKonfirmPass.getText().toString().trim();
                if (TextUtils.isEmpty(nama)) {
                    mNama.setError("Nama harus diisi!");
                    return;
                }
                if (TextUtils.isEmpty(email)) {
                    mEmail.setError("Email harus diisi!");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    mPassword.setError("Password harus diisi!");
                    return;
                }
                if (TextUtils.isEmpty(konfirmpass)) {
                    mKonfirmPass.setError("Konfirmasi Password harus diisi!");
                    return;
                }
                if (!konfirmpass.equals(password)) {
                    Toast.makeText(Register.this, "Password & KonfirmPass harus sama", Toast.LENGTH_SHORT).show();
                }

                fauth.createUserWithEmailAndPassword(email,konfirmpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            UploadData();
                            Toast.makeText(Register.this, "Akun user berhasil dbuat!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), Homepage.class));
                        }else {
                            Toast.makeText(Register.this, "Error!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        }

        private void UploadData(){
            UserData user= new UserData(nama,"","","","",email,password,"");
            myRef.child("user").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user);
        }
    }
