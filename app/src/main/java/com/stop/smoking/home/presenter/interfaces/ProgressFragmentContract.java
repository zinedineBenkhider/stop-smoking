package com.stop.smoking.home.presenter.interfaces;


import com.stop.smoking.home.presenter.model.Trophy;

import java.util.List;

public interface ProgressFragmentContract {
    interface ProgressFragmentView {
        void showProgress();
        void hideProgress();
        void showContentLayout();
        void hideContentLayout();
        void showMessageLayout();
        void hideMessageLayout();
        void setDataToAdapter(List<Trophy> campaignList);
    }

    interface ProgressFragmentPresenter {
        void onActivityCreated();
    }
}
