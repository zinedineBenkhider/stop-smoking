package com.stop.smoking.home.presenter.model;

public class AcceptInReturnModel {
    private String duration;
    private String lostMoney;
    private String lifeSpanLost;

    public AcceptInReturnModel(String duration, String lostMoney, String lifeSpanLost) {
        this.duration = duration;
        this.lostMoney = lostMoney;
        this.lifeSpanLost = lifeSpanLost;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLostMoney() {
        return lostMoney;
    }

    public void setLostMoney(String lostMoney) {
        this.lostMoney = lostMoney;
    }

    public String getLifeSpanLost() {
        return lifeSpanLost;
    }

    public void setLifeSpanLost(String lifeSpanLost) {
        this.lifeSpanLost = lifeSpanLost;
    }
}
