package jaisonbrooks.popularmovies.models;

import java.util.Comparator;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/13/15
 */
public class MovieSort implements Comparator<Movie> {

    @Override
    public int compare(Movie left, Movie right) {
        if (left.vote_average < right.vote_average) return -1;
        if (left.vote_average > right.vote_average) return 1;

        return 0;
    }
}
