package com.jaisonbrooks.alexandria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;


public final class MainActivity extends BaseActivity {
    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Bind(R.id.toolbar) Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(mToolbar);

        // TODO - Bug that causes intent loop between AddActivity and MainActivity. Not good :/
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//        if (preferences.getString("pref_startFragment", "0").equals("1")) {
//            Intent intent = new Intent(this, AddActivity.class);
//            startActivity(intent);
//        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (R.id.action_about == id) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.add_book_fab)
    public void onAddBookClicked() {
        startActivity(new Intent(this, AddActivity.class));
    }
}