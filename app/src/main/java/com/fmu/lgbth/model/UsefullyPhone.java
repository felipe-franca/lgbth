package com.fmu.lgbth.model;

public class UsefullyPhone {
    private final String title;
    private final String description;
    private final String number;

    public UsefullyPhone(String title, String description, String number) {
        this.number = number;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getNumber() {
        return number;
    }
}
