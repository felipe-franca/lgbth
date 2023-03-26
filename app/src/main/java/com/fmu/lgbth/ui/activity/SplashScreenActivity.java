package com.fmu.lgbth.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.fmu.lgbth.R.layout.activity_splash_screen);
        getSupportActionBar().hide();

        Handler handler = new Handler();
        handler.postDelayed(() -> {
            Intent homeActivity = new Intent(this, MainActivity.class);
            startActivity(homeActivity);
            finish();
        }, 2500);
    }
}