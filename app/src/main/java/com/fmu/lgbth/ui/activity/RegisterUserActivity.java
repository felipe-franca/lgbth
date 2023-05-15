package com.fmu.lgbth.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.UserApi;
import com.fmu.lgbth.utils.Base64Converter;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterUserActivity extends AppCompatActivity {

    private final int GALLERY_REQ_CODE = 5432;
    private ShapeableImageView imgFromGallery;
    private EditText name;
    private EditText email;
    private EditText password;

    private ProgressBar loading;
    private Button cancelIssueButton;
    private Button submitIssueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(com.fmu.lgbth.R.layout.activity_register_user);

        imgFromGallery = findViewById(R.id.register_activity_person_image);

        imgFromGallery.setOnClickListener(view -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });

        Button cancelButton = findViewById(R.id.register_activity_cancel_issue);
        Button saveButton = findViewById(R.id.register_activity_submit_issue);

        cancelButton.setOnClickListener(view -> {
            Intent homeIntent = new Intent(RegisterUserActivity.this, MainActivity.class);
            startActivity(homeIntent);
        });

        saveButton.setOnClickListener(view -> {
            loading = findViewById(R.id.register_activity_loading);
            submitIssueButton = findViewById(R.id.register_activity_submit_issue);
            cancelIssueButton = findViewById(R.id.register_activity_cancel_issue);

            cancelIssueButton.setVisibility(View.GONE);
            submitIssueButton.setVisibility(View.GONE);
            loading.setVisibility(View.VISIBLE);

            if (!checkFields()) {
                Toast.makeText(this, "Por Favor preencha todos os campos", Toast.LENGTH_SHORT).show();
                cancelIssueButton.setVisibility(View.VISIBLE);
                submitIssueButton.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                return;
            }

            if (!checkPassword()) {
                Toast.makeText(this, "As senhas não conferem !", Toast.LENGTH_SHORT).show();
                cancelIssueButton.setVisibility(View.VISIBLE);
                submitIssueButton.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                return;
            }

            User user = new User(
                    name.getText().toString(),
                    email.getText().toString(),
                    password.getText().toString());

            this.postUserData(user);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                imgFromGallery.setImageURI(data.getData());
            }
        }
    }

    private Boolean checkFields() {
        name = findViewById(R.id.register_activity_name_input);
        email = findViewById(R.id.register_activity_email);
        password = findViewById(R.id.register_activity_pwd);
        EditText passwordConfirm = findViewById(R.id.register_activity_pwd_confirmation);

        return !email.getText().toString().equals("")
                && !password.getText().toString().equals("")
                && !name.getText().toString().equals("")
                && !passwordConfirm.getText().toString().equals("");
    }

    private Boolean checkPassword() {
        EditText passwordConfirm = findViewById(R.id.register_activity_pwd_confirmation);
        return password.getText().toString().equals(passwordConfirm.getText().toString());
    }

    private void postUserData(User user) {
        ShapeableImageView avatarView = findViewById(R.id.register_activity_person_image);

        if (avatarView.getDrawable() instanceof VectorDrawable) {
            Toast.makeText(RegisterUserActivity.this, "Adicione uma imagem", Toast.LENGTH_SHORT).show();
            return;
        }

        Bitmap avatarBitmap = ((BitmapDrawable) avatarView.getDrawable()).getBitmap();
        String base64EncodedImage = Base64Converter.encodedImage(avatarBitmap);

        user.setAvatar(base64EncodedImage);
        UserApi client = new RestClient().getUserApi();
        Call<User> call = client.create(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    User user = response.body();
                    Toast.makeText(RegisterUserActivity.this,
                                    user.getName() + " cadastrado com sucesso !", Toast.LENGTH_SHORT)
                            .show();

                    Intent intent = new Intent(RegisterUserActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } catch (NullPointerException e) {
                    cancelIssueButton.setVisibility(View.VISIBLE);
                    submitIssueButton.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.GONE);
                    e.printStackTrace();
                    Toast.makeText(RegisterUserActivity.this,
                            "Ocorreu um erro ao tentar registar o usuário. Tente novamente mais tarde",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                cancelIssueButton.setVisibility(View.VISIBLE);
                submitIssueButton.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(RegisterUserActivity.this,
                        "Ocorreu um erro ao tentar registar o usuário. Tente novamente mais tarde",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}