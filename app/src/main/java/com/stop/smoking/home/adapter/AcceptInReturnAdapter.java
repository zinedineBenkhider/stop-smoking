package com.stop.smoking.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stop.smoking.R;
import com.stop.smoking.home.presenter.model.AcceptInReturnModel;

import java.util.ArrayList;
import java.util.List;

public class AcceptInReturnAdapter extends RecyclerView.Adapter<AcceptInReturnAdapter.TrophyViewHolder> {
    private List<AcceptInReturnModel> acceptInReturnList;

    public AcceptInReturnAdapter() {
        this.acceptInReturnList = new ArrayList<>();
    }

    @Override
    public AcceptInReturnAdapter.TrophyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_accept_in_return, parent, false);
        TrophyViewHolder trophyViewHolder = new TrophyViewHolder(v);
        return trophyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrophyViewHolder holder, int position) {
        holder.updateTrophy(acceptInReturnList.get(position));

    }

    @Override
    public int getItemCount() {
        return acceptInReturnList.size();
    }


    public void bindViewModel(AcceptInReturnModel acceptInReturnModel) {
        this.acceptInReturnList.add(acceptInReturnModel);
        notifyDataSetChanged();
    }
    public void emptyData() {
        this.acceptInReturnList=new ArrayList<>();
    }


    public static class TrophyViewHolder extends RecyclerView.ViewHolder {
        private TextView lostMoneyTextView, durationTextView,lifeSpanLostTextView;

        private View view;

        public TrophyViewHolder(View v) {
            super(v);
            this.view = v;
            durationTextView = v.findViewById(R.id.duration_accept_in_return);
            lostMoneyTextView = v.findViewById(R.id.lost_money_accept_in_return);
            lifeSpanLostTextView = v.findViewById(R.id.life_span_lost_accept_in_return);

        }

        public void updateTrophy(final AcceptInReturnModel acceptInReturnModel) {
            durationTextView.setText(acceptInReturnModel.getDuration());
            lostMoneyTextView.setText(acceptInReturnModel.getLostMoney());
            lifeSpanLostTextView.setText(acceptInReturnModel.getLifeSpanLost());
        }
    }
}