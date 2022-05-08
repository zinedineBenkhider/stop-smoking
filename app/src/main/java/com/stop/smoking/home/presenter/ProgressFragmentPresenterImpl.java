package com.stop.smoking.home.presenter;

import android.app.Application;

import com.stop.smoking.home.presenter.interfaces.ProgressFragmentContract;
import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.AcceptInReturnModel;
import com.stop.smoking.home.presenter.model.Trophy;
import com.stop.smoking.home.repository.ProfileRepository;
import com.stop.smoking.home.repository.dataBase.entity.Profile;
import com.stop.smoking.home.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragmentPresenterImpl implements ProgressFragmentContract.ProgressFragmentPresenter {
    private ProfileRepository mProfileReposistory;
    private ProgressFragmentContract.ProgressFragmentView mainView;
    public ProgressFragmentPresenterImpl(ProgressFragmentContract.ProgressFragmentView mainView, Application application){
        this.mainView=mainView;
        this.mProfileReposistory=ProfileRepository.getInstance(application);
    }

    @Override
    public void onActivityCreated() {
        Profile profile=this.mProfileReposistory.getProfile();
        this.mainView.setData(profile);

        List<AcceptInReturnModel> acceptInReturnModels=new ArrayList<>();
        float moneyLostPerDay=((float)profile.getPricePerPack()/profile.getCigarettesInPack())*profile.getCigarettesPerDay();
        int lifeSpanLostPerDayOnMilliseconds=660000*profile.getCigarettesPerDay();

        //After 1 Week
        String lifeSpanLostPerWeek=Utility.millisecondToDate(lifeSpanLostPerDayOnMilliseconds* 7L).toStringForAcceptInReturn();
        AcceptInReturnModel afterOneWeek=new AcceptInReturnModel("1 Week",Utility.formatFloat(moneyLostPerDay*7)+" "+Utility.getSymbolOfDevise(profile.getDevise()),lifeSpanLostPerWeek );
        acceptInReturnModels.add(afterOneWeek);

        //After 1 Month
        String lifeSpanLostPerMonth=Utility.millisecondToDate(lifeSpanLostPerDayOnMilliseconds* 30L).toStringForAcceptInReturn();
        AcceptInReturnModel afterOneMonth=new AcceptInReturnModel("1 Month",Utility.formatFloat(moneyLostPerDay*30)+" "+Utility.getSymbolOfDevise(profile.getDevise()),lifeSpanLostPerMonth );
        acceptInReturnModels.add(afterOneMonth);

        //After 1 Year
        String lifeSpanLostPerYear=Utility.millisecondToDate(lifeSpanLostPerDayOnMilliseconds*365L).toStringForAcceptInReturn();
        AcceptInReturnModel afterOneYear=new AcceptInReturnModel("1 Year",Utility.formatFloat(moneyLostPerDay*365)+" "+Utility.getSymbolOfDevise(profile.getDevise()),lifeSpanLostPerYear );
        acceptInReturnModels.add(afterOneYear);

        //After 5 Year
        String lifeSpanLostPerFiveYear=Utility.millisecondToDate(lifeSpanLostPerDayOnMilliseconds*365L*5).toStringForAcceptInReturn();
        AcceptInReturnModel afterFiveYear=new AcceptInReturnModel("5 Years",Utility.formatFloat(moneyLostPerDay*365*5)+" "+Utility.getSymbolOfDevise(profile.getDevise()),lifeSpanLostPerFiveYear );
        acceptInReturnModels.add(afterFiveYear);

        //After 10 Year
        String lifeSpanLostPerTenYears=Utility.millisecondToDate(lifeSpanLostPerDayOnMilliseconds*365L*10L).toStringForAcceptInReturn();
        AcceptInReturnModel afterTenYears=new AcceptInReturnModel("10 Years",Utility.formatFloat(moneyLostPerDay*365*10)+" "+Utility.getSymbolOfDevise(profile.getDevise()),lifeSpanLostPerTenYears );
        acceptInReturnModels.add(afterTenYears);

        //After 20 Years
        String lifeSpanLostPerTwentyYears=Utility.millisecondToDate(lifeSpanLostPerDayOnMilliseconds*365L*20L).toStringForAcceptInReturn();
        AcceptInReturnModel afterTwentyYears=new AcceptInReturnModel("20 Years",Utility.formatFloat(moneyLostPerDay*365*20)+" "+Utility.getSymbolOfDevise(profile.getDevise()),lifeSpanLostPerTwentyYears );
        acceptInReturnModels.add(afterTwentyYears);
        this.mainView.setDataToRecyclerView(acceptInReturnModels);

    }
}
