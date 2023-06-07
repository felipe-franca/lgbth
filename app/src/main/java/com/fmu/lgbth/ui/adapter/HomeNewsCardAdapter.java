package com.fmu.lgbth.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.utils.Base64Converter;

import java.util.List;

public class HomeNewsCardAdapter extends RecyclerView.Adapter<HomeNewsCardAdapter.ViewHolder> {
    private Context context;
    private List<Post> newsList;

    public HomeNewsCardAdapter(Context context, List<Post> newsList) {
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

        public void bind(Post aPost) {
            description.setText(aPost.getDescription());

            Bitmap decodeUserImage = Base64Converter.decodeImage(aPost.getBanner());
            bannerImage.setImageBitmap(decodeUserImage);
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
        Post aPost = newsList.get(position);
        holder.bind(aPost);
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }
}
