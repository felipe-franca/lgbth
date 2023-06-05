package com.fmu.lgbth.utils;

import com.fmu.lgbth.dao.NewsDao;
import com.fmu.lgbth.dao.PostDao;
import com.fmu.lgbth.model.News;
import com.fmu.lgbth.model.Post;

import java.util.List;

public class Faker {
    public static List<News> getFakeNews() {
        NewsDao dao = new NewsDao();

        for (int i = 1; i <= 3; i++)  {
            News aNews = new News(
                    "Some description for testing " + i,
                    "https://observatoriog.bol.uol.com.br/wordpress/wp-content/uploads/2019/11/kit-2-bandeiras-gls-gay-lgbt-arco-iris-150m-x-090m-D_NQ_NP_819487-MLB28709953300_112018-F-1.jpg"
            );

            dao.add(aNews);
        }

        return dao.getNews();
    }

//    public static List<Post> getFakePosts() {
//        PostDao dao = new PostDao();
//
//        for (int i = 0; i < 1; i++) {
//            Post aPost = new Post(
//                    "Title" + i,
//                    "This is a simple resume " + i,
//                    "This is a simple resume " + i,
//                    "This is a description " + i,
//                    "https://observatoriog.bol.uol.com.br/wordpress/wp-content/uploads/2019/11/kit-2-bandeiras-gls-gay-lgbt-arco-iris-150m-x-090m-D_NQ_NP_819487-MLB28709953300_112018-F-1.jpg",
//                    "post");
//
//            dao.add(aPost);
//        }
//
//        return dao.getPosts();
//    }
}
