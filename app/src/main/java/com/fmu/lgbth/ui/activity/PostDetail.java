package com.fmu.lgbth.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.fmu.lgbth.R;

public class PostDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post_detail);

        int id = getIntent().getIntExtra("POSTID", 0);

        Log.i("post-id", Integer.toString(id));

    }
}