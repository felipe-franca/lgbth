package com.fmu.lgbth.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fmu.lgbth.R;
import com.fmu.lgbth.model.Post;
import com.fmu.lgbth.utils.ImageFetcher;

import java.util.List;

public class HomePostsAdapter extends BaseAdapter {
    private final List<Post> postList;
    private final Context context;

    public HomePostsAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_card, parent, false);
        Post aPost = postList.get(position);

        bind(view, aPost);

        return view;
    }

    private void bind(View view, Post post) {
        TextView title = view.findViewById(R.id.home_post_card_title);
        TextView resume = view.findViewById(R.id.home_post_resume);
        ImageView banner = view.findViewById(R.id.home_post_banner);
        TextView description = view.findViewById(R.id.home_post_description);
        TextView footer = view.findViewById(R.id.home_post_footer);

        title.setText(post.getTitle());
        resume.setText(post.getResume());
        description.setText(post.getDescription());
        footer.setText(post.getFooter());

        new ImageFetcher((ImageView) banner).execute(post.getImageResource());
    }
}
