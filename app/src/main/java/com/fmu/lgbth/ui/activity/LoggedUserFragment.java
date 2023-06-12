package com.fmu.lgbth.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.utils.Base64Converter;
import com.google.android.material.imageview.ShapeableImageView;

public class LoggedUserFragment extends Fragment {
    public LoggedUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_logged_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configListeners(view);

        try {
            ProgressBar progressBar =  view.findViewById(R.id.register_activity_loading);
            User user = getPersistedUserIfExists();

            ShapeableImageView userImageView = view.findViewById(R.id.logged_user_image);
            TextView userName = view.findViewById(R.id.logged_user_name);
            TextView userAge = view.findViewById(R.id.logged_user_age);

            if (!user.getAvatar().equals("")) {
                Bitmap userBitmapedImage = Base64Converter.decodeImage(user.getAvatar());
                userImageView.setImageBitmap(userBitmapedImage);
            }

            userName.setText(user.getName());

            if (null != user.getAge()) {
                String age = user.getAge() + " Anos";
                userAge.setText(age);
            }

            progressBar.setVisibility(View.GONE);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User getPersistedUserIfExists() {
        UserDao dao = Room.databaseBuilder(getContext(), Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        return dao.get();
    }

    private void configListeners(View aView) {
        Button getOut = aView.findViewById(R.id.get_out);

        getOut.setOnClickListener(getOutView -> {
            removePersistedUser();

            Intent homeIntent = new Intent(getContext(), MainActivity.class);
            Toast.makeText(getContext(),"Esperamos você de volta !", Toast.LENGTH_SHORT).show();
            startActivity(homeIntent);
            getActivity().finish();
        });

        Button uPhones = aView.findViewById(R.id.usefully_phones);

        uPhones.setOnClickListener(phonesView -> {
            Intent usefullyPhonesIntent = new Intent(getContext(), UsefullyPhonesActivity.class);
            startActivity(usefullyPhonesIntent);
        });

        Button uProfile = aView.findViewById(R.id.logged_user_profile_config);

        uProfile.setOnClickListener(v -> {
            Intent userProfileIntent = new Intent(getContext(), UserProfileActivity.class);
            startActivity(userProfileIntent);
        });
    }

    private void removePersistedUser() {
        UserDao dao = Room.databaseBuilder(getContext(), Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        dao.delete();
    }
}