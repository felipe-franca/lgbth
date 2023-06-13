package com.fmu.lgbth.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.PostsApi;
import com.fmu.lgbth.utils.Base64Converter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetail extends AppCompatActivity {

    private TextView title;
    private ImageView banner;
    private TextView resume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post_detail);

        int id = getIntent().getIntExtra("POSTID", 0);

        setPostData(id);
    }

    public void setPostData (int id) {
        PostsApi api = new RestClient().getPostApi();
        Call<Post> call = api.getPostById(id);

        LinearLayoutManager lm = new LinearLayoutManager(PostDetail.this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Post post = response.body();

                if (null == post) {
                    Toast.makeText(PostDetail.this, "Nenhum post encontrado", Toast.LENGTH_SHORT).show();
                    return;
                }

                bind(post);

                Button keepReeding = findViewById(R.id.post_detail_keep_reeding);

                keepReeding.setOnClickListener(v -> {
                    String url = post.getUrl();
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(browserIntent);
                });
                ProgressBar progressBar = findViewById(R.id.post_detail_progress_bar);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(PostDetail.this, "Ocorreu um erro ao carregar os Post!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bind (Post post) {
        title = findViewById(R.id.post_detail_title_edit_text);
        banner = findViewById(R.id.post_detail_image_view);
        resume = findViewById(R.id.post_detail_resume_text_view);

        title.setText(post.getTitle());
        resume.setText(post.getResume());

        Bitmap decodeUserImage = Base64Converter.decodeImage(post.getBanner());
        banner.setImageBitmap(decodeUserImage);
    }
}