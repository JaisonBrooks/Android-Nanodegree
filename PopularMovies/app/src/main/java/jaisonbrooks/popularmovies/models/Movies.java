package jaisonbrooks.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public class Movies implements Parcelable {
    public ArrayList<Movie> results = new ArrayList<>();

    public Movies(){}

    public Movies(Parcel in) {
        in.readTypedList(results, Movie.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }

    public static final Parcelable.Creator<Movies> CREATOR = new Parcelable.Creator<Movies>() {
        @Override
        public Movies createFromParcel(Parcel source) {
            return new Movies(source);
        }

        @Override
        public Movies[] newArray(int size) {
            return new Movies[size];
        }
    };
}
