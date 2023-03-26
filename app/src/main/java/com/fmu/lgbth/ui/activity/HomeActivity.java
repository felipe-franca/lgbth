package com.fmu.lgbth.ui.activity;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.News;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.ui.adapter.HomeNewsCardAdapter;
import com.fmu.lgbth.ui.adapter.HomePostsAdapter;
import com.fmu.lgbth.utils.Faker;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private HomeNewsCardAdapter adapter;
    private List<News> newsList;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        configNewsView();
        configPostView();
    }

    private void configNewsView() {
        RecyclerView newsListView = findViewById(R.id.home_news_recycler_list);

        newsList = Faker.getFakeNews();

        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        adapter = new HomeNewsCardAdapter(this, newsList);

        newsListView.setLayoutManager(lm);
        newsListView.setAdapter(adapter);
    }

    private void configPostView() {
        ListView postListView = findViewById(R.id.home_post_list_view);

        postList = Faker.getFakePosts();
        postListView.setAdapter(new HomePostsAdapter(this, postList));
    }
}