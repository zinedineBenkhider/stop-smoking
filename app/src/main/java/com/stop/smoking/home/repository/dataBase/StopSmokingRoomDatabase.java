package com.stop.smoking.home.repository.dataBase;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.stop.smoking.home.repository.dataBase.dao.ProfileDao;
import com.stop.smoking.home.repository.dataBase.dao.RewardDao;
import com.stop.smoking.home.repository.dataBase.entity.Profile;
import com.stop.smoking.home.repository.dataBase.entity.Reward;

@Database(entities = { Profile.class, Reward.class}, version = 3,exportSchema = false)
public abstract class StopSmokingRoomDatabase extends RoomDatabase {
    public abstract ProfileDao ProfileDao();
    public abstract RewardDao RewardDao();
    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile StopSmokingRoomDatabase INSTANCE;
    public static StopSmokingRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StopSmokingRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            StopSmokingRoomDatabase.class, "stop_smoking_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
