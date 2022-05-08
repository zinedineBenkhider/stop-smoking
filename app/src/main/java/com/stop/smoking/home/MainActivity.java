package com.stop.smoking.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.stop.smoking.R;
import com.stop.smoking.home.activity.ProfileActivity;
import com.stop.smoking.home.fragment.HealthFragment;
import com.stop.smoking.home.fragment.ProgressFragment;
import com.stop.smoking.home.fragment.RewardFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottom_navigation;
    private FloatingActionButton buttonAddReward;
    Fragment selectedFragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        buttonAddReward= findViewById(R.id.btn_new_award_activity_main);
        buttonAddReward.setVisibility(View.GONE);
        bottom_navigation.setOnItemSelectedListener(listener);
        configureToolbar();
        Fragment fragment = ProgressFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        bottom_navigation.setSelectedItemId(R.id.progress_nav_item);
        buttonAddReward.setOnClickListener(v -> {
            RewardFragment rewardFragment=(RewardFragment)selectedFragment;
            rewardFragment.showCreateRewardDialog(null);
        });
    }

    @Nullable
    BottomNavigationView.OnItemSelectedListener  listener = new BottomNavigationView.OnItemSelectedListener () {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            buttonAddReward.setVisibility(View.GONE);
            switch (item.getItemId()) {
     /*           case (R.id.trophy_nav_item):
                    selectedFragment = TrophyFragment.newInstance();
                    break;*/
                case (R.id.progress_nav_item):
                    selectedFragment = ProgressFragment.newInstance();
                    break;
                case (R.id.reward_nav_item):
                    selectedFragment = RewardFragment.newInstance();
                    buttonAddReward.setVisibility(View.VISIBLE);
                    break;
                case (R.id.health_nav_item):
                    selectedFragment = HealthFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            this.startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void configureToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        TextView toolBarTitle = findViewById(R.id.title_tool_bar);
        toolBarTitle.setText(R.string.app_name);
        AppCompatImageButton backBtn = findViewById(R.id.back_btn_tool_bar);
        backBtn.setVisibility(View.GONE);
    }


}