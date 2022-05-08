package com.stop.smoking.home.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.stop.smoking.home.repository.dataBase.StopSmokingRoomDatabase;
import com.stop.smoking.home.repository.dataBase.dao.RewardDao;
import com.stop.smoking.home.repository.dataBase.entity.Reward;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class RewardRepository {
    private RewardDao mRewardDao;
    private StopSmokingRoomDatabase db;
    private static RewardRepository INSTANCE =null;

    public RewardRepository(Application application) {
        db = StopSmokingRoomDatabase.getDatabase(application);
        mRewardDao=db.RewardDao();
    }

    public static RewardRepository getInstance(Application application){
        if (INSTANCE==null){
            INSTANCE=new RewardRepository(application);
        }
        return INSTANCE;
    }

    public void insertReward(Reward reward) {
        new RewardRepository.insertRewardAsyncTask(mRewardDao).execute(reward);
    }

    public void updateReward(Reward reward){
        new RewardRepository.UpdateRewardAsyncTask(mRewardDao).execute(reward);
    }

    public void deleteReward(Reward reward){
        new RewardRepository.DeleteRewardAsyncTask(mRewardDao).execute(reward);
    }


    public Reward getRewardById(String rewardId){
        try {
            return new getRewardByIdAsyncTask(mRewardDao).execute(rewardId).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Reward> getRewards(){
        try {
            List<Reward> rewards= new GetRewardAsyncTask(mRewardDao).execute().get();
            return rewards;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private static class insertRewardAsyncTask extends AsyncTask<Reward, Void, Void> {
        private RewardDao mAsyncTaskDaoReward;
        insertRewardAsyncTask (RewardDao noteDao) {
            mAsyncTaskDaoReward=noteDao;
        }
        @Override
        protected Void doInBackground(final Reward... params) {
            mAsyncTaskDaoReward.insertReward(params[0]);
            return null;
        }
    }

    private static class GetRewardAsyncTask extends AsyncTask<String, Void, List<Reward>> {
        private RewardDao mAsyncTaskDaoReward;
        GetRewardAsyncTask (RewardDao noteDao) {
            mAsyncTaskDaoReward=noteDao;
        }
        @Override
        protected List<Reward> doInBackground(final String... params) {
            return mAsyncTaskDaoReward.getRewards();
        }
    }

    private static class UpdateRewardAsyncTask extends AsyncTask<Reward,Void,Void> {
        private RewardDao mAsyncTaskDaoReward;
        UpdateRewardAsyncTask (RewardDao  dao) {
            mAsyncTaskDaoReward = dao;
        }
        @Override
        protected Void doInBackground(final Reward... params) {
            mAsyncTaskDaoReward.update(params[0]);
            return null;
        }
    }

    private static class DeleteRewardAsyncTask extends AsyncTask<Reward,Void,Void> {
        private RewardDao mAsyncTaskDaoReward;
        DeleteRewardAsyncTask (RewardDao  dao) {
            mAsyncTaskDaoReward = dao;
        }
        @Override
        protected Void doInBackground(final Reward... params) {
            mAsyncTaskDaoReward.delete(params[0]);
            return null;
        }
    }



    private static class getRewardByIdAsyncTask extends AsyncTask<String,Void,Reward> {
        private RewardDao mAsyncTaskDaoReward;
        getRewardByIdAsyncTask (RewardDao  dao) {
            mAsyncTaskDaoReward = dao;
        }
        @Override
        protected Reward doInBackground(final String... params) {
           return mAsyncTaskDaoReward.getRewardById(params[0]);
        }
    }
}

