package com.stop.smoking.home.repository.dataBase.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.stop.smoking.home.repository.dataBase.entity.Reward;

import java.util.List;

@Dao
public interface RewardDao {
    @Insert
    void insertReward(Reward reward);

    @Query("SELECT * FROM reward_table")
    List<Reward> getRewards();

    @Update
    void update(Reward... reward);

    @Delete
    void delete(Reward reward);

    @Query("SELECT * FROM reward_table where id=:rewardId")
    Reward getRewardById(String rewardId);

    @Delete
    void deleteReward(Reward reward);
}
