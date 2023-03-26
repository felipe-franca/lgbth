package com.fmu.lgbth.dao;

import com.fmu.lgbth.model.News;
import com.fmu.lgbth.model.Post;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostDao {
    private final static ArrayList<Post> postList = new ArrayList<>();

    public List<Post> getPosts() {
        return (List<Post>) postList.clone();
    }

    public void add(Post... posts) {
        PostDao.postList.addAll(Arrays.asList(posts));
    }

}
