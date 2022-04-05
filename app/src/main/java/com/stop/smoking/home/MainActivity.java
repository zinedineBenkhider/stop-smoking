package com.stop.smoking.home;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.stop.smoking.MenuActivity;
import com.stop.smoking.R;
import com.stop.smoking.home.fragment.ProgressFragment;
import com.stop.smoking.home.fragment.TrophyFragment;

public class MainActivity extends MenuActivity {

    private BottomNavigationView bottom_navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        bottom_navigation.setOnNavigationItemSelectedListener(listener);
        configureToolbar();
        Fragment fragment = TrophyFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

    }

    BottomNavigationView.OnNavigationItemSelectedListener listener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case (R.id.trophy_nav_item):
                    selectedFragment = TrophyFragment.newInstance();
                    break;
                case (R.id.progress_nav_item):
                    selectedFragment = ProgressFragment.newInstance();
                    break;
                case (R.id.reward_nav_item):
                    selectedFragment = TrophyFragment.newInstance();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };


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