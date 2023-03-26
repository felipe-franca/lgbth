package com.fmu.lgbth.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.News;
import com.fmu.lgbth.utils.ImageFetcher;

import java.util.List;

public class HomeNewsCardAdapter extends RecyclerView.Adapter<HomeNewsCardAdapter.ViewHolder> {
    private Context context;
    private List<News> newsList;

    public HomeNewsCardAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final ImageView bannerImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            description = itemView.findViewById(R.id.main_news_card_description);
            bannerImage = itemView.findViewById(R.id.main_news_card_banner);
        }

        public void bind(News aNews) {
            description.setText(aNews.getDescription());
            Log.i("URL", aNews.getBannerName());
            new ImageFetcher((ImageView) bannerImage).execute(aNews.getBannerName());
        }
    }

    @NonNull
    @Override
    public HomeNewsCardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_card_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        News aNews = newsList.get(position);
        holder.bind(aNews);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
