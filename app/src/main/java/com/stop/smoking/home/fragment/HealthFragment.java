package com.stop.smoking.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stop.smoking.R;
import com.stop.smoking.home.adapter.HealthAdapter;
import com.stop.smoking.home.adapter.TrophyAdapter;
import com.stop.smoking.home.presenter.HealthFragmentPresenterImpl;
import com.stop.smoking.home.presenter.TrophiesFragmentPresenterImpl;
import com.stop.smoking.home.presenter.interfaces.HealthFragmentContract;
import com.stop.smoking.home.presenter.interfaces.TrophiesActionInterface;
import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.Health;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.List;

/**

 */
public class HealthFragment extends Fragment implements HealthFragmentContract.HealthFragmentView {
    private static HealthFragment INSTANCE = null;
    private HealthAdapter healthAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout contentLayout;
    private LinearLayout messageLayout;
    private TextView messageNoContactTextView;

    private HealthFragment() {
    }

    public static HealthFragment newInstance() {
        if (INSTANCE == null) {
            return new HealthFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_health, container, false);
        INSTANCE = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = rootView.findViewById(R.id.progress_bar_health);
        contentLayout = rootView.findViewById(R.id.content_health_fragment);
        messageLayout = rootView.findViewById(R.id.no_element_linear_layout);
        messageNoContactTextView = rootView.findViewById(R.id.message_no_elements);
        healthAdapter = new HealthAdapter();
        recyclerView = rootView.findViewById(R.id.recycler_view_health);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(healthAdapter);
        HealthFragmentPresenterImpl fragmentPresenter = new HealthFragmentPresenterImpl(this);
        fragmentPresenter.onActivityCreated();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showContentLayout() {

    }

    @Override
    public void hideContentLayout() {

    }

    @Override
    public void showMessageLayout() {

    }

    @Override
    public void hideMessageLayout() {

    }

    @Override
    public void setDataToAdapter(List<Health> healthList) {
        for (int i = 0; i < healthList.size(); i++) {
            healthAdapter.bindViewModel(healthList.get(i));
        }
    }


}