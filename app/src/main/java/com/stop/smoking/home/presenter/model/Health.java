package com.stop.smoking.home.presenter.model;

public class Health {
    private String title;
    private int percent;
    private String status;
    private String description;

    public Health(String title, int percent, String status, String description) {
        this.title = title;
        this.percent = percent;
        this.status = status;
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
