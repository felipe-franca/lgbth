package com.fmu.lgbth.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.model.User;


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

        try {
            User user = getPersistedUserIfExists();
            if (user == null) {
                replaceFragment(new UnregisteredFragment());
            } else {
                 replaceFragment(new LoggedUserFragment());
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

        fragmentTransaction.replace(R.id.user_fragment_parent, fragment);
        fragmentTransaction.commit();
    }

    private User getPersistedUserIfExists() {
        UserDao dao = Room.databaseBuilder(getContext(), Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        return dao.get();
    }
}