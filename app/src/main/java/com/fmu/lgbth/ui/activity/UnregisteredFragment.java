package com.fmu.lgbth.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fmu.lgbth.R;

public class UnregisteredFragment extends Fragment {

    public UnregisteredFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_unregistered, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button registerButton = view.findViewById(R.id.register_button_issue);

        Button sigInButton = view.findViewById(R.id.login_button_issue);

        registerButton.setOnClickListener(buttonView -> {
            Intent intent = new Intent(getActivity(), RegisterUserActivity.class);
            startActivity(intent);
        });

        sigInButton.setOnClickListener(siginInButtonView -> {
            Intent sigInintent   = new Intent(getActivity(), SignInActivity.class);
            startActivity(sigInintent);
        });
    }
}