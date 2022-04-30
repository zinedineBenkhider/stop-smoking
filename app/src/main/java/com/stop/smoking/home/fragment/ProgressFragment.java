package com.stop.smoking.home.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment  implements ProgressFragmentContract.ProgressFragmentView , MenuItem.OnMenuItemClickListener {
    private static ProgressFragment INSTANCE = null;
    private TextView nonSmokerTextView,recoveredLifeExpectancyTextView;
    private View rootView;
    private ProgressBar progressBar;
    private LinearLayout contentLayout;
    private LinearLayout messageLayout;
    private TextView messageNoContactTextView;
    private ImageButton imageButton;
    private static  String stoppedDate="28-04-2022 05:05:05";
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
        nonSmokerTextView=rootView.findViewById(R.id.non_smoker_time_text_view);
        recoveredLifeExpectancyTextView=rootView.findViewById(R.id.recovered_life_expectancy_text_view);
        final Handler handler = new Handler();
        final int delay = 1000;
        handler.postDelayed(new Runnable() {
            public void run() {
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                Date date = new Date();
                String currentDate= formatter.format(date);
                String stoppedSmokingDuration=findDifference(stoppedDate,currentDate,false);
                String recoveredLifeExpectancy=findDifference(stoppedDate,currentDate,true);
                nonSmokerTextView.setText(stoppedSmokingDuration);
                recoveredLifeExpectancyTextView.setText(recoveredLifeExpectancy);
                handler.postDelayed(this, delay);
            }
        }, delay);

        imageButton=rootView.findViewById(R.id.select_days_btn);
        imageButton.setOnClickListener(v -> showPopup(v));
        ProgressFragmentPresenterImpl fragmentPresenter = new ProgressFragmentPresenterImpl(this,getActivity().getApplication());
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

    // Function to print difference in
    // time start_date and end_date
    static String findDifference(String start_date, String end_date,boolean divideByFour)
    {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);
            assert d2 != null;
            assert d1 != null;
            long difference_In_Time = d2.getTime() - d1.getTime();
            if(divideByFour){
                difference_In_Time=difference_In_Time/4;
            }
            long difference_In_Seconds = (difference_In_Time / 1000) % 60;
            long difference_In_Minutes = (difference_In_Time / (1000 * 60)) % 60;
            long difference_In_Hours = (difference_In_Time / (1000 * 60 * 60)) % 24;
            long difference_In_Years = (difference_In_Time / (1000L * 60 * 60 * 24 * 365));
            long difference_In_Days = (difference_In_Time / (1000 * 60 * 60 * 24)) % 365;

            String res="";
            if(difference_In_Years!=0){
                res+=difference_In_Years+"Y ";
            }
            if(difference_In_Days!=0){
                res+=difference_In_Days+"D ";
            }
            if(difference_In_Hours!=0){
                res+=difference_In_Hours+"h ";
            }
            if(difference_In_Minutes!=0){
                res+=difference_In_Minutes+"m ";
            }
            res+=difference_In_Seconds+"s ";

           return res;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
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