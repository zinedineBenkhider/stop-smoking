package com.stop.smoking.home.presenter.interfaces;


import com.stop.smoking.home.presenter.model.Reward;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.List;

public interface RewardFragmentContract {
    interface RewardFragmentView {
        void showProgress();
        void hideProgress();
        void showContentLayout();
        void hideContentLayout();
        void showMessageLayout();
        void hideMessageLayout();
        void setDataToAdapter(List<Reward> campaignList);
    }

    interface RewardFragmentPresenter {
        void onActivityCreated();
    }
}
