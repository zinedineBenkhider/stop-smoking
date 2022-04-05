package com.stop.smoking.home.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stop.smoking.R;
import com.stop.smoking.home.adapter.TrophyAdapter;
import com.stop.smoking.home.presenter.ProgressFragmentPresenterImpl;
import com.stop.smoking.home.presenter.TrophiesFragmentPresenterImpl;
import com.stop.smoking.home.presenter.interfaces.ProgressFragmentContract;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.List;

public class ProgressFragment extends Fragment  implements ProgressFragmentContract.ProgressFragmentView {
    private static ProgressFragment INSTANCE = null;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout contentLayout;
    private LinearLayout messageLayout;
    private TextView messageNoContactTextView;

    private ProgressFragment() {
    }

    public static ProgressFragment newInstance() {
        if (INSTANCE == null) {
            return new ProgressFragment();
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
        ProgressFragmentPresenterImpl fragmentPresenter = new ProgressFragmentPresenterImpl(this);
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

}