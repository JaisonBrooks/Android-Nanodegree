package jaisonbrooks.popularmovies.async;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import jaisonbrooks.popularmovies.interfaces.OnMovies;
import jaisonbrooks.popularmovies.models.Movie;
import jaisonbrooks.popularmovies.models.Movies;
import jaisonbrooks.popularmovies.utils.PreferenceManagerUtil;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public class ExploreLocalMovies extends AsyncTask<String, Void, Movies> {

    OnMovies mCallback;
    Context mContext;
    Set<String> mFavoriteMovieIds;


    public ExploreLocalMovies(Context context) {
        mCallback = (OnMovies) context;
        mContext = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        PreferenceManagerUtil prefs = new PreferenceManagerUtil(mContext);
        mFavoriteMovieIds = prefs.getStrSet(PreferenceManagerUtil.PREF_MOVIE_FAVORITES);

        // I have a strange Bug somewhere around here.
    }

    @Override
    protected Movies doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        Gson gson;
        GsonBuilder gsonBuilder;
        Movies movies = new Movies();


            for (String mMovieId : mFavoriteMovieIds) {

                Log.e("ExploreLocalMovies", "Movie ID: " + mMovieId);

                try {

                    Uri.Builder builder = new Uri.Builder();
                    builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath("movie").appendPath(mMovieId.toString()).appendQueryParameter("api_key", "7fa82d7aa48d5cc30a64b80870546857");

                    Log.e("ExploreLocal", "This URL: " + builder.build().toString());


                    URL url = new URL(builder.build().toString());

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    int status = urlConnection.getResponseCode();
                    switch(status) {
                        case 200:
                            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                            StringBuilder buffer = new StringBuilder();
                            String line;
                            while ((line = bufferedReader.readLine()) != null) {
                                buffer.append(line).append("\n");
                            }
                            if (buffer.length() == 0) {
                                return null;
                            }

                            gsonBuilder = new GsonBuilder();
                            gson = gsonBuilder.create();

                            Movie movie = gson.fromJson(buffer.toString(), Movie.class);
                            movies.results.add(movie);
                    }

                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    return null;
                }
                finally {
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                    if (bufferedReader != null) {
                        try {
                            bufferedReader.close();
                        } catch (final IOException e) {
                            Log.e("ExploreMovies", "Error closing stream", e);
                        }
                    }

                }

            }

        return movies;
    }

    @Override
    protected void onPostExecute(Movies popularMovies) {
        super.onPostExecute(popularMovies);

        if (popularMovies != null) {
            mCallback.OnMoviesSuccess(popularMovies);
        } else {
            mCallback.OnMoviesError();
        }
    }
}
