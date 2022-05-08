package com.stop.smoking.home.repository.dataBase.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity(tableName = "reward_table")
public class Reward {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "id")
    public String id;
    private String name;
    private int price;
    private boolean isBought;

    public Reward(@NonNull String id,String name, int price, boolean isBought) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isBought = isBought;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }
}
