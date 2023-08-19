package com.humorstech.respyr.reading;

public class WorkoutSliderDataModel {

    private String title;
    private String description;
    private int image;

    public WorkoutSliderDataModel(String title, String description, int image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getImage() {
        return image;
    }
}
