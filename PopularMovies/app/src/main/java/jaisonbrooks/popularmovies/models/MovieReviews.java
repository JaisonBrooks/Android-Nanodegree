package jaisonbrooks.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jbrooks on 8/23/15.
 */

public class MovieReviews implements Parcelable {

    public String id;
    public ArrayList<MovieReview> results = new ArrayList<>();

    public MovieReviews(Parcel in) {
        in.readTypedList(results, MovieReview.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
    public static final Creator<MovieReviews> CREATOR = new Creator<MovieReviews>() {
        @Override
        public MovieReviews createFromParcel(Parcel source) {
            return new MovieReviews(source);
        }

        @Override
        public MovieReviews[] newArray(int size) {
            return new MovieReviews[size];
        }
    };
}
