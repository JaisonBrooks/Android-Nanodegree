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

import jaisonbrooks.popularmovies.interfaces.OnMovies;
import jaisonbrooks.popularmovies.models.Movies;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public class ExploreMovies extends AsyncTask<String, Void, Movies> {

    OnMovies mCallback;

    public ExploreMovies(Context context) {
        mCallback = (OnMovies) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Movies doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        Gson gson;
        GsonBuilder gsonBuilder;

        try {
            //http://api.themoviedb.org/3/discover/movie
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath("discover").appendPath("movie").appendQueryParameter("sort_by",params[0]).appendQueryParameter("api_key", "7fa82d7aa48d5cc30a64b80870546857");

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

                    Movies popularMovies = gson.fromJson(buffer.toString(), Movies.class);

                    return popularMovies;

                case 500:
                    Log.e("Explore Movies", "There was a 500 error server-side");
                    return null;
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            return null;
        } finally {
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
        return null;
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
