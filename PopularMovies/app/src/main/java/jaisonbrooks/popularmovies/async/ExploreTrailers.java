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

import jaisonbrooks.popularmovies.interfaces.OnTrailers;
import jaisonbrooks.popularmovies.models.MovieTrailers;

/**
 * Created by jbrooks on 8/23/15.
 */
public class ExploreTrailers extends AsyncTask<String, Void, MovieTrailers> {

    OnTrailers mCallback;

    public ExploreTrailers(OnTrailers onTrailers) {
        mCallback = onTrailers;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }


    @Override
    protected MovieTrailers doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader bufferedReader = null;
        Gson gson;
        GsonBuilder gsonBuilder;

        try {
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http").authority("api.themoviedb.org").appendPath("3").appendPath("movie").appendPath(params[0]).appendPath("videos").appendQueryParameter("language", "en").appendQueryParameter("api_key", "7fa82d7aa48d5cc30a64b80870546857");

            URL url = new URL(builder.build().toString());

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            int status = urlConnection.getResponseCode();
            switch(status) {
                case 200:
                    bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuffer buffer = new StringBuffer();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        buffer.append(line).append("\n");
                    }
                    if (buffer.length() == 0) {
                        return null;
                    }

                    gsonBuilder = new GsonBuilder();
                    gson = gsonBuilder.create();

                    MovieTrailers movieTrailers = gson.fromJson(buffer.toString(), MovieTrailers.class);

                    return movieTrailers;

                case 500:
                    Log.e("Explore Trailers", "There was a 500 error server-side");
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
                    Log.e("ExploreTrailers", "Error closing stream", e);
                }
            }
        }
        return null;
    }


    @Override
    protected void onPostExecute(MovieTrailers movieTrailers) {
        super.onPostExecute(movieTrailers);

        if (movieTrailers != null) {
            mCallback.OnTrailersSuccess(movieTrailers);
        } else {
            mCallback.OnTrailersError();
        }
    }
}
