package jaisonbrooks.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.HashSet;

import jaisonbrooks.popularmovies.adapter.MoviesAdapter;
import jaisonbrooks.popularmovies.async.ExploreLocalMovies;
import jaisonbrooks.popularmovies.async.ExploreMovies;
import jaisonbrooks.popularmovies.interfaces.OnMovies;
import jaisonbrooks.popularmovies.models.Movies;
import jaisonbrooks.popularmovies.ui.MarginDecoration;
import jaisonbrooks.popularmovies.utils.PreferenceManagerUtil;

public class MainActivity extends AppCompatActivity implements OnMovies {

    private Movies popularMovies;
    private MoviesAdapter moviesAdapter;
    private static final String OBJ_KEY = "popular_movies";
    private String sort_type = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sort_option_labels, R.layout.dropdown_title);
        adapter.setDropDownViewResource(R.layout.dropdown_list);
        final String[] arrays = getResources().getStringArray(R.array.sort_option_values);
        Spinner mNavigationSpinner = new Spinner(getSupportActionBar().getThemedContext());
        mNavigationSpinner.setAdapter(adapter);
        mNavigationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sort_type = arrays[position];

                if (sort_type.equals("local_favorites")) {
                    // I know its clunky, but it'll do for now
                    new ExploreLocalMovies(MainActivity.this).execute();
                } else {
                    new ExploreMovies(MainActivity.this).execute(sort_type);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(findViewById(android.R.id.content), "Nothing was Selected", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.addView(mNavigationSpinner);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        //

        if (savedInstanceState == null || !savedInstanceState.containsKey(OBJ_KEY)) {
            new ExploreMovies(this).execute(sort_type);
        } else {
            sort_type = savedInstanceState.getString(getString(R.string.pref_sort_key));
            popularMovies = savedInstanceState.getParcelable(OBJ_KEY);
        }

        // Validate whether we have created a local favorite preference before, if not, create an empty string hashset
        PreferenceManagerUtil prefs = new PreferenceManagerUtil(MainActivity.this);
        if (!prefs.has(PreferenceManagerUtil.PREF_MOVIE_FAVORITES)) {
            prefs.putStrSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES, new HashSet<String>());
        }

        moviesAdapter = new MoviesAdapter(MainActivity.this, popularMovies);
        initRecyclerView();
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(OBJ_KEY, popularMovies);
        outState.putString(getString(R.string.pref_sort_key), sort_type);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
//            startActivityForResult(settings, RESULT_OK);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    // NO LONGER using SETTINGS
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

//        if (resultCode == RESULT_OK) {
//            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
//            // Set the default sort key
//            default_sort = preferences.getString(getString(R.string.pref_sort_key), getString(R.string.pref_sort_option_default));
//            new ExploreMovies(this).execute(default_sort);
//            /*if (default_sort.equals("local_sort")) {
//                Collections.sort(popularMovies.results, new MovieSort());
//            } else {
//                new ExploreMovies(this).execute(default_sort);
//            }*/
//        }
    }

    @Override
    public void OnMoviesSuccess(Movies movies) {
        popularMovies = movies;
        moviesAdapter.addItems(popularMovies);
    }

    @Override
    public void OnMoviesError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_message), Snackbar.LENGTH_LONG).show();
    }
}
