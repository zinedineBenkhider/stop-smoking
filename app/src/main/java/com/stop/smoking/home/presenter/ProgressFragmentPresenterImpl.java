package com.stop.smoking.home.presenter;

import android.app.Application;

import com.stop.smoking.home.presenter.interfaces.ProgressFragmentContract;
import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.Trophy;
import com.stop.smoking.home.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragmentPresenterImpl implements ProgressFragmentContract.ProgressFragmentPresenter {
    private ProfileRepository mProfileReposistory;
    private ProgressFragmentContract.ProgressFragmentView mainView;
    public ProgressFragmentPresenterImpl(ProgressFragmentContract.ProgressFragmentView mainView, Application application){
        this.mainView=mainView;
        this.mProfileReposistory=ProfileRepository.getInstance(application);
    }

    @Override
    public void onActivityCreated() {
        this.mainView.hideProgress();
    }
}
