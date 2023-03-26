package com.fmu.lgbth.model;

public class Post {
    private final String title;
    private final String resume;
    private final String description;
    private final String footer;
    private final String imageResource;
    private final String type;

    public Post(String title, String resume, String description, String footer, String imageResource, String type) {
        this.title = title;
        this.resume = resume;
        this.description = description;
        this.footer = footer;
        this.imageResource = imageResource;
        this.type = type;
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

    public String getFooter() {
        return footer;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getType() {
        return type;
    }
}
