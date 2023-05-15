package com.fmu.lgbth.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.fmu.lgbth.R;


public class UserFragment extends Fragment {
    public UserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configListeners(view);
    }

    private void configListeners(View view) {
        Button saveUserButton = view.findViewById(R.id.persist_user);

        saveUserButton.setOnClickListener(buttonView -> {
            Intent intentTo = new Intent(getActivity(), RegisterUserActivity.class);
            startActivity(intentTo);
        });
    }
}