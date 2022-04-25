package com.stop.smoking.home.presenter.model;

public class Reward {
    private String name;
    private int percent;
    private String status;
    private int price;

    public Reward(String name, int percent, String status, int price) {
        this.name = name;
        this.percent = percent;
        this.status = status;
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
