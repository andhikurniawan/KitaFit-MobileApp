package com.akasa.kitafit.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.akasa.kitafit.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        fauth = FirebaseAuth.getInstance();
        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {


                        Intent intent = new Intent(SplashScreen.this, Login.class);
                        startActivity(intent);
                    }



            }
        };
        timerThread.start();
    }


    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
