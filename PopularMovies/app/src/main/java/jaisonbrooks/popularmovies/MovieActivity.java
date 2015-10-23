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


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import jaisonbrooks.popularmovies.models.Movie;

public class MovieActivity extends AppCompatActivity {

    private Movie movie;
    //private MovieReviews movieReviews;
    //private MovieTrailers movieTrailers;


    private static final String MOVIE_KEY = "movie";
    //private static final String MOVIE_REVIEWS_KEY = "movie_reviews";
    //private static final String MOVIE_TRAILERS_KEY = "movie_trailers";

    // Layout components
    private LinearLayout trailersParent = null;
    private LinearLayout reviewsParent = null;

    //private PreferenceManagerUtil prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);


        if (savedInstanceState == null || !savedInstanceState.containsKey(MOVIE_KEY)) {
            Intent intent = getIntent();
            movie = intent.getParcelableExtra(MOVIE_KEY);

        } else {
            movie = savedInstanceState.getParcelable(MOVIE_KEY);
        }

        getFragmentManager().beginTransaction()
                .add(R.id.movie_container, MovieFragment.newInstance(movie))
                .commit();
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

//    @Override
//    public void OnTrailersSuccess(MovieTrailers trailers) {
//
//        movieTrailers = trailers;
//
//        if (trailers.results.size() == 0) {
//
//            TextView noResults = new TextView(MovieActivity.this);
//            noResults.setText(R.string.detail_trailers_none);
//            noResults.setVisibility(View.VISIBLE);
//            reviewsParent.addView(noResults);
//
//        } else {
//
//            LayoutInflater layoutInflater = LayoutInflater.from(MovieActivity.this);
//
//            for (final MovieTrailer trailer : trailers.results) {
//
//                View rowItem = layoutInflater.inflate(R.layout.comp_trailer_item, null, false);
//                assert rowItem != null;
//
//                LinearLayout item = (LinearLayout) rowItem.findViewById(R.id.trailer_parent);
//                item.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        AlertDialog.Builder alert = new AlertDialog.Builder(MovieActivity.this);
//                        alert.setTitle(getString(R.string.detail_alert_trailer_title));
//                        alert.setMessage(getString(R.string.detail_alert_trailer_body));
//                        alert.setPositiveButton(getString(R.string.detail_alert_trailer_btn_positive), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                openTrailer(trailer.key);
//                            }
//                        });
//                        alert.setNegativeButton(getString(R.string.detail_alert_trailer_btn_dismiss), new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        });
//                        alert.create().show();
//                    }
//                });
//                TextView name = (TextView) rowItem.findViewById(R.id.trailer_title);
//                name.setText(trailer.name);
//                trailersParent.addView(rowItem);
//            }
//        }
//    }
//
//    @Override
//    public void OnTrailersError() {
//        Snackbar.make(findViewById(android.R.id.content), getString(R.string.detail_trailers_error), Snackbar.LENGTH_LONG).show();
//    }
//
//    // Open Trailer via Youtube
//    private void openTrailer(String id){
//        try{
//            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
//            startActivity(intent);
//        }catch (ActivityNotFoundException ex){
//            Intent intent=new Intent(Intent.ACTION_VIEW,
//                    Uri.parse("http://www.youtube.com/watch?v="+id));
//            startActivity(intent);
//        }
//    }
//
//
//    @Override
//    public void OnReviewsSuccess(MovieReviews reviews) {
//        movieReviews = reviews;
//
//        if (reviews.results.size() == 0) {
//
//            TextView noResults = new TextView(MovieActivity.this);
//            noResults.setText(R.string.detail_reviews_none);
//            noResults.setVisibility(View.VISIBLE);
//            reviewsParent.addView(noResults);
//
//        } else {
//
//            LayoutInflater layoutInflater = LayoutInflater.from(MovieActivity.this);
//
//            for (final MovieReview review : reviews.results) {
//
//                View rowItem = layoutInflater.inflate(R.layout.comp_review_item, null, false);
//                assert rowItem != null;
//
//                LinearLayout item = (LinearLayout) rowItem.findViewById(R.id.review_base);
//                item.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent=new Intent(Intent.ACTION_VIEW,
//                                Uri.parse(review.url));
//                        startActivity(intent);
//                    }
//                });
//
//                TextView content = (TextView) rowItem.findViewById(R.id.content);
//                content.setText(review.content);
//                TextView author = (TextView) rowItem.findViewById(R.id.author);
//                author.setText(" - " + review.author);
//
//                reviewsParent.addView(rowItem);
//            }
//        }
//
//
//
//    }
//
//    @Override
//    public void OnReviewsError() {
//        Snackbar.make(findViewById(android.R.id.content), getString(R.string.detail_reviews_error), Snackbar.LENGTH_LONG).show();
//    }
}
