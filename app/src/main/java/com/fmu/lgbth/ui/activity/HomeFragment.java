package com.fmu.lgbth.ui.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.News;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.ui.adapter.HomeNewsCardAdapter;
import com.fmu.lgbth.ui.adapter.HomePostsAdapter;
import com.fmu.lgbth.utils.Faker;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private List<News> newsList;
    private List<Post> postList;
    private HomeNewsCardAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configNewsView(view);
        configPostView(view);
    }

    private void configNewsView(View view) {
        RecyclerView newsListView = view.findViewById(R.id.home_news_recycler_list);

        newsList = Faker.getFakeNews();

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.HORIZONTAL);

        adapter = new HomeNewsCardAdapter(getContext(), newsList);

        newsListView.setLayoutManager(lm);
        newsListView.setAdapter(adapter);

        configListeners(view);
    }

    private void configPostView(View view) {
        ListView postListView = view.findViewById(R.id.home_post_list_view);

        postList = Faker.getFakePosts();
        postListView.setAdapter(new HomePostsAdapter(getContext(), postList));
    }

    private void configListeners(View view) {
        TextView politics = view.findViewById(R.id.home_politics_button);

        politics.setOnClickListener(politicsView -> {
            Toast.makeText(getContext(), "Define what to do with politics", Toast.LENGTH_SHORT).show();

            return;
        });

        TextView works = view.findViewById(R.id.home_works_button);

        works.setOnClickListener(politicsView -> {
            Toast.makeText(getContext(), "Define what to do with works", Toast.LENGTH_SHORT).show();

            return;
        });

        TextView ong = view.findViewById(R.id.home_ong_button);

        ong.setOnClickListener(politicsView -> {
            Toast.makeText(getContext(), "Define what to do with ongs", Toast.LENGTH_SHORT).show();

            return;
        });

        TextView podcasts = view.findViewById(R.id.home_podcast_button);

        podcasts.setOnClickListener(politicsView -> {
            Toast.makeText(getContext(), "Define what to do with podcasts", Toast.LENGTH_SHORT).show();

            return;
        });
    }
}