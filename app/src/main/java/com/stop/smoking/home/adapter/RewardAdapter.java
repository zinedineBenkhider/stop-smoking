package com.stop.smoking.home.adapter;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stop.smoking.R;
import com.stop.smoking.home.presenter.interfaces.RewardActionInterface;
import com.stop.smoking.home.presenter.model.RewardModel;

import java.util.ArrayList;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
    private List<RewardModel> rewardList;
    private static RewardActionInterface rewardActionInterface;
    private static Application application;

    public RewardAdapter(RewardActionInterface rewardActionInterface, Application application) {
        this.rewardList = new ArrayList<>();
        RewardAdapter.rewardActionInterface = rewardActionInterface;
        RewardAdapter.application=application;
    }

    @Override
    public RewardAdapter.RewardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_reward_item, parent, false);
        RewardViewHolder trophyViewHolder = new RewardViewHolder(v);
        return trophyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RewardViewHolder holder, int position) {
        holder.updateReward(rewardList.get(position));
    }

    @Override
    public int getItemCount() {
        return rewardList.size();
    }

    public void bindViewModel(RewardModel reward) {
        this.rewardList.add(reward);
        notifyDataSetChanged();
    }

    public void emptyData() {
        this.rewardList=new ArrayList<>();
    }
    public static class RewardViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView,priceTextView, percentTextView,statusTextView;
        private ProgressBar rewardProgressBar,rewardCompletedProgressBar;
        private ImageButton rewardMoreBtn;
        private Button buyRewardBtn;
        private View view;
        public RewardViewHolder(View v) {
            super(v);
            this.view = v;
            nameTextView = v.findViewById(R.id.name_reward_item);
            priceTextView = v.findViewById(R.id.price_reward_item);
            percentTextView = v.findViewById(R.id.percent_reward_item);
            statusTextView = v.findViewById(R.id.status_reward_item);
            rewardProgressBar = v.findViewById(R.id.reward_progress);
            rewardCompletedProgressBar=v.findViewById(R.id.reward_progress_completed);
            rewardMoreBtn=v.findViewById(R.id.more_btn_reward);
            buyRewardBtn=v.findViewById(R.id.buy_btn_reward_item);

        }

        public void updateReward(final RewardModel reward) {
            nameTextView.setText(reward.getName());
            priceTextView.setText(reward.getPrice()+" €");
            statusTextView.setText(reward.getStatus());
            if(reward.getIsBought()){
                rewardCompletedProgressBar.setVisibility(View.VISIBLE);
                rewardProgressBar.setVisibility(View.GONE);
                percentTextView.setText("100%");
                rewardCompletedProgressBar.setProgress(100);
                statusTextView.setText(application.getResources().getString(R.string.bought));
            }
            else{
                if(reward.getPercent()==100){
                    statusTextView.setText(application.getResources().getString(R.string.available));
                }
                rewardProgressBar.setVisibility(View.VISIBLE);
                rewardCompletedProgressBar.setVisibility(View.GONE);
                percentTextView.setText(reward.getPercent()+"%");
                rewardProgressBar.setProgress(reward.getPercent());
            }
            if(reward.getIsBought()){
                buyRewardBtn.setEnabled(false);
            }
            else{
                buyRewardBtn.setEnabled(true);
            }
            rewardMoreBtn.setOnClickListener(v -> rewardActionInterface.onRewardClick(v,reward));
            buyRewardBtn.setOnClickListener(v -> rewardActionInterface.onBuyRewardClick(reward));
        }
    }
}