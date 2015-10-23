package jaisonbrooks.popularmovies;


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jaisonbrooks.popularmovies.async.ExploreReviews;
import jaisonbrooks.popularmovies.async.ExploreTrailers;
import jaisonbrooks.popularmovies.interfaces.OnReviews;
import jaisonbrooks.popularmovies.interfaces.OnTrailers;
import jaisonbrooks.popularmovies.models.IMDB;
import jaisonbrooks.popularmovies.models.Movie;
import jaisonbrooks.popularmovies.models.MovieReview;
import jaisonbrooks.popularmovies.models.MovieReviews;
import jaisonbrooks.popularmovies.models.MovieTrailer;
import jaisonbrooks.popularmovies.models.MovieTrailers;
import jaisonbrooks.popularmovies.ui.PaletteTransformation;
import jaisonbrooks.popularmovies.utils.PreferenceManagerUtil;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {


    private Movie movie;
    private PreferenceManagerUtil prefs;

    public static MovieFragment newInstance(Movie movie) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putParcelable("movie", movie);
        fragment.setArguments(args);
        return fragment;
    }

    public MovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movie = getArguments().getParcelable("movie");
        }

        prefs = new PreferenceManagerUtil(getActivity());
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container,savedInstanceState);

        View root = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // Title
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) root.findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movie.title);
        //

        // popularity
        final TextView popularity = (TextView) root.findViewById(R.id.popularity);
        popularity.setText(String.format("%.1f", movie.popularity));
        //

        // Rating
        final TextView rating = (TextView) root.findViewById(R.id.rating);
        rating.setText(String.format("%.2f", movie.vote_average) + " / 10 - " + movie.vote_count + " votes");
        //


        // Backdrop
        ImageView backdrop = (ImageView) root.findViewById(R.id.backdrop);
        Picasso.with(getActivity())
                .load(IMDB.IMG_BASE + IMDB.SIZE_W500 + movie.backdrop_path)
                .fit().centerCrop().transform(PaletteTransformation.instance())
                .into(backdrop, new PaletteTransformation.PaletteCallback(backdrop) {
                    @Override
                    public void onError() {
                        Log.e("DetailsActivity", "Errors creating palette from image");
                    }

                    @Override
                    public void onSuccess(Palette palette) {
                        /**
                         * NOTE: Had to remove the cool palette stuff due to issues with the act /fragment master view implmentation
                         */
                        //collapsingToolbar.setContentScrimColor(palette.getVibrantColor(getActivity().getColor(R.color.primary)));
                       // popularity.setTextColor(palette.getVibrantColor(getActivity().getResources().getColor(R.color.primary)));

                    }
                });
        //

        // Poster
        ImageView poster = (ImageView) root.findViewById(R.id.poster_detail);
        Picasso.with(getActivity()).load(IMDB.IMG_BASE + IMDB.SIZE_W185 + movie.poster_path).into(poster);


        // Overview
        TextView overview = (TextView) root.findViewById(R.id.overview);
        overview.setText(movie.overview);
        //

        // Release Date
        TextView release_date =(TextView) root.findViewById(R.id.release_date);
        release_date.setText(movie.release_date);
        //

        // Trailers
        final LinearLayout trailersParent = (LinearLayout) root.findViewById(R.id.trailers_parent_layout);
        final ExploreTrailers exploreTrailers = new ExploreTrailers(new OnTrailers() {
            @Override
            public void OnTrailersSuccess(MovieTrailers trailers) {
                if (trailers.results.size() == 0) {

                    TextView noResults = new TextView(getActivity());
                    noResults.setText(R.string.detail_trailers_none);
                    noResults.setVisibility(View.VISIBLE);
                    trailersParent.addView(noResults);

                } else {


                    /**
                     * Not the best method im sure, this was  a quick and dirty solution (not my best work)
                     */

                    for (final MovieTrailer trailer : trailers.results) {

                        View rowItem = inflater.inflate(R.layout.comp_trailer_item, null, false);
                        assert rowItem != null;

                        LinearLayout item = (LinearLayout) rowItem.findViewById(R.id.trailer_parent);
                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickTrailer(trailer);
                            }
                        });
                        TextView name = (TextView) rowItem.findViewById(R.id.trailer_title);
                        name.setText(trailer.name);
                        trailersParent.addView(rowItem);
                    }
                }
            }

            @Override
            public void OnTrailersError() {
                //Snackbar.make(getActivity().findViewById(android.R.id.content), getActivity().getResources().getString(R.string.detail_trailers_error), Snackbar.LENGTH_LONG).show();
            }
        });
        exploreTrailers.execute(Integer.toString(movie.id));
        //

        // Reviews
        final LinearLayout reviewsParent = (LinearLayout) root.findViewById(R.id.reviews_parent_layout);
        ExploreReviews exploreReviews = new ExploreReviews(new OnReviews() {
            @Override
            public void OnReviewsSuccess(MovieReviews reviews) {
                if (reviews.results.size() == 0) {

                    TextView noResults = new TextView(getActivity());
                    noResults.setText(R.string.detail_reviews_none);
                    noResults.setVisibility(View.VISIBLE);
                    reviewsParent.addView(noResults);

                } else {


                    /**
                     * Not the best method im sure, this was  a quick and dirty solution (not my best work)
                     */

                    for (final MovieReview review : reviews.results) {

                        View rowItem = inflater.inflate(R.layout.comp_review_item, null, false);
                        assert rowItem != null;

                        LinearLayout item = (LinearLayout) rowItem.findViewById(R.id.review_base);
                        item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickReview(review);
                            }
                        });

                        TextView content = (TextView) rowItem.findViewById(R.id.content);
                        content.setText(review.content);
                        TextView author = (TextView) rowItem.findViewById(R.id.author);
                        author.setText(" - " + review.author);

                        reviewsParent.addView(rowItem);
                    }
                }
            }

            @Override
            public void OnReviewsError() {
                //Snackbar.make(getActivity().findViewById(android.R.id.content), getActivity().getResources().getString(R.string.detail_reviews_error), Snackbar.LENGTH_LONG).show();
            }
        });
        exploreReviews.execute(Integer.toString(movie.id));
        //

        // Favorite fab
        final FloatingActionButton fab = (FloatingActionButton) root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (prefs.hasStrInSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES, Integer.toString(movie.id))) {
                    prefs.removeStrFromSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES, Integer.toString(movie.id));
                    Snackbar.make(view, "Removed " + movie.title + " from Favorites", Snackbar.LENGTH_LONG).show();
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_white_24dp));
                } else {
                    prefs.addStrToSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES, Integer.toString(movie.id));
                    Snackbar.make(view, "Added " + movie.title + " to Favorites", Snackbar.LENGTH_LONG).show();
                    fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
                }
            }
        });
        if (prefs.hasStrInSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES, Integer.toString(movie.id))) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_white_24dp));
        }
        //


        return root;
    }

    private void openTrailer(String id){
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            startActivity(intent);
        }
    }

    private void onClickTrailer(final MovieTrailer trailer) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle(getString(R.string.detail_alert_trailer_title));
        alert.setMessage(getString(R.string.detail_alert_trailer_body));
        alert.setPositiveButton(getString(R.string.detail_alert_trailer_btn_positive), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openTrailer(trailer.key);
            }
        });
        alert.setNegativeButton(getString(R.string.detail_alert_trailer_btn_dismiss), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alert.create().show();
    }

    private void onClickReview(final MovieReview review) {
        Intent intent=new Intent(Intent.ACTION_VIEW,
                Uri.parse(review.url));
        startActivity(intent);
    }


}
