package jaisonbrooks.popularmovies.interfaces;

import jaisonbrooks.popularmovies.models.MovieReviews;

/**
 * Created by jbrooks on 8/23/15.
 */
public interface OnReviews {
    void OnReviewsSuccess(MovieReviews reviews);
    void OnReviewsError();
}
