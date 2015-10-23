package jaisonbrooks.popularmovies.interfaces;

import jaisonbrooks.popularmovies.models.Movies;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public interface OnMovies {
    void OnMoviesSuccess(Movies popularMovies);
    void OnMoviesError();
}
