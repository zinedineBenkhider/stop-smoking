package com.stop.smoking.home.presenter.model;

public class RewardModel {
    private String name;
    private int price;
    private String status;
    private int percent;
    private String id;
    private boolean isBought;
    public RewardModel(String name, int price, String status, int percent, String id) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.percent = percent;
        this.id = id;
        this.isBought=false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        percent = percent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsBought() {
        return isBought;
    }

    public void setIsBought(Boolean isBought) {
        this.isBought = isBought;
    }
}
