package com.fmu.lgbth.dao;

import com.fmu.lgbth.model.News;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsDao {
    private final static ArrayList<News> newsList = new ArrayList<>();

    public List<News> getNews() {
        return (List<News>) newsList.clone();
    }

    public void add(News... news) {
        NewsDao.newsList.addAll(Arrays.asList(news));
    }

}
