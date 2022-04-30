package com.stop.smoking.home.repository.dataBase.dao;

import androidx.room.*;

import com.stop.smoking.home.repository.dataBase.entity.Profile;

import java.util.List;

@Dao
public interface ProfileDao {
    @Insert
    void insertProfile(Profile profile);

    @Query("SELECT * FROM profile_table")
    List<Profile> getProfiles();

    @Update
    void update(Profile... profile);

    @Delete
    void delete(Profile profile);
}
