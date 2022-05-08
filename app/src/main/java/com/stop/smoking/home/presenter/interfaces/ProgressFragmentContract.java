package com.stop.smoking.home.presenter.interfaces;


import com.stop.smoking.home.presenter.model.AcceptInReturnModel;
import com.stop.smoking.home.presenter.model.Trophy;
import com.stop.smoking.home.repository.dataBase.entity.Profile;

import java.util.List;

public interface ProgressFragmentContract {
    interface ProgressFragmentView {
        void showProgress();
        void hideProgress();
        void showContentLayout();
        void hideContentLayout();
        void showMessageLayout();
        void hideMessageLayout();
        void setData(Profile profile);
        void setDataToRecyclerView(List<AcceptInReturnModel> acceptInReturnModels);
    }

    interface ProgressFragmentPresenter {
        void onActivityCreated();
    }
}
