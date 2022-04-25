package com.stop.smoking.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stop.smoking.R;
import com.stop.smoking.home.presenter.interfaces.RewardActionInterface;
import com.stop.smoking.home.presenter.interfaces.TrophiesActionInterface;
import com.stop.smoking.home.presenter.model.Reward;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.ArrayList;
import java.util.List;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.RewardViewHolder> {
    private List<Reward> rewardList;
    private static RewardActionInterface rewardActionInterface;

    public RewardAdapter(RewardActionInterface rewardActionInterface) {
        this.rewardList = new ArrayList<>();
        this.rewardActionInterface = rewardActionInterface;
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

    public void bindViewModel(Reward reward) {
        this.rewardList.add(reward);
        notifyDataSetChanged();
    }

    public static class RewardViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView,priceTextView, percentTextView,statusTextView;
        private ProgressBar rewardProgressBar,rewardCompletedProgressBar;
        private ImageButton rewardMoreBtn;
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

        }

        public void updateReward(final Reward reward) {
            nameTextView.setText(reward.getName());
            priceTextView.setText(reward.getPrice()+" €");
            percentTextView.setText(reward.getPercent()+"%");
            statusTextView.setText(reward.getStatus());
            if(reward.getPercent()==100){
                rewardProgressBar.setVisibility(View.GONE);
                rewardCompletedProgressBar.setProgress(reward.getPercent());
            }
            else{
                rewardCompletedProgressBar.setVisibility(View.GONE);
                rewardProgressBar.setProgress(reward.getPercent());
            }
            rewardMoreBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rewardActionInterface.onRewardClick(v,reward);
                }
            });

        }
    }
}