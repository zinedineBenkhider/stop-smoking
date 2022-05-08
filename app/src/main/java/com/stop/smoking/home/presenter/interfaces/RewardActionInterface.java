package com.stop.smoking.home.presenter.interfaces;

import android.view.View;

import com.stop.smoking.home.presenter.model.RewardModel;

public interface RewardActionInterface {
    void onRewardClick(View v, RewardModel reward);
    void onBuyRewardClick(RewardModel reward);

}
