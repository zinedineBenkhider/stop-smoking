package com.stop.smoking.home.presenter.interfaces;


import com.stop.smoking.home.presenter.model.Health;
import java.util.List;

public interface HealthFragmentContract {
    interface HealthFragmentView {
        void showProgress();
        void hideProgress();
        void showContentLayout();
        void hideContentLayout();
        void showMessageLayout();
        void hideMessageLayout();
        void setDataToAdapter(List<Health> healthList);
    }

    interface HealthFragmentPresenter {
        void onActivityCreated();
    }
}
