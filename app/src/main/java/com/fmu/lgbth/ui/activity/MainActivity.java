package com.fmu.lgbth.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.databinding.ActivityMainBinding;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.utils.Base64Converter;
import com.google.android.material.imageview.ShapeableImageView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ShapeableImageView userImage;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        getSupportActionBar().hide();

        setContentView(binding.getRoot());
        replaceFragment(new HomeFragment());

        userImage = findViewById(R.id.logged_user_logo_main_activity_image);

        configUserImage();

        binding.bottomNavigationMenu.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.user)
                userImage.setVisibility(View.GONE);
            else
                userImage.setVisibility(View.VISIBLE);

            switch (item.getItemId()) {
                case R.id.home:
                    replaceFragment(new HomeFragment());
                    break;
                case R.id.favorite:
                    replaceFragment(new FavoriteFragment());
                    break;
                case R.id.user:
                    replaceFragment(new UserFragment());
                    break;
            }

            return true;
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }

    private void configUserImage() {
        UserDao dao = Room.databaseBuilder(this, Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        User user = dao.get();

        if (null != user) {
            if (!user.getAvatar().equals("")) {
                Bitmap decodeUserImage = Base64Converter.decodeImage(user.getAvatar());
                userImage.setImageBitmap(decodeUserImage);
            }
        }
    }
}