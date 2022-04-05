package com.stop.smoking.home.presenter.interfaces;


import com.stop.smoking.home.presenter.model.Trophy;

import java.util.List;

public interface TrophiesFragmentContract {
    interface TrophiesFragmentView {
        void showProgress();
        void hideProgress();
        void showContentLayout();
        void hideContentLayout();
        void showMessageLayout();
        void hideMessageLayout();
        void setDataToAdapter(List<Trophy> campaignList);
    }

    interface TrophiesFragmentPresenter {
        void onActivityCreated();
    }
}
