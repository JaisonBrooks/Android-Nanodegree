package com.jaisonbrooks.football_scores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
{
    public static int selectedMatchId;
    public static int currentFragment = 2;

    private final String TAG_FRAGMENT_MAIN = "my_main";
    private final String TAG_SELECTED_MATCH_ID = "selected_match";
    private final String TAG_PAGER_CURRENT = "pager_current";

    public static String LOG_TAG = "MainActivity";
    private final String SAVE_TAG = "Save Test";

    private PagerFragment pagerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(LOG_TAG, "Reached MainActivity onCreate");
        if (savedInstanceState == null) {
            pagerFragment = new PagerFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, pagerFragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_about)
        {
            Intent start_about = new Intent(this,AboutActivity.class);
            startActivity(start_about);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        Log.v(SAVE_TAG,"will save");
        Log.v(SAVE_TAG,"fragment: "+String.valueOf(pagerFragment.mPagerHandler.getCurrentItem()));
        Log.v(SAVE_TAG,"selected id: "+selectedMatchId);
        outState.putInt(TAG_PAGER_CURRENT, pagerFragment.mPagerHandler.getCurrentItem());
        outState.putInt(TAG_SELECTED_MATCH_ID, selectedMatchId);
        getSupportFragmentManager().putFragment(outState, TAG_FRAGMENT_MAIN, pagerFragment);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        Log.v(SAVE_TAG,"will retrieve");
        Log.v(SAVE_TAG,"fragment: "+String.valueOf(savedInstanceState.getInt(TAG_PAGER_CURRENT)));
        Log.v(SAVE_TAG,"selected id: "+savedInstanceState.getInt(TAG_SELECTED_MATCH_ID));
        currentFragment = savedInstanceState.getInt(TAG_PAGER_CURRENT);
        selectedMatchId = savedInstanceState.getInt(TAG_SELECTED_MATCH_ID);
        pagerFragment = (PagerFragment) getSupportFragmentManager().getFragment(savedInstanceState,TAG_FRAGMENT_MAIN);
        super.onRestoreInstanceState(savedInstanceState);
    }
}
