package com.fmu.lgbth.model;

public class Post {
    private final Integer id;

    private final String banner;

    private final String title;

    private final String resume;

    private final String description;

    private final String shortDescription;

    private final String type;

    private final String category;

    private final String url;

    private final String createdAt;

    public Post(
            Integer id,
            String banner,
            String title,
            String resume,
            String description,
            String type,
            String category,
            String url,
            String createdAt,
            String shortDescription
    ) {
        this.banner = banner;
        this.title = title;
        this.resume = resume;
        this.id = id;
        this.description = description;
        this.type = type;
        this.category = category;
        this.url = url;
        this.createdAt = createdAt;
        this.shortDescription = shortDescription;
    }

    public Integer getId() {
        return id;
    }

    public String getBanner() {
        return banner;
    }

    public String getTitle() {
        return title;
    }

    public String getResume() {
        return resume;
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getUrl() {
        return url;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
