package com.stop.smoking.home.fragment;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
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
import com.stop.smoking.home.presenter.model.Reward;
import java.util.List;

public class RewardFragment extends Fragment implements RewardFragmentContract.RewardFragmentView, RewardActionInterface {
    private static RewardFragment INSTANCE = null;
    private Dialog createRewardDialogBox;
    private Button cancelRewardDialogBoxBtn;
    private RewardAdapter rewardAdapter;
    private RecyclerView recyclerView;
    private View rootView;
    private ProgressBar progressBar;
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
        rewardAdapter = new RewardAdapter(this);
        recyclerView = rootView.findViewById(R.id.recycler_view_reward);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rewardAdapter);
        RewardFragmentPresenterImpl fragmentPresenter = new RewardFragmentPresenterImpl(this);
        fragmentPresenter.onActivityCreated();
    }

    public void showPopup(View v) {
        String[] items=getResources().getStringArray(R.array.rewardMenu);
        PopupMenu menu = new PopupMenu(getContext(), v);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            menu.setGravity(Gravity.END);
        }
        for (String s : items) {
            menu.getMenu().add(s);
        }
        menu.setOnMenuItemClickListener(item -> {
            if(item.getTitle().equals("Edit")){
                showCreateRewardDialog();
            };
            return true;
        });
        menu.show();
    }

    public void showCreateRewardDialog(){
        createRewardDialogBox = new Dialog(getContext());
        createRewardDialogBox.setContentView(R.layout.edit_reward);
        createRewardDialogBox.show();
        cancelRewardDialogBoxBtn= createRewardDialogBox.findViewById(R.id.cancel_reward_dialog_box);
        cancelRewardDialogBoxBtn.setOnClickListener(v -> cancelCreateRewardDialog());
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
    public void setDataToAdapter(List<Reward> rewards) {
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
    public void onRewardClick(View v,Reward reward) {
       this.showPopup(v);
    }
}