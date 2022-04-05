package com.stop.smoking.home.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.stop.smoking.R;
import com.stop.smoking.home.presenter.interfaces.TrophiesActionInterface;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.ArrayList;
import java.util.List;

public class TrophyAdapter extends RecyclerView.Adapter<TrophyAdapter.TrophyViewHolder> {
    private List<Trophy> trophiesList;
    private TrophiesActionInterface trophyActionInterface;

    public TrophyAdapter(TrophiesActionInterface trophyActionInterface) {
        this.trophiesList = new ArrayList<>();
        this.trophyActionInterface = trophyActionInterface;
    }

    @Override
    public TrophyAdapter.TrophyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_trophy_item, parent, false);
        TrophyViewHolder trophyViewHolder = new TrophyViewHolder(v);
        return trophyViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TrophyViewHolder holder, int position) {
        holder.updateTrophy(trophiesList.get(position));

    }

    @Override
    public int getItemCount() {
        return trophiesList.size();
    }


    public void bindViewModel(Trophy trophy) {
        this.trophiesList.add(trophy);
        notifyDataSetChanged();
    }

    public static class TrophyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, descriptionTextView;

        private View view;

        public TrophyViewHolder(View v) {
            super(v);
            this.view = v;
            nameTextView = v.findViewById(R.id.name_trophy_item);
            descriptionTextView = v.findViewById(R.id.description_trophy_item);

        }

        public void updateTrophy(final Trophy trophy) {
            nameTextView.setText(trophy.getName());
            descriptionTextView.setText(trophy.getDescription());

        }
    }
}