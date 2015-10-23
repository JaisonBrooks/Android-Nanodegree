package jaisonbrooks.popularmovies;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import jaisonbrooks.popularmovies.async.ExploreLocalMovies;
import jaisonbrooks.popularmovies.async.ExploreMovies;
import jaisonbrooks.popularmovies.interfaces.OnMovies;
import jaisonbrooks.popularmovies.interfaces.OnMoviesClicked;
import jaisonbrooks.popularmovies.models.Movie;
import jaisonbrooks.popularmovies.models.Movies;

public class MoviesActivity extends AppCompatActivity implements OnMovies, OnMoviesClicked {

    boolean mTwoPane = false;
    private Movies popularMovies;
    private static final String OBJ_KEY = "popular_movies";
    private String sort_type = "popularity.desc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_core);

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
                    new ExploreLocalMovies(MoviesActivity.this).execute();
                } else {
                    new ExploreMovies(MoviesActivity.this).execute(sort_type);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Snackbar.make(findViewById(android.R.id.content), "Nothing was Selected", Snackbar.LENGTH_SHORT).show();
            }
        });
        toolbar.addView(mNavigationSpinner);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        if (savedInstanceState == null || !savedInstanceState.containsKey(OBJ_KEY)) {
            new ExploreMovies(this).execute(sort_type);
        } else {
            sort_type = savedInstanceState.getString(getString(R.string.pref_sort_key));
            popularMovies = savedInstanceState.getParcelable(OBJ_KEY);
        }

        FragmentManager fm = getFragmentManager();

        if (findViewById(R.id.movie_container) != null) {
            mTwoPane = true;

            MovieFragment df = (MovieFragment) fm.findFragmentByTag("movie");
            if (df == null) {
                df = MovieFragment.newInstance(new Movie());
                fm.beginTransaction().replace(R.id.movie_container, df, "movie").commit();
            }
        }

        MoviesFragment moviesFragment = (MoviesFragment) fm.findFragmentByTag("movies");
        if (moviesFragment == null) {
            moviesFragment = MoviesFragment.newInstance(popularMovies);
            fm.beginTransaction().replace(R.id.movies_container, moviesFragment, "movies").commit();
        }


    }

    @Override
    public void OnMoviesSuccess(Movies movies) {
        popularMovies = movies;

        FragmentManager fm = getFragmentManager();

        MoviesFragment moviesFragment = MoviesFragment.newInstance(movies);
        fm.beginTransaction().replace(R.id.movies_container, moviesFragment, "movies").commit();

        if (mTwoPane) {
            if (movies.results.size() == 0) {
                MovieFragment df = (MovieFragment) fm.findFragmentByTag("movie");
                fm.beginTransaction().remove(df).commit();
            } else {
                Movie movie = movies.results.get(0);
                MovieFragment df = MovieFragment.newInstance(movie);
                fm.beginTransaction().replace(R.id.movie_container, df, "movie").commit();
            }
        }
    }

    @Override
    public void OnMoviesError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.error_message), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void OnMovieClicked(Movie movie) {
        if (mTwoPane) {
            FragmentManager fm = getFragmentManager();
            MovieFragment df = MovieFragment.newInstance(movie);
            fm.beginTransaction().replace(R.id.movie_container, df, "movie").commit();
        } else {
            Intent intent = new Intent(MoviesActivity.this, MovieActivity.class);
            intent.putExtra("movie", movie);
            startActivity(intent);
        }
    }
}
