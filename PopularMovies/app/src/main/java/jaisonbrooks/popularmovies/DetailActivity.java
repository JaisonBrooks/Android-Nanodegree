package jaisonbrooks.popularmovies;

/**
 * *UDACITY REVIEW*
 *
 *
 * I was confused when in the rubric when it says we have to provide a sort function by "Highest Rating".
 * I didnt know whether this sort was supposed to reorder the already retrieved data from the Popularity endpoint
 * or whether this was a separate API query using the (sort_by = vote_average.des or the vote_count.desc).
 *
 * Since i was unclear, i provided a few extra options in the Settings to hopefully cover all possible avenues. Thanks.
 *
 * I really hope this doesnt take away points due to this confusion. :?
 *
 *
 */


import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

public class DetailActivity extends AppCompatActivity implements OnTrailers, OnReviews {

    private Movie movie;
    private MovieReviews movieReviews;
    private MovieTrailers movieTrailers;


    private static final String MOVIE_KEY = "movie";
    private static final String MOVIE_REVIEWS_KEY = "movie_reviews";
    private static final String MOVIE_TRAILERS_KEY = "movie_trailers";

    // Layout components
    private LinearLayout trailersParent = null;
    private LinearLayout reviewsParent = null;

    private PreferenceManagerUtil prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        prefs = new PreferenceManagerUtil(DetailActivity.this);

        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_KEY)) {
            Intent intent = getIntent();
            movie = intent.getParcelableExtra(MOVIE_KEY);

            new ExploreReviews(DetailActivity.this).execute(Integer.toString(movie.id));
            new ExploreTrailers(DetailActivity.this).execute(Integer.toString(movie.id));

        } else {
            movie = savedInstanceState.getParcelable(MOVIE_KEY);
            movieReviews = savedInstanceState.getParcelable(MOVIE_REVIEWS_KEY);
            movieTrailers = savedInstanceState.getParcelable(MOVIE_TRAILERS_KEY);
        }

        setupDetails();

    }

    public void setupDetails() {

        // Title
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(movie.title);
        //

        // popularity
        final TextView popularity = (TextView) findViewById(R.id.popularity);
        popularity.setText(String.format("%.1f",movie.popularity));
        //

        // Rating
        final TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText(String.format("%.2f", movie.vote_average) + " / 10 - " + movie.vote_count + " votes");
        //


        // Backdrop
        ImageView backdrop = (ImageView) findViewById(R.id.backdrop);
        Picasso.with(this)
                .load(IMDB.IMG_BASE + IMDB.SIZE_W500 + movie.backdrop_path)
                .fit().centerCrop().transform(PaletteTransformation.instance())
                .into(backdrop, new PaletteTransformation.PaletteCallback(backdrop) {
                    @Override
                    public void onError() {
                        Log.e("DetailsActivity", "Errors creating palette from image");
                    }

                    @Override
                    public void onSuccess(Palette palette) {
                        collapsingToolbar.setContentScrimColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));
                        popularity.setTextColor(palette.getVibrantColor(getResources().getColor(R.color.primary)));

                    }
                });
        //

        // Poster
        ImageView poster = (ImageView) findViewById(R.id.poster_detail);
        Picasso.with(this).load(IMDB.IMG_BASE + IMDB.SIZE_W185 + movie.poster_path).into(poster);


        // Overview
        TextView overview = (TextView) findViewById(R.id.overview);
        overview.setText(movie.overview);
        //

        // Release Date
        TextView release_date =(TextView) findViewById(R.id.release_date);
        release_date.setText(movie.release_date);
        //

        // Trailers
        trailersParent = (LinearLayout) findViewById(R.id.trailers_parent_layout);
        //

        // Reviews
        reviewsParent = (LinearLayout) findViewById(R.id.reviews_parent_layout);
        //

        // Favorite fab
        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
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



    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(MOVIE_KEY, movie);
        super.onSaveInstanceState(outState);
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnTrailersSuccess(MovieTrailers trailers) {

        movieTrailers = trailers;

        if (trailers.results.size() == 0) {

            TextView noResults = new TextView(DetailActivity.this);
            noResults.setText(R.string.detail_trailers_none);
            noResults.setVisibility(View.VISIBLE);
            reviewsParent.addView(noResults);

        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);

            for (final MovieTrailer trailer : trailers.results) {

                View rowItem = layoutInflater.inflate(R.layout.comp_trailer_item, null, false);
                assert rowItem != null;

                LinearLayout item = (LinearLayout) rowItem.findViewById(R.id.trailer_parent);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(DetailActivity.this);
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
                });
                TextView name = (TextView) rowItem.findViewById(R.id.trailer_title);
                name.setText(trailer.name);
                trailersParent.addView(rowItem);
            }
        }
    }

    @Override
    public void OnTrailersError() {
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.detail_trailers_error), Snackbar.LENGTH_LONG).show();
    }

    // Open Trailer via Youtube
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


    @Override
    public void OnReviewsSuccess(MovieReviews reviews) {
        movieReviews = reviews;

        if (reviews.results.size() == 0) {

            TextView noResults = new TextView(DetailActivity.this);
            noResults.setText(R.string.detail_reviews_none);
            noResults.setVisibility(View.VISIBLE);
            reviewsParent.addView(noResults);

        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(DetailActivity.this);

            for (final MovieReview review : reviews.results) {

                View rowItem = layoutInflater.inflate(R.layout.comp_review_item, null, false);
                assert rowItem != null;

                LinearLayout item = (LinearLayout) rowItem.findViewById(R.id.review_base);
                item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(Intent.ACTION_VIEW,
                                Uri.parse(review.url));
                        startActivity(intent);
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
        Snackbar.make(findViewById(android.R.id.content), getString(R.string.detail_reviews_error), Snackbar.LENGTH_LONG).show();
    }
}
