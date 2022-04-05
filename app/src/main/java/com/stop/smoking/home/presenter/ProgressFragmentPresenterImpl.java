package com.stop.smoking.home.presenter;

import com.stop.smoking.home.presenter.interfaces.ProgressFragmentContract;
import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragmentPresenterImpl implements ProgressFragmentContract.ProgressFragmentPresenter {
   private ProgressFragmentContract.ProgressFragmentView mainView;
    public ProgressFragmentPresenterImpl(ProgressFragmentContract.ProgressFragmentView mainView){
        this.mainView=mainView;
    }

    @Override
    public void onActivityCreated() {
        this.mainView.hideProgress();
    }
}
