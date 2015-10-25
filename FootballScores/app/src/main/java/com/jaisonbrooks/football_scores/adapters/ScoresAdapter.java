package com.jaisonbrooks.football_scores.adapters;

import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.jaisonbrooks.football_scores.R;
import com.jaisonbrooks.football_scores.providers.ScoresContract;
import com.jaisonbrooks.football_scores.utils.SvgDecoder;
import com.jaisonbrooks.football_scores.utils.SvgDrawableTranscoder;
import com.jaisonbrooks.football_scores.utils.SvgSoftwareLayerSetter;
import com.jaisonbrooks.football_scores.utils.Utilities;

import java.io.InputStream;

import butterknife.Bind;
import butterknife.BindColor;
import butterknife.ButterKnife;
import butterknife.OnClick;

public final class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ViewHolder> {

    public interface OnScoreItemClickListener {
        void onScoreItemClicked(int position, View view);

        void onShareScoreItemClicked(String shareText);

        OnScoreItemClickListener DUMMY = new OnScoreItemClickListener() {
            @Override public void onScoreItemClicked(int position, View view) { }

            @Override public void onShareScoreItemClicked(String shareText) {}
        };
    }

    public interface ScoresQuery {
        String[] PROJECTION = {
                ScoresContract.ScoreEntry.COLUMN_LEAGUE,
                ScoresContract.ScoreEntry.COLUMN_HOME,
                ScoresContract.ScoreEntry.COLUMN_AWAY,
                ScoresContract.ScoreEntry.COLUMN_HOME_GOALS,
                ScoresContract.ScoreEntry.COLUMN_AWAY_GOALS,
                ScoresContract.ScoreEntry.COLUMN_MATCH_DAY
        };

        int LEAGUE = 0;
        int HOME = 1;
        int AWAY = 2;
        int HOME_GOALS = 3;
        int AWAY_GOALS = 4;
        int MATCH_DAY = 5;
    }

    private final Fragment mFragment;
    private final LayoutInflater mInflater;
    private final GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder;

    private Cursor mCursor;
    private OnScoreItemClickListener mListener = OnScoreItemClickListener.DUMMY;

    public ScoresAdapter(Fragment fragment) {
        mInflater = LayoutInflater.from(fragment.getActivity());
        mFragment = fragment;

        requestBuilder = Glide.with(fragment)
                .using(Glide.buildStreamModelLoader(Uri.class, fragment.getActivity()), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(R.color.primary_light)
                .error(R.drawable.ic_people_outline_black_24dp)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    public ScoresAdapter setListener(OnScoreItemClickListener listener) {
        mListener = listener;
        return this;
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    @Override
    public int getItemCount() {
        return mCursor != null ? mCursor.getCount() : 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_score, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Resources res = mFragment.getResources();
        mCursor.moveToPosition(position);

        holder.mLeagueName.setText(Utilities.getLeague(res, mCursor.getInt(ScoresQuery.LEAGUE)));
        holder.mHomeName.setText(mCursor.getString(ScoresQuery.HOME));
        holder.mAwayName.setText(mCursor.getString(ScoresQuery.AWAY));
        holder.mMatchDay.setText(res.getString(R.string.match_day,
                mCursor.getInt(ScoresQuery.MATCH_DAY)));
        holder.mScore.setText(Utilities.getScores(res,
                mCursor.getInt(ScoresQuery.HOME_GOALS),
                mCursor.getInt(ScoresQuery.AWAY_GOALS)));

        holder.mHomeCrest.setImageResource(Utilities.getTeamCrestByTeamName(
                mCursor.getString(ScoresQuery.HOME)));
        holder.mAwayCrest.setImageResource(Utilities.getTeamCrestByTeamName(
                mCursor.getString(ScoresQuery.AWAY)));

//        Uri uri = Uri.parse(mCursor.getString(ScoresQuery.HOME_CREST));
//        requestBuilder
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .load(uri)
//                .into(holder.mHomeCrest);
//
//        uri = Uri.parse(mCursor.getString(ScoresQuery.AWAY_CREST));
//        requestBuilder
//                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .load(uri)
//                .into(holder.mAwayCrest);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.score_item_league) TextView mLeagueName;
        @Bind(R.id.score_item_home_name) TextView mHomeName;
        @Bind(R.id.score_item_away_name) TextView mAwayName;
        @Bind(R.id.score_item_matchday) TextView mMatchDay;
        @Bind(R.id.score_item_score) TextView mScore;
        @Bind(R.id.score_item_home_crest) ImageView mHomeCrest;
        @Bind(R.id.score_item_away_crest) ImageView mAwayCrest;

        @BindColor(R.color.secondary_text) int mTintColor;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            DrawableCompat.setTint(mMatchDay.getCompoundDrawables()[0], mTintColor);
            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    mListener.onScoreItemClicked(getAdapterPosition(), v);
                }
            });
        }

        @OnClick(R.id.share_button) void onShare() {
            mListener.onShareScoreItemClicked(mFragment.getString(R.string.score_share_template,
                    mHomeName.getText(), mScore.getText(), mAwayName.getText()));
        }
    }
}
