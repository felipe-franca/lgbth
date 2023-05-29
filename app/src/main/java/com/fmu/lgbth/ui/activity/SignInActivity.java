package com.fmu.lgbth.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.UserApi;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sign_in);

        configView();
    }

    private void configView() {
        Button signInButton = findViewById(R.id.register_button_issue);

        signInButton.setOnClickListener(bView -> {
            if (!checkFields ()) {

                return;
            }

            User user = new User("", email.getText().toString(), password.getText().toString());

            UserApi api = new RestClient().getUserApi();
            Call<User> call = api.signIn(user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();

                    if (null == user) {
                        Toast.makeText(SignInActivity.this, "Erro ao efetuar login", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    persistUser(user);
                    Toast.makeText(SignInActivity.this, "Bem vindo " + user.getName(), Toast.LENGTH_SHORT).show();
                    next();
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Toast.makeText(SignInActivity.this, t.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private Boolean checkFields () {
        email = findViewById(R.id.signin_email_input);
        password = findViewById(R.id.sigin_pwd_input);

        return !email.getText().toString().equals("") && !password.getText().toString().equals("");
    }

    private void persistUser(User user) {
        Database db = Room.databaseBuilder(this, Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build();

        UserDao dao = db.getUserDao();
        dao.delete();
        dao.persist(user);
    }

    private void next() {
        Intent homeIntent = new Intent(SignInActivity.this , MainActivity.class);
        startActivity(homeIntent);
        finish();
    }
}