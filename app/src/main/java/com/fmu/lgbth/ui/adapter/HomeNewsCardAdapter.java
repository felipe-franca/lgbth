package com.fmu.lgbth.ui.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.model.RecyclerViewInterface;
import com.fmu.lgbth.utils.Base64Converter;

import java.util.List;

public class HomeNewsCardAdapter extends RecyclerView.Adapter<HomeNewsCardAdapter.ViewHolder> {
    private final RecyclerViewInterface recyclerViewInterface;
    private Context context;
    private List<Post> newsList;

    public HomeNewsCardAdapter(Context context, List<Post> newsList, RecyclerViewInterface recyclerViewInterface) {
        this.context = context;
        this.newsList = newsList;
        this.recyclerViewInterface = recyclerViewInterface;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView description;
        private final ImageView bannerImage;

        public ViewHolder(@NonNull View itemView, RecyclerViewInterface recyclerViewInterface, List<Post> newsList) {
            super(itemView);

            description = itemView.findViewById(R.id.main_news_card_description);
            bannerImage = itemView.findViewById(R.id.main_news_card_banner);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != recyclerViewInterface) {
                        int position = getAbsoluteAdapterPosition();

                        if (position != RecyclerView.NO_POSITION) {
                            recyclerViewInterface.onItemClick(position, v, newsList);
                        }
                    }
                }
            });
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
        return new ViewHolder(view, recyclerViewInterface, newsList);
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

    public long getItemId (int position) {
        return newsList.get(position).getId();
    }

    public void updatePosts(List<Post> newPostsList) {
        this.newsList = newPostsList;
        notifyDataSetChanged();
    }
}
