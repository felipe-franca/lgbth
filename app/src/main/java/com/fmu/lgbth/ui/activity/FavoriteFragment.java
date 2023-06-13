package com.fmu.lgbth.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.PostsApi;
import com.fmu.lgbth.ui.adapter.HomePostsAdapter;
import com.fmu.lgbth.utils.Base64Converter;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteFragment extends Fragment {
    public FavoriteFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configPosts(view);
    }

    private void configPosts(View view) {
        try {
            ListView newsListView = view.findViewById(R.id.favorite_post_card);

            User user = getUser();

            if (null == user) {
                ProgressBar progressBar = view.findViewById(R.id.favorites_progress_bar);
                progressBar.setVisibility(View.GONE);

                TextView nonLoggedUser = view.findViewById(R.id.non_user_logged);
                nonLoggedUser.setVisibility(View.VISIBLE);
            } else {
                PostsApi api = new RestClient().getPostApi();
                Call<List<Post>> call = api.getFavoritePosts(user.getId());

                call.enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                        List<Post> postList = response.body();

                        if (null == postList || postList.size() < 1) {
                            Toast.makeText(getContext(), "Nenhum post encontrado", Toast.LENGTH_SHORT).show();

                            ProgressBar progressBar = view.findViewById(R.id.favorites_progress_bar);
                            progressBar.setVisibility(View.GONE);

                            TextView nonUserFavorite = view.findViewById(R.id.non_favorites_existing);
                            nonUserFavorite.setVisibility(View.VISIBLE);

                            return;
                        }

                        HomePostsAdapter newsAdapter = new HomePostsAdapter(getContext(), postList);
                        newsListView.setAdapter(newsAdapter);

                        ProgressBar loading = view.findViewById(R.id.favorites_progress_bar);
                        loading.setVisibility(View.GONE);
                        newsListView.setVisibility(View.VISIBLE);

                        newsListView.setOnItemClickListener((adapterView, view, i, l) -> {
                            Intent intent = new Intent(getContext(), PostDetail.class);
                            intent.putExtra("POSTID", postList.get(i).getId());

                            startActivity(intent);

                            ImageView cv = view.findViewById(R.id.news_favorite_icon);
                            cv.setOnClickListener(v -> {
                                unsetFavorite(postList.get(i).getId());
                                newsAdapter.delete(i);
                            });
                        });
                    }

                    @Override
                    public void onFailure(Call<List<Post>> call, Throwable t) {
                        t.printStackTrace();
                        Toast.makeText(getContext(), "Ocorreu um erro ao carregar as 'News'", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void unsetFavorite (int id) {
        try {
            User user = getUser();

            PostsApi api = new RestClient().getPostApi();
            JsonObject json = new JsonObject();
            json.addProperty("userId", user.getId());
            json.addProperty("postId", id);
            Call<ResponseBody> call = api.unfavoritePost(json);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "Post desfavoritado !", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Erro ao desfavoritar Post !", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Erro ao desfavoritar Post !", Toast.LENGTH_SHORT).show();
        }
    }

    private User getUser() {
        UserDao dao = Room.databaseBuilder(getContext(), Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        return dao.get();
    }
}