package jaisonbrooks.popularmovies.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import jaisonbrooks.popularmovies.DetailActivity;
import jaisonbrooks.popularmovies.R;
import jaisonbrooks.popularmovies.interfaces.OnMoviesClicked;
import jaisonbrooks.popularmovies.models.IMDB;
import jaisonbrooks.popularmovies.models.Movies;
import jaisonbrooks.popularmovies.ui.PaletteTransformation;

/**
 * *  Author | Jaison Brooks
 * *  Date   | 7/12/15
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private Movies mMovies;
    private Context mContext;
    OnMoviesClicked mCallback;

    public MoviesAdapter(Context context, Movies popularMovies) {
        mContext = context;
        mCallback = (OnMoviesClicked) mContext;
        mMovies = popularMovies;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);

        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.title.setTag(viewHolder);
        viewHolder.poster.setTag(viewHolder);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.title.setText(mMovies.results.get(position).title);
        Picasso.with(mContext)
                .load(IMDB.IMG_BASE + IMDB.SIZE_W185 + mMovies.results.get(position).poster_path)
                .fit().centerCrop()
                .transform(PaletteTransformation.instance())
                .into(viewHolder.poster, new PaletteTransformation.PaletteCallback(viewHolder.poster) {
                    @Override
                    public void onError() {
                        Log.e("MoviesAdapter", "Errors creating palette from image");
                    }

                    @Override
                    public void onSuccess(Palette palette) {
                        viewHolder.title_base.setBackgroundColor(palette.getVibrantColor(mContext.getResources().getColor(R.color.dark_grey)));
                    }
                });
    }


    @Override
    public int getItemCount() {
        if (mMovies == null) {
            return 0;
        }
        return mMovies.results.size();
    }

    public void addItems(Movies parcel) {
        mMovies = parcel;
        notifyDataSetChanged();
    }

    public void removeItems() {
        mMovies.results.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView title;
        public LinearLayout title_base;
        public ImageView poster;
        public LinearLayout base;

        public ViewHolder(View x) {
            super(x);
            title = (TextView) x.findViewById(R.id.title);
            title_base = (LinearLayout) x.findViewById(R.id.title_base);
            poster = (ImageView) x.findViewById(R.id.poster);
            base = (LinearLayout) x.findViewById(R.id.movie_base);
            base.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCallback.OnMovieClicked(mMovies.results.get(getAdapterPosition()));
        }

//        @Override
//        public void onClick(View v) {
//            Intent intent = new Intent(v.getContext(), DetailActivity.class);
//            intent.putExtra("popular_movie", mMovies.results.get(getAdapterPosition()));
//            v.getContext().startActivity(intent);
//        }
    }
}
