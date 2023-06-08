package com.fmu.lgbth.model;

import android.view.View;

import java.util.List;

public interface RecyclerViewInterface {
    void onItemClick (int position, View view, List<Post> newsList);
}
