package com.stop.smoking.home.fragment;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stop.smoking.R;
import com.stop.smoking.home.adapter.TrophyAdapter;
import com.stop.smoking.home.presenter.ProgressFragmentPresenterImpl;
import com.stop.smoking.home.presenter.TrophiesFragmentPresenterImpl;
import com.stop.smoking.home.presenter.interfaces.ProgressFragmentContract;
import com.stop.smoking.home.presenter.model.Trophy;

import java.util.ArrayList;
import java.util.List;

public class ProgressFragment extends Fragment  implements ProgressFragmentContract.ProgressFragmentView , MenuItem.OnMenuItemClickListener {
    private static ProgressFragment INSTANCE = null;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout contentLayout;
    private LinearLayout messageLayout;
    private TextView messageNoContactTextView;
    private ImageButton imageButton;
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
        rootView = inflater.inflate(R.layout.fragment_progress, container, false);
        INSTANCE = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        imageButton=rootView.findViewById(R.id.select_days_btn);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(v);
            }
        });
        ProgressFragmentPresenterImpl fragmentPresenter = new ProgressFragmentPresenterImpl(this);
        fragmentPresenter.onActivityCreated();
    }

    public void showPopup(View v) {
        String[] items=getResources().getStringArray(R.array.days);
        PopupMenu menu = new PopupMenu(getContext(), v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menu.setGravity(Gravity.END);
        }
        for (String s : items) {
            menu.getMenu().add(s);
        }
        menu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.description_trophy_item:
                return true;
            case R.id.day_text_view:
                return true;
            default:
                return false;
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //progressBar.setVisibility(View.GONE);
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