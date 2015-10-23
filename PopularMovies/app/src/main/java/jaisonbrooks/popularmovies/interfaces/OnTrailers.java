package jaisonbrooks.popularmovies.interfaces;

import jaisonbrooks.popularmovies.models.MovieTrailers;

/**
 * Created by jbrooks on 8/23/15.
 */
public interface OnTrailers {
    void OnTrailersSuccess(MovieTrailers trailers);
    void OnTrailersError();
}
