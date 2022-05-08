package com.stop.smoking.home.presenter.interfaces;


import android.widget.EditText;

import com.stop.smoking.home.presenter.model.RewardModel;

import java.util.List;

public interface RewardFragmentContract {
    interface RewardFragmentView {
        void showProgress();
        void hideProgress();
        void showContentLayout();
        void hideContentLayout();
        void showMessageLayout();
        void hideMessageLayout();
        void setDataToAdapter(List<RewardModel> rewardModelList);
    }

    interface RewardFragmentPresenter {
        void onActivityCreated();
        void onSave(String name, int price,String rewardId);
        void onBuy(RewardModel rewardModel);
        void setDataToRewardDialogBox(String rewardId,EditText nameEditText, EditText priceEditText);
        void deleteReward(String rewardId);
        void cancelPurchase(String rewardId);
    }
}
