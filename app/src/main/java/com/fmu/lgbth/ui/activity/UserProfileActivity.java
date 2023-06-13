package com.fmu.lgbth.ui.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.VectorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.room.Room;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.UserApi;
import com.fmu.lgbth.utils.Base64Converter;
import com.google.android.material.imageview.ShapeableImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private final int GALLERY_REQ_CODE = 5432;
    private TextView birthDateButton;
    private DialogFragment datePicker;
    private User user;

    private Button cancelButton;
    private Button submitIssue;
    private ProgressBar loading;

    private ShapeableImageView shapeableImageView;
    private EditText userName;
    private EditText userEmail;
    private EditText userAge;
    private ImageView userAvatar;
    private TextView birthDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile);

        configUser();
        configListenners();

        try {
            bind();
        } catch (Exception e) {
            e.printStackTrace();
        }

        birthDateButton = findViewById(R.id.profile_activity_birthdate);

        birthDateButton.setOnClickListener(v -> {
            datePicker = new DatePickerFragment();
            datePicker.show(getSupportFragmentManager(), "date picker");
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                Uri imageUri = data.getData();
                String filePath = getPathFromUri(imageUri);

                if (null != filePath) {
                    File bitmapFile = new File(filePath);

                    long fileSizeInBytes = bitmapFile.length();
                    long fileSizeInKB = fileSizeInBytes / 1024;

                    if (fileSizeInKB >= 50) {
                        Toast.makeText(UserProfileActivity.this, "A imagem deve ser inferior a 50KB", Toast.LENGTH_SHORT).show();
                    } else {
                        shapeableImageView.setImageURI(data.getData());
                    }
                } else {
                    Toast.makeText(UserProfileActivity.this, "Ocorreu um erro desconhecido ao tentar anexar a imagem. Tente novamente.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private String getPathFromUri(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(columnIndex);
            cursor.close();
            return path;
        }
        return null;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String currentDateString = df.format(c.getTime());

        birthDateButton.setText(currentDateString);
        datePicker.dismiss();
    }

    private void bind() throws ParseException {
        userName = findViewById(R.id.profile_activity_name_input);
        userEmail = findViewById(R.id.profile_activity_email);
        userAge = findViewById(R.id.profile_activity_age);
        userAvatar = findViewById(R.id.profile_activity_person_image);
        birthDate = findViewById(R.id.profile_activity_birthdate);

        userName.setText(user.getName());
        userEmail.setText(user.getEmail());

        if (user.getAge() != null) {
            userAge.setText(Integer.toString(user.getAge()));
        }

        if (user.getAvatar() != null && !user.getAvatar().equals("")) {
            Bitmap decodeUserImage = Base64Converter.decodeImage(user.getAvatar());
            userAvatar.setImageBitmap(decodeUserImage);
        }

        Log.i("birth", user.getBirthDate() );
        if (user.getBirthDate() != null) {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = inputFormat.parse(user.getBirthDate());

            birthDate.setText(outputFormat.format(date));
        }

    }

    public void configListenners () {
        cancelButton = findViewById(R.id.profile_activity_cancel_issue);
        submitIssue = findViewById(R.id.profile_activity_submit_issue);
        loading = findViewById(R.id.profile_activity_loading);
        shapeableImageView = findViewById(R.id.profile_activity_person_image);

        submitIssue.setOnClickListener(v -> {
            postUser();
        });

        cancelButton.setOnClickListener(v -> {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
            finish();
        });

        shapeableImageView.setOnClickListener(view -> {
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        });


    }

    private void postUser () {
        try {
            cancelButton.setVisibility(View.INVISIBLE);
            submitIssue.setVisibility(View.INVISIBLE);
            loading.setVisibility(View.VISIBLE);

            if (!(shapeableImageView.getDrawable() instanceof VectorDrawable)) {
                Bitmap avatarBitmap = ((BitmapDrawable) shapeableImageView.getDrawable()).getBitmap();
                String base64EncodedImage = Base64Converter.encodedImage(avatarBitmap);
                user.setAvatar(base64EncodedImage);
            }

            user.setName(userName.getText().toString());
            user.setEmail(userEmail.getText().toString());

            if (!userAge.getText().toString().equals("")) {
                user.setAge(Integer.parseInt(userAge.getText().toString()));
            }

            if (!birthDate.getText().toString().equals("") ) {
                user.setBirthDate(birthDate.getText().toString());
            }

            UserApi client = new RestClient().getUserApi();
            Call<User> call = client.update(user);

            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    try {
                        User user = response.body();
                        persistUser(user);
                        Toast.makeText(UserProfileActivity.this,
                                        user.getName() + " atualizado com sucesso !", Toast.LENGTH_SHORT)
                                .show();

                        Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (NullPointerException e) {
                        cancelButton.setVisibility(View.VISIBLE);
                        submitIssue.setVisibility(View.VISIBLE);
                        loading.setVisibility(View.GONE);
                        e.printStackTrace();
                        Toast.makeText(UserProfileActivity.this,
                                "Ocorreu um erro ao tentar atualizar o usuário. Tente novamente mais tarde",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    cancelButton.setVisibility(View.VISIBLE);
                    submitIssue.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.INVISIBLE);
                    t.printStackTrace();
                    Toast.makeText(UserProfileActivity.this,
                            "Ocorreu um erro ao tentar atualizar o usuário. Tente novamente mais tarde",
                            Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            cancelButton.setVisibility(View.VISIBLE);
            submitIssue.setVisibility(View.VISIBLE);
            loading.setVisibility(View.INVISIBLE);
            e.printStackTrace();
            Toast.makeText(UserProfileActivity.this,
                    "Ocorreu um erro ao tentar atualizar o usuário. Tente novamente mais tarde",
                    Toast.LENGTH_SHORT).show();
        }
    }
    private void configUser() {
        UserDao dao = Room.databaseBuilder(this, Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        user = dao.get();
    }

    private void persistUser(User user) {
        Database db = Room.databaseBuilder(this, Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build();

        UserDao dao = db.getUserDao();
        dao.update(user.getId(), user.getName(), user.getEmail(), user.getAge(), user.getAvatar(), user.getBirthDate());
    }
}