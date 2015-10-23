package jaisonbrooks.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by jbrooks on 8/23/15.
 */

public class MovieTrailers implements Parcelable {

    public String id;
    public ArrayList<MovieTrailer> results = new ArrayList<>();

    public MovieTrailers(Parcel in) {
        in.readTypedList(results, MovieTrailer.CREATOR);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(results);
    }
    public static final Parcelable.Creator<MovieTrailers> CREATOR = new Parcelable.Creator<MovieTrailers>() {
        @Override
        public MovieTrailers createFromParcel(Parcel source) {
            return new MovieTrailers(source);
        }

        @Override
        public MovieTrailers[] newArray(int size) {
            return new MovieTrailers[size];
        }
    };
}
