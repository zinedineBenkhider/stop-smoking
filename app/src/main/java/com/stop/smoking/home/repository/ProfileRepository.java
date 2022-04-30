package com.stop.smoking.home.repository;

import android.app.Application;
import android.os.AsyncTask;
import com.stop.smoking.home.repository.dataBase.StopSmokingRoomDatabase;
import com.stop.smoking.home.repository.dataBase.dao.ProfileDao;
import com.stop.smoking.home.repository.dataBase.entity.Profile;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfileRepository {
    private ProfileDao mProfileDao;
    private StopSmokingRoomDatabase db;
    private static ProfileRepository INSTANCE =null;

    public ProfileRepository(Application application) {
        db = StopSmokingRoomDatabase.getDatabase(application);
        mProfileDao=db.ProfileDao();
    }

    public static ProfileRepository getInstance(Application application){
        if (INSTANCE==null){
            INSTANCE=new ProfileRepository(application);
        }
        return INSTANCE;
    }

    public void insertProfile(Profile profile) {
        new ProfileRepository.insertProfileAsyncTask(mProfileDao).execute(profile);
    }

    public void updateProfile(Profile profile){
        new ProfileRepository.UpdateProfileAsyncTask(mProfileDao).execute(profile);
    }

    public  Profile getProfile(){
        try {
            List<Profile> profiles= new GetProfileAsyncTask(mProfileDao).execute().get();
            if(profiles.size()>0){
                return profiles.get(0);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return  null;
    }

    private static class insertProfileAsyncTask extends AsyncTask<Profile, Void, Void> {
        private ProfileDao mAsyncTaskDaoProfile;
        insertProfileAsyncTask (ProfileDao noteDao) {
            mAsyncTaskDaoProfile=noteDao;
        }
        @Override
        protected Void doInBackground(final Profile... params) {
            mAsyncTaskDaoProfile.insertProfile(params[0]);
            return null;
        }

    }

    private static class GetProfileAsyncTask extends AsyncTask<String, Void, List<Profile>> {
        private ProfileDao mAsyncTaskDaoProfile;
        GetProfileAsyncTask (ProfileDao noteDao) {
            mAsyncTaskDaoProfile=noteDao;
        }
        @Override
        protected List<Profile> doInBackground(final String... params) {
            return mAsyncTaskDaoProfile.getProfiles();
        }
    }

    private static class UpdateProfileAsyncTask extends AsyncTask<Profile,Void,Void> {
        private ProfileDao mAsyncTaskDaoProfile;
        UpdateProfileAsyncTask (ProfileDao  dao) {
            mAsyncTaskDaoProfile = dao;
        }
        @Override
        protected Void doInBackground(final Profile... params) {
            mAsyncTaskDaoProfile.update(params[0]);
            return null;
        }
    }
}

