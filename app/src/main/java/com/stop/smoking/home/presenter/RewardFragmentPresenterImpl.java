package com.stop.smoking.home.presenter;

import android.app.Application;
import android.widget.EditText;

import com.stop.smoking.home.fragment.ProgressFragment;
import com.stop.smoking.home.presenter.interfaces.RewardFragmentContract;
import com.stop.smoking.home.presenter.model.DifferenceDateTime;
import com.stop.smoking.home.presenter.model.RewardModel;
import com.stop.smoking.home.repository.ProfileRepository;
import com.stop.smoking.home.repository.RewardRepository;
import com.stop.smoking.home.repository.dataBase.entity.Profile;
import com.stop.smoking.home.repository.dataBase.entity.Reward;
import com.stop.smoking.home.util.Utility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class RewardFragmentPresenterImpl implements RewardFragmentContract.RewardFragmentPresenter {
   private RewardFragmentContract.RewardFragmentView mainView;
    private RewardRepository mRewardReposistory;
    private ProfileRepository mProfileReposistory;
    public RewardFragmentPresenterImpl(RewardFragmentContract.RewardFragmentView mainView, Application application){
        this.mainView=mainView;
        mRewardReposistory=RewardRepository.getInstance(application);
        mProfileReposistory= ProfileRepository.getInstance(application);
    }

    @Override
    public void onActivityCreated() {
        Profile profile=mProfileReposistory.getProfile();
        List<Reward> rewards= mRewardReposistory.getRewards();
        List<RewardModel> rewardsModel=new ArrayList<>();
        Date date = new Date();
        String currentDate= ProgressFragment.dateTimeFormatter.format(date);
        DifferenceDateTime stoppedSmokingDuration= Utility.findDifference(profile.getQuittingDate()+":00",currentDate,false,ProgressFragment.dateTimeFormatter);
        float unsmokedDays=(float)stoppedSmokingDuration.getDifferenceWithMilliseconds()/86400000;
        float cigaretteNotSmoked=unsmokedDays* profile.getCigarettesPerDay();
        float moneySaved=cigaretteNotSmoked*profile.getPricePerPack()/profile.getCigarettesInPack();

        for (int i=0;i<rewards.size();i++){
            float availableMoney=moneySaved-profile.getMoneySpentOnRewards();
            int numberOfDaysNeededFromStoppingDate=(int)(stoppedSmokingDuration.getDays() * rewards.get(i).getPrice()/availableMoney);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DATE, numberOfDaysNeededFromStoppingDate-(int)stoppedSmokingDuration.getDays());
            SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd MMM yyyy");
            String dateRewardWillBeAvailable = dateTimeFormatter.format(calendar.getTime());
            int percent =(int)(availableMoney*100/rewards.get(i).getPrice());
            if(percent>100){
                percent=100;
            }
            RewardModel rewardModel= new RewardModel(rewards.get(i).getName(),rewards.get(i).getPrice(),dateRewardWillBeAvailable,percent, rewards.get(i).getId());
            rewardModel.setIsBought(rewards.get(i).isBought());
            rewardsModel.add(rewardModel);
        }
        this.mainView.setDataToAdapter(rewardsModel);
        this.mainView.hideProgress();
    }

    @Override
    public void onSave(String name, int price,String rewardId) {
        if(rewardId==null){
            mRewardReposistory.insertReward(new Reward(UUID.randomUUID().toString(),name,price,false));
        }
        else{
            Reward reward=mRewardReposistory.getRewardById(rewardId);
            reward.setPrice(price);
            reward.setName(name);
            mRewardReposistory.updateReward(reward);
        }
        onActivityCreated();
    }

    @Override
    public void onBuy(RewardModel rewardModel) {
        System.out.println("Buy");
        Reward reward=mRewardReposistory.getRewardById(rewardModel.getId());
        reward.setBought(true);
        mRewardReposistory.updateReward(reward);
        Profile profile=mProfileReposistory.getProfile();
        profile.setMoneySpentOnRewards(profile.getMoneySpentOnRewards()+reward.getPrice());
        mProfileReposistory.updateProfile(profile);
        onActivityCreated();
    }

    @Override
    public void setDataToRewardDialogBox(String rewardId,EditText nameEditText,EditText priceEditText) {
        if(rewardId!=null){
            Reward reward=mRewardReposistory.getRewardById(rewardId);
            nameEditText.setText(reward.getName());
            priceEditText.setText(String.valueOf(reward.getPrice()));
        }
    }
    @Override
    public void deleteReward(String rewardId){
        Reward reward=mRewardReposistory.getRewardById(rewardId);
        mRewardReposistory.deleteReward(reward);
        if(reward.isBought()){
            Profile profile=mProfileReposistory.getProfile();
            profile.setMoneySpentOnRewards(profile.getMoneySpentOnRewards()-reward.getPrice());
            mProfileReposistory.updateProfile(profile);
        }
        onActivityCreated();
    }

    @Override
    public void cancelPurchase(String rewardId){
        Reward reward=mRewardReposistory.getRewardById(rewardId);
        reward.setBought(false);
        mRewardReposistory.updateReward(reward);
        Profile profile=mProfileReposistory.getProfile();
        profile.setMoneySpentOnRewards(profile.getMoneySpentOnRewards()-reward.getPrice());
        mProfileReposistory.updateProfile(profile);
        onActivityCreated();
    }

}
