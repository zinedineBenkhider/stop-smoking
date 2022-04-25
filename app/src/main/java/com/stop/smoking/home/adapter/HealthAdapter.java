package com.stop.smoking.home.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.stop.smoking.R;
import com.stop.smoking.home.presenter.model.Health;
import java.util.ArrayList;
import java.util.List;

public class HealthAdapter extends RecyclerView.Adapter<HealthAdapter.HealthViewHolder> {
    private List<Health> healthList;

    public HealthAdapter() {
        this.healthList = new ArrayList<Health>();
    }

    @Override
    public HealthAdapter.HealthViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_health_item, parent, false);
        HealthViewHolder healthViewHolder = new HealthViewHolder(v);
        return healthViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HealthViewHolder holder, int position) {
        holder.updateTrophy(healthList.get(position));
    }

    @Override
    public int getItemCount() {
        return healthList.size();
    }

    public void bindViewModel(Health health) {
        this.healthList.add(health);
        notifyDataSetChanged();
    }

    public static class HealthViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTextView, statusTextView, descriptionTextView, percentTextView;
        private ProgressBar healthProgressBar,healthCompletedProgressBar;
        private View view;
        public HealthViewHolder(View v) {
            super(v);
            this.view = v;
            titleTextView = v.findViewById(R.id.title_health_item);
            statusTextView = v.findViewById(R.id.status_health_item);
            descriptionTextView = v.findViewById(R.id.description_health_item);
            percentTextView= v.findViewById(R.id.percent_health_item);
            healthProgressBar = v.findViewById(R.id.health_progress);
            healthCompletedProgressBar=v.findViewById(R.id.health_progress_completed);
        }

        public void updateTrophy(final Health health) {
            titleTextView.setText(health.getTitle());
            statusTextView.setText(health.getStatus());
            descriptionTextView.setText(health.getDescription());
            percentTextView.setText(health.getPercent()+"%");

            if(health.getPercent()==100){
                healthProgressBar.setVisibility(View.GONE);
                healthCompletedProgressBar.setProgress(health.getPercent());
            }
            else{
                healthCompletedProgressBar.setVisibility(View.GONE);
                healthProgressBar.setProgress(health.getPercent());
            }


        }
    }
}