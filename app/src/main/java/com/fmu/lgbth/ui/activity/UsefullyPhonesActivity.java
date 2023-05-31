package com.fmu.lgbth.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.UsefullyPhone;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.UsefullyPhones;
import com.fmu.lgbth.ui.adapter.UsefullyPhonesAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsefullyPhonesActivity extends AppCompatActivity {

    private ListView usefullyPhonesListView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_usefully_phones);

        usefullyPhonesListView = findViewById(R.id.phones_list_view);
        progressBar = findViewById(R.id.usefully_phones_unloaded_user_progress);

        getUsefullyPhones();
    }

    private void getUsefullyPhones() {
        try {
            UsefullyPhones usefullyPhonesApi = new RestClient().getUsefullyPhonesApi();
            Call<List<UsefullyPhone>> call = usefullyPhonesApi.get();

            call.enqueue(new Callback<List<UsefullyPhone>>() {
                @Override
                public void onResponse(Call<List<UsefullyPhone>> call, Response<List<UsefullyPhone>> response) {
                    List<UsefullyPhone> body = response.body();

                    if (null == body) {
                        Toast.makeText(UsefullyPhonesActivity.this, "Nenhum Telephones cadastrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    usefullyPhonesListView.setAdapter(new UsefullyPhonesAdapter(body, UsefullyPhonesActivity.this));
                    progressBar.setVisibility(View.GONE);
                    usefullyPhonesListView.setVisibility(View.VISIBLE);

                }

                @Override
                public void onFailure(Call<List<UsefullyPhone>> call, Throwable t) {
                    Toast.makeText(UsefullyPhonesActivity.this, "Ocorreu um erro desconhecido. Tente novamente em instantes.", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}