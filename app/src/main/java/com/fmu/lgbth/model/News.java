package com.fmu.lgbth.model;

public class News {
    private final String description;
    private final String bannerName;

    public News(String description, String bannerName) {
        this.bannerName = bannerName;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getBannerName() {
        return bannerName;
    }
}
