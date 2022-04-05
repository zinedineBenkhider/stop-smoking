package com.stop.smoking;

import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MenuActivity extends AppCompatActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}