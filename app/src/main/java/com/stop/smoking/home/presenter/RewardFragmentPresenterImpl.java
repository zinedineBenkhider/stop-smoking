package com.stop.smoking.home.presenter;

import com.stop.smoking.home.presenter.interfaces.RewardFragmentContract;
import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.Reward;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.ArrayList;
import java.util.List;

public class RewardFragmentPresenterImpl implements RewardFragmentContract.RewardFragmentPresenter {
   private RewardFragmentContract.RewardFragmentView mainView;
    public RewardFragmentPresenterImpl(RewardFragmentContract.RewardFragmentView mainView){
        this.mainView=mainView;
    }

    @Override
    public void onActivityCreated() {
        this.mainView.hideProgress();
        List<Reward> rewards=new ArrayList<Reward>();
        rewards.add(new Reward("Montre",20,"6 mai",100));
        this.mainView.setDataToAdapter(rewards);
    }
}
