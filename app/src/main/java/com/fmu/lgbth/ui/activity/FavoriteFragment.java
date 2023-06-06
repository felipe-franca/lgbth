package com.fmu.lgbth.ui.activity;

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
import android.widget.ListView;
import android.widget.ProgressBar;
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

import java.util.List;

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
            PostsApi api = new RestClient().getPostApi();
            Call<List<Post>> call = api.getFavoritePosts(user.getId());

//            LinearLayoutManager lm = new LinearLayoutManager(getContext());
//            lm.setOrientation(LinearLayoutManager.HORIZONTAL);

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    List<Post> postList = response.body();

                    if (null == postList) {
                        Toast.makeText(getContext(), "Nenhum post encontrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    HomePostsAdapter newsAdapter = new HomePostsAdapter(getContext(), postList);
                    newsListView.setAdapter(newsAdapter);

                    ProgressBar loading = view.findViewById(R.id.favorites_progress_bar);
                    loading.setVisibility(View.GONE);
                    newsListView.setVisibility(View.VISIBLE);
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Ocorreu um erro ao carregar as 'News'", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private User getUser() {
        UserDao dao = Room.databaseBuilder(getContext(), Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        return dao.get();
    }
}