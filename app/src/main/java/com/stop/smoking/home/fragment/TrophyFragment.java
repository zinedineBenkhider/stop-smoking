package com.stop.smoking.home.fragment;

import android.content.Intent;
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
import com.stop.smoking.home.adapter.TrophyAdapter;
import com.stop.smoking.home.presenter.TrophiesFragmentPresenterImpl;
import com.stop.smoking.home.presenter.interfaces.TrophiesActionInterface;
import com.stop.smoking.home.presenter.interfaces.TrophiesFragmentContract;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.List;


public class TrophyFragment extends Fragment implements TrophiesFragmentContract.TrophiesFragmentView, TrophiesActionInterface {

    private static TrophyFragment INSTANCE = null;
    private TrophyAdapter trophyAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout contentLayout;
    private LinearLayout messageLayout;
    private TextView messageNoContactTextView;

    private TrophyFragment() {
    }

    public static TrophyFragment newInstance() {
        if (INSTANCE == null) {
            return new TrophyFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_trophy, container, false);
        INSTANCE = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = rootView.findViewById(R.id.progress_bar_trophy);
        contentLayout = rootView.findViewById(R.id.content_trophy_fragment);
        messageLayout = rootView.findViewById(R.id.no_element_linear_layout);
        messageNoContactTextView = rootView.findViewById(R.id.message_no_elements);
        trophyAdapter = new TrophyAdapter(this);
        recyclerView = rootView.findViewById(R.id.recycler_view_trophy);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setAdapter(trophyAdapter);
        TrophiesFragmentPresenterImpl fragmentPresenter = new TrophiesFragmentPresenterImpl(this);
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
    public void setDataToAdapter(List<Trophy> trophies) {
        for (int i = 0; i < trophies.size(); i++) {
            trophyAdapter.bindViewModel(trophies.get(i));
        }
    }

    @Override
    public void showContentLayout() {
        contentLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideContentLayout() {
        contentLayout.setVisibility(View.GONE);
    }

    @Override
    public void showMessageLayout() {
        messageLayout.setVisibility(View.VISIBLE);
        messageNoContactTextView.setText(R.string.msg_no_trophy);
    }

    @Override
    public void hideMessageLayout() {
        messageLayout.setVisibility(View.GONE);
    }


    @Override
    public void onTrophyClick(Trophy trophy) {
       /* Intent intent = new Intent(getActivity(), ActivityTrophyDetail.class);
        intent.putExtra(ActivityTrophyDetail.ID_ASSET_DETAIL_ASSET, trophy.getId());
        startActivity(intent);*/

    }
}