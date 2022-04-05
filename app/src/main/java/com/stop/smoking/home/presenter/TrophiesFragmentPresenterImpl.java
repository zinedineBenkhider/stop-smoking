package com.stop.smoking.home.presenter;

import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.ArrayList;
import java.util.List;

public class TrophiesFragmentPresenterImpl implements TrophiesFragmentContract.TrophiesFragmentPresenter {
   private TrophiesFragmentContract.TrophiesFragmentView mainView;
    public TrophiesFragmentPresenterImpl(TrophiesFragmentContract.TrophiesFragmentView mainView){
        this.mainView=mainView;
    }

    @Override
    public void onActivityCreated() {
        this.mainView.hideProgress();
        List<Trophy> trophies=new ArrayList<Trophy>();
        trophies.add(new Trophy("The decision is taked","The decision is taked"));
        this.mainView.setDataToAdapter(trophies);
    }
}
