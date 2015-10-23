package jaisonbrooks.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.HashSet;

import jaisonbrooks.popularmovies.adapter.MoviesAdapter;
import jaisonbrooks.popularmovies.async.ExploreMovies;
import jaisonbrooks.popularmovies.interfaces.OnMovies;
import jaisonbrooks.popularmovies.models.Movies;
import jaisonbrooks.popularmovies.ui.MarginDecoration;
import jaisonbrooks.popularmovies.utils.PreferenceManagerUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment{

    private Movies popularMovies;
    private MoviesAdapter moviesAdapter;
    private static final String OBJ_KEY = "movies";


    public static MoviesFragment newInstance(Movies movies) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putParcelable(OBJ_KEY, movies);
        fragment.setArguments(args);
        return fragment;
    }


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null || !savedInstanceState.containsKey(OBJ_KEY)) {
            popularMovies = getArguments().getParcelable(OBJ_KEY);
            if (popularMovies == null) {
                popularMovies = new Movies();
            }
        } else {
            popularMovies = savedInstanceState.getParcelable(OBJ_KEY);
        }
        PreferenceManagerUtil prefs = new PreferenceManagerUtil(getActivity());
        if (!prefs.has(PreferenceManagerUtil.PREF_MOVIE_FAVORITES)) {
            prefs.putStrSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES, new HashSet<String>());
        }
        moviesAdapter = new MoviesAdapter(getActivity(), popularMovies);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_movies, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new MarginDecoration(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(moviesAdapter);


        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(OBJ_KEY, popularMovies);
        super.onSaveInstanceState(outState);
    }

}
