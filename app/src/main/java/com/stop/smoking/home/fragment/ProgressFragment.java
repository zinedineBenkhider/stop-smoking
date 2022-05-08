package com.stop.smoking.home.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.stop.smoking.R;
import com.stop.smoking.home.MainActivity;
import com.stop.smoking.home.activity.ProfileActivity;
import com.stop.smoking.home.adapter.AcceptInReturnAdapter;
import com.stop.smoking.home.presenter.ProgressFragmentPresenterImpl;
import com.stop.smoking.home.presenter.interfaces.ProgressFragmentContract;
import com.stop.smoking.home.presenter.model.AcceptInReturnModel;
import com.stop.smoking.home.presenter.model.DifferenceDateTime;
import com.stop.smoking.home.repository.dataBase.entity.Profile;
import com.stop.smoking.home.util.Utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ProgressFragment extends Fragment  implements ProgressFragmentContract.ProgressFragmentView  {
    private static ProgressFragment INSTANCE = null;
    private TextView nonSmokerTextView,recoveredLifeExpectancyTextView,moneySavedTextView,
            unsmokedCigarettesTextView, moneySpentTextView,lifeLostTextView,
            smokedCigarettesTextView,dayTextView,nbDaysTextView, progressPercentTextView;
    private View rootView;
    public static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
    private ProgressFragmentPresenterImpl progressFragmentPresenterImpl;
    private Profile profile;
    private ProgressBar progressBar;
    private ImageButton imageButton;
    private RecyclerView acceptInReturnRecycler;
    private AcceptInReturnAdapter acceptInReturnAdapter;

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
        acceptInReturnRecycler=rootView.findViewById(R.id.recycler_view_accept_in_return);
        acceptInReturnRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        acceptInReturnAdapter=new AcceptInReturnAdapter();
        acceptInReturnRecycler.setAdapter(acceptInReturnAdapter);
        nonSmokerTextView=rootView.findViewById(R.id.non_smoker_time_text_view);
        recoveredLifeExpectancyTextView=rootView.findViewById(R.id.recovered_life_expectancy_text_view);
        moneySavedTextView=rootView.findViewById(R.id.money_saved_text_view);
        unsmokedCigarettesTextView=rootView.findViewById(R.id.unsmoked_cigarette_text_view);
        lifeLostTextView=rootView.findViewById(R.id.life_lost_text_view);
        moneySpentTextView=rootView.findViewById(R.id.money_spent_text_view);
        smokedCigarettesTextView=rootView.findViewById(R.id.smoked_cigarettes_text_view);
        dayTextView=rootView.findViewById(R.id.day_text_view);
        progressBar=rootView.findViewById(R.id.progress_bar);
        nbDaysTextView =rootView.findViewById(R.id.selected_days_text_view);
        progressPercentTextView =rootView.findViewById(R.id.progress_percent_text_view);
        imageButton=rootView.findViewById(R.id.select_days_btn);
        imageButton.setOnClickListener(v -> showPopup(v));
        progressFragmentPresenterImpl=new ProgressFragmentPresenterImpl(this,getActivity().getApplication());
        progressFragmentPresenterImpl.onActivityCreated();
    }

    public void showPopup(View v) {
        String[] items=getResources().getStringArray(R.array.days);
        PopupMenu menu = new PopupMenu(getContext(), v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menu.setGravity(Gravity.END);
        }
        int id=0;
        for (String s : items) {
            menu.getMenu().add(id,id,id,s);
            id++;
        }
        menu.show();
        menu.setOnMenuItemClickListener(item -> {
            nbDaysTextView.setText(item.getTitle());
            int nbDays= Utility.getNbDays(item.getItemId()) ;
            setProgressValues(nbDays);
            return false;
        });
    }

    private void setProgressValues(int nbDays){
        Date date = new Date();
        String currentDate= dateTimeFormatter.format(date);
        DifferenceDateTime stoppedSmokingDuration= Utility.findDifference(profile.getQuittingDate()+":00",currentDate,false,dateTimeFormatter);
        float unsmokedDays=(float)stoppedSmokingDuration.getDifferenceWithMilliseconds()/86400000;
        float progressPercent=unsmokedDays*100/nbDays;
        String progressPercentStr;
        if(progressPercent>100){
            progressPercent=100;
        }
        if(progressPercent==Math.round(progressPercent)){
            progressPercentStr=(int)progressPercent+" %";
        }
        else{
            progressPercentStr=String.format("%.1f", progressPercent)+" %";
        }
        progressPercentTextView.setText(progressPercentStr);
        onProgressPercentChanged((int)progressPercent);
    }

    private void onProgressPercentChanged(int percentage){
        Animator animation = ObjectAnimator.ofInt(progressBar,"progress",percentage);
        animation.setDuration(750);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();
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
    public void setData(Profile profile) {
        this.profile=profile;
        if(profile==null){
            Intent intent = new Intent(getContext(), ProfileActivity.class);
            startActivity(intent);
            getActivity().finish();
        }
        else{
            final Handler handler = new Handler();
            final int delay = 1000;
            String daysStringResource=getResources().getString(R.string.days);
            calculateValues(daysStringResource);
            handler.postDelayed(new Runnable() {
                public void run() {
                    calculateValues(daysStringResource);
                    handler.postDelayed(this, delay);
                }
            }, delay);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.YEAR, profile.getYearsOfSmoking()*-1);
            String dateOfBeginning= dateTimeFormatter.format(cal.getTime());
            DifferenceDateTime smokingDuration= Utility.findDifference(dateOfBeginning,profile.getQuittingDate()+":00",true,dateTimeFormatter);
            lifeLostTextView.setText(smokingDuration.toStringBis());
            float smokingHours=(float)smokingDuration.getDifferenceWithMilliseconds()/86400000;
            float cigaretteSmoked=smokingHours * profile.getCigarettesPerDay();
            smokedCigarettesTextView.setText(String.valueOf((int)cigaretteSmoked));
            float moneySpent=cigaretteSmoked*profile.getPricePerPack()/profile.getCigarettesInPack();
            moneySpentTextView.setText((int)moneySpent+" "+Utility.getSymbolOfDevise(profile.getDevise()));
            Date date = new Date();
            String currentDate= dateTimeFormatter.format(date);
            DifferenceDateTime stoppedSmokingDuration = Utility.findDifference(profile.getQuittingDate()+":00",currentDate,false,dateTimeFormatter);
            float unsmokedDays=(float)stoppedSmokingDuration.getDifferenceWithMilliseconds()/86400000;
            String[] daysMenuItems=getResources().getStringArray(R.array.days);
            String menuItemTitle=Utility.getUpperMenuItemFromUnsmokedDays(daysMenuItems,(int)unsmokedDays);
            nbDaysTextView.setText(menuItemTitle);
            int menuItemId = Arrays.asList(daysMenuItems).indexOf(menuItemTitle);
            int nbDays= Utility.getNbDays(menuItemId);
            setProgressValues(nbDays);

        }
    }

    @Override
    public void setDataToRecyclerView(List<AcceptInReturnModel> acceptInReturnModels) {
        acceptInReturnAdapter.emptyData();
        for (int i = 0; i < acceptInReturnModels.size(); i++) {
            acceptInReturnAdapter.bindViewModel(acceptInReturnModels.get(i));
        }
    }

    private void calculateValues(String daysStringResource){
        Date date = new Date();
        String currentDate= dateTimeFormatter.format(date);
        DifferenceDateTime stoppedSmokingDuration= Utility.findDifference(profile.getQuittingDate()+":00",currentDate,false,dateTimeFormatter);
        String stoppedSmokingDurationStr=stoppedSmokingDuration.toString();
        String recoveredLifeExpectancyStr=Utility.findDifference(profile.getQuittingDate()+":00",currentDate,true,dateTimeFormatter).toString();
        nonSmokerTextView.setText(stoppedSmokingDurationStr);
        recoveredLifeExpectancyTextView.setText(recoveredLifeExpectancyStr);
        float unsmokedDays=(float)stoppedSmokingDuration.getDifferenceWithMilliseconds()/86400000;
        float cigaretteNotSmoked=unsmokedDays* profile.getCigarettesPerDay();
        unsmokedCigarettesTextView.setText(String.format("%.2f", cigaretteNotSmoked));
        float moneySaved=cigaretteNotSmoked*profile.getPricePerPack()/profile.getCigarettesInPack();
        moneySavedTextView.setText(String.format("%.2f", moneySaved)+" "+Utility.getSymbolOfDevise(profile.getDevise()));
        dayTextView.setText((int)unsmokedDays+1+" "+daysStringResource);
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


}