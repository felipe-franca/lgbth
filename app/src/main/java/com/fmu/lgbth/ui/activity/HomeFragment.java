package com.fmu.lgbth.ui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.fmu.lgbth.R;
import com.fmu.lgbth.dao.UserDao;
import com.fmu.lgbth.database.Database;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.model.RecyclerViewInterface;
import com.fmu.lgbth.model.User;
import com.fmu.lgbth.rest.RestClient;
import com.fmu.lgbth.rest.api.PostsApi;
import com.fmu.lgbth.ui.adapter.HomeNewsCardAdapter;
import com.fmu.lgbth.ui.adapter.HomePostsAdapter;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements RecyclerViewInterface {
    private HomeNewsCardAdapter recycleViewAdapter;
    private String currentFilter;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configNewsView(view);
        configPostView(view);
        configListeners(view);
    }

    private void configNewsView(View view) {
        try {
            RecyclerView newsListView = view.findViewById(R.id.home_news_recycler_list);

            PostsApi api = new RestClient().getPostApi();
            Call<List<Post>> call = api.getAll();

            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    List<Post> postList = response.body();

                    if (null == postList) {
                        Toast.makeText(getContext(), "Nenhum post encontrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    recycleViewAdapter = new HomeNewsCardAdapter(getContext(), postList, HomeFragment.this);
                    newsListView.setLayoutManager(lm);
                    newsListView.setAdapter(recycleViewAdapter);
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configPostView(View view) {
        try {
            ListView newsListView = view.findViewById(R.id.home_post_list_view);

            PostsApi api = new RestClient().getPostApi();
            Call<List<Post>> call = api.getNews();

            LinearLayoutManager lm = new LinearLayoutManager(getContext());
            lm.setOrientation(LinearLayoutManager.HORIZONTAL);

            call.enqueue(new Callback<List<Post>>() {
                @SuppressLint("ClickableViewAccessibility")
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    List<Post> postList = response.body();

                    if (null == postList) {
                        Toast.makeText(getContext(), "Nenhum post encontrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    HomePostsAdapter newsAdapter = new HomePostsAdapter(getContext(), postList);
                    newsListView.setAdapter(newsAdapter);

                    newsListView.setOnItemClickListener((adapterView, view, i, l) -> {
                        Intent intent = new Intent(getContext(), PostDetail.class);
                        intent.putExtra("POSTID", postList.get(i).getId());

                        startActivity(intent);

                        ImageView cv = view.findViewById(R.id.news_favorite_icon);
                        cv.setOnClickListener(v -> {
                            setFavorite(postList.get(i).getId());
                        });
                    });
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Ocorreu um erro ao carregar as 'News'", Toast.LENGTH_SHORT).show();
                }
            });

            configListeners(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void configListeners(View view) {
        TextView politics = view.findViewById(R.id.home_politics_button);

        politics.setOnClickListener(politicsView -> {
            resetFilterColor(view);

            if (null != currentFilter && !currentFilter.equals("") && currentFilter.equals("politics")) {
                configNewsByCategory(view,"");
                return;
            }

            Drawable gradientDrawable = getResources().getDrawable(R.drawable.selected_filter);
            politics.setBackground(gradientDrawable);
            politics.setTextColor(Color.BLACK);

            currentFilter = "politics";

            configNewsByCategory(view, currentFilter);
        });

        TextView works = view.findViewById(R.id.home_works_button);

        works.setOnClickListener(politicsView -> {
            resetFilterColor(view);

            if (null != currentFilter && !currentFilter.equals("") && currentFilter.equals("works")) {
                configNewsByCategory(view, "");
                return;
            }

            Drawable gradientDrawable = getResources().getDrawable(R.drawable.selected_filter);
            works.setBackground(gradientDrawable);
            works.setTextColor(Color.BLACK);

            currentFilter = "works";

            configNewsByCategory(view, currentFilter);
        });

        TextView ong = view.findViewById(R.id.home_ong_button);

        ong.setOnClickListener(politicsView -> {
            resetFilterColor(view);

            if (null != currentFilter && !currentFilter.equals("") && currentFilter.equals("ong")) {
                configNewsByCategory(view,"");
                return;
            }

            Drawable gradientDrawable = getResources().getDrawable(R.drawable.selected_filter);
            ong.setBackground(gradientDrawable);
            ong.setTextColor(Color.BLACK);

            currentFilter = "ong";

            configNewsByCategory(view, currentFilter);
        });

        TextView podcasts = view.findViewById(R.id.home_podcast_button);

        podcasts.setOnClickListener(politicsView -> {
            resetFilterColor(view);

            if (null != currentFilter && !currentFilter.equals("") && currentFilter.equals("podcasts")) {
                configNewsByCategory(view,"");
                return;
            }

            Drawable gradientDrawable = getResources().getDrawable(R.drawable.selected_filter);
            podcasts.setBackground(gradientDrawable);
            podcasts.setTextColor(Color.BLACK);

            currentFilter = "podcasts";

            configNewsByCategory(view, currentFilter);
        });
    }

    private void resetFilterColor(View view) {
        Drawable defaultBackground = getResources().getDrawable(R.drawable.home_options_background);

        TextView politics = view.findViewById(R.id.home_politics_button);
        politics.setBackground(defaultBackground);
        politics.setTextColor(Color.WHITE);

        TextView works = view.findViewById(R.id.home_works_button);
        works.setBackground(defaultBackground);
        works.setTextColor(Color.WHITE);

        TextView ong = view.findViewById(R.id.home_ong_button);
        ong.setBackground(defaultBackground);
        ong.setTextColor(Color.WHITE);

        TextView podcasts = view.findViewById(R.id.home_podcast_button);
        podcasts.setBackground(defaultBackground);
        podcasts.setTextColor(Color.WHITE);
    }

    private void setFavorite(int id) {
        try {
            User user = getUser();

            if (null == user) {
                Toast.makeText(getContext(), "Você precis estar logado para favoritar uma matéria.", Toast.LENGTH_SHORT).show();
                return;
            }

            PostsApi api = new RestClient().getPostApi();
            JsonObject json = new JsonObject();
            json.addProperty("userId", user.getId());
            json.addProperty("postId", id);
            Call<ResponseBody> call = api.postFavorite(json);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Toast.makeText(getContext(), "Favoritado com sucesso !", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(getContext(), "Erro ao favoritar Post !", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Erro ao favoritar Post !", Toast.LENGTH_SHORT).show();
        }
    }

    private User getUser() {
        UserDao dao = Room.databaseBuilder(getContext(), Database.class, "lgbt.db")
                .allowMainThreadQueries()
                .build().getUserDao();

        return dao.get();
    }

    @Override
    public void onItemClick(int position, View v, List<Post> newsList) {
        Log.i("myposition", Integer.toString(position));
        v.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), PostDetail.class);
            intent.putExtra("POSTID", newsList.get(position).getId());

            startActivity(intent);
        });

        ImageView iv = v.findViewById(R.id.post_favorite_icon);

        iv.setOnClickListener(view -> {
            setFavorite(newsList.get(position).getId());
        });
    }

    private void configNewsByCategory(View view, String category) {
        try {
            PostsApi api = new RestClient().getPostApi();
            Call<List<Post>> call = !category.equals("") ? api.getPostByCategory(category) : api.getAll();

            RecyclerView newsListView = view.findViewById(R.id.home_news_recycler_list);

            call.enqueue(new Callback<List<Post>>() {
                @Override
                public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {
                    List<Post> postList = response.body();

                    if (null == postList) {
                        Toast.makeText(getContext(), "Nenhum post encontrado", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    recycleViewAdapter = new HomeNewsCardAdapter(getContext(), postList, HomeFragment.this);
                    newsListView.setAdapter(recycleViewAdapter);
                }

                @Override
                public void onFailure(Call<List<Post>> call, Throwable t) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}