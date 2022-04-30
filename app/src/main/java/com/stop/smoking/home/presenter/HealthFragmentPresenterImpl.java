package com.stop.smoking.home.presenter;

import com.stop.smoking.home.presenter.interfaces.HealthFragmentContract;
import com.stop.smoking.home.presenter.model.Health;
import com.stop.smoking.home.repository.ProfileRepository;

import java.util.ArrayList;
import java.util.List;

public class HealthFragmentPresenterImpl implements HealthFragmentContract.HealthFragmentPresenter {

   private HealthFragmentContract.HealthFragmentView mainView;
    public HealthFragmentPresenterImpl(HealthFragmentContract.HealthFragmentView mainView){
        this.mainView=mainView;

    }

    @Override
    public void onActivityCreated() {
        this.mainView.hideProgress();
        List<Health> healthList=new ArrayList<Health>();
        healthList.add(new Health("Aprés 20 minutes", 100, "Atteinte","Votre pression artérielle et votre fréquence cardiaque diminuent."));
        healthList.add(new Health("Aprés 8 heures", 80, "Dans 2 heures","Le niveau de monoxyde de carbone dans votre sang est à nouveau normal."));

        this.mainView.setDataToAdapter(healthList);
    }
}
