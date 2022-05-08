package com.stop.smoking.home.fragment;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.stop.smoking.R;
import com.stop.smoking.home.adapter.RewardAdapter;
import com.stop.smoking.home.presenter.RewardFragmentPresenterImpl;
import com.stop.smoking.home.presenter.interfaces.RewardActionInterface;
import com.stop.smoking.home.presenter.interfaces.RewardFragmentContract;
import com.stop.smoking.home.presenter.model.RewardModel;

import java.util.List;

public class RewardFragment extends Fragment implements RewardFragmentContract.RewardFragmentView, RewardActionInterface {
    private static RewardFragment INSTANCE = null;
    private Dialog createRewardDialogBox;
    private Button cancelRewardDialogBoxBtn,saveRewardDialogBoxBtn,buyRewardBtn;
    private RewardAdapter rewardAdapter;
    private RecyclerView recyclerView;
    private EditText rewardNameEditText,rewardPriceEditText;
    private View rootView;
    private ProgressBar progressBar;
    private RewardFragmentPresenterImpl fragmentPresenter;
    private RewardFragment() {
    }

    public static RewardFragment newInstance() {
        if (INSTANCE == null) {
            return new RewardFragment();
        }
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_reward, container, false);
        INSTANCE = this;
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar = rootView.findViewById(R.id.progress_bar_reward);
        buyRewardBtn=rootView.findViewById(R.id.buy_btn_reward_item);
        rewardAdapter = new RewardAdapter(this,getActivity().getApplication());
        recyclerView = rootView.findViewById(R.id.recycler_view_reward);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rewardAdapter);
        fragmentPresenter = new RewardFragmentPresenterImpl(this,getActivity().getApplication());
        fragmentPresenter.onActivityCreated();
    }

    public void showPopup(View v,RewardModel reward) {
        String[] items;
        if(!reward.getIsBought()){
            items=getResources().getStringArray(R.array.miniRewardMenu);
        }
        else {
            items=getResources().getStringArray(R.array.rewardMenu);
        }
        PopupMenu menu = new PopupMenu(getContext(), v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menu.setGravity(Gravity.END);
        }
        for (String s : items) {
            menu.getMenu().add(s);
        }
        menu.setOnMenuItemClickListener(item -> {
            if(item.getTitle().equals("Edit")){
                showCreateRewardDialog(reward.getId());
            }
            else if (item.getTitle().equals("Delete")){
                fragmentPresenter.deleteReward(reward.getId());
            }
            else{
                fragmentPresenter.cancelPurchase(reward.getId());
            }
            return true;
        });
        menu.show();
    }

    public void showCreateRewardDialog(String rewardId){
        createRewardDialogBox = new Dialog(getContext());
        createRewardDialogBox.setContentView(R.layout.edit_reward);
        createRewardDialogBox.show();
        cancelRewardDialogBoxBtn= createRewardDialogBox.findViewById(R.id.cancel_reward_dialog_box);
        saveRewardDialogBoxBtn= createRewardDialogBox.findViewById(R.id.save_reward_dialog_box);
        rewardNameEditText= createRewardDialogBox.findViewById(R.id.reward_name_edit_text);
        rewardPriceEditText= createRewardDialogBox.findViewById(R.id.reward_price_edit_text);
        fragmentPresenter.setDataToRewardDialogBox(rewardId,rewardNameEditText,rewardPriceEditText);
        cancelRewardDialogBoxBtn.setOnClickListener(v -> cancelCreateRewardDialog());
        saveRewardDialogBoxBtn.setOnClickListener(v->{
            if(!TextUtils.isEmpty(rewardNameEditText.getText()) && !TextUtils.isEmpty(rewardPriceEditText.getText())){
                fragmentPresenter.onSave(rewardNameEditText.getText().toString(),Integer.parseInt(rewardPriceEditText.getText().toString()),rewardId);
                createRewardDialogBox.cancel();
            }
            else{
                Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.msg_required_fields_empty), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }

    public void cancelCreateRewardDialog(){
        createRewardDialogBox.cancel();
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
    public void setDataToAdapter(List<RewardModel> rewards) {
        rewardAdapter.emptyData();
        for (int i = 0; i < rewards.size(); i++) {
            rewardAdapter.bindViewModel(rewards.get(i));
        }
    }

    @Override
    public void showContentLayout() {

    }

    @Override
    public void hideContentLayout(){
    }

    @Override
    public void showMessageLayout() {

    }

    @Override
    public void hideMessageLayout() {

    }

    @Override
    public void onRewardClick(View v,RewardModel reward) {
       this.showPopup(v,reward);
    }

    @Override
    public void onBuyRewardClick(RewardModel reward) {
        if(!reward.getIsBought() && reward.getPercent()==100){
            this.fragmentPresenter.onBuy(reward);
        }
        else{
            Toast toast = Toast.makeText(getContext(), getResources().getString(R.string.not_enough_money), Toast.LENGTH_LONG);
            toast.show();
        }
    }
}