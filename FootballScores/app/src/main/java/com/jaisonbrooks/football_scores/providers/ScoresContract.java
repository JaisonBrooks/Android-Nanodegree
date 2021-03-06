package com.jaisonbrooks.football_scores.providers;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public final class ScoresContract {

    public static final String CONTENT_AUTHORITY = "com.jaisonbrooks.football_scores.footballscores";
    public static Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_SCORES = "scores";

    public interface Leagues {
        String BUNDESLIGA1 = "394";
        String BUNDESLIGA2 = "395";
        String LIGUE1 = "396";
        String LIGUE2 = "397";
        String PREMIER_LEAGUE = "398";
        String PRIMERA_DIVISION = "399";
        String SEGUNDA_DIVISION = "400";
        String SERIE_A = "401";
        String PRIMERA_LIGA = "402";
        String BUNDESLIGA3 = "403";
        String EREDIVISIE = "404";
        String CHAMPIONS_LEAGUE = "362";
    }

    public static final class ScoreEntry implements BaseColumns {
        //public static Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCORES).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORES;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SCORES;

        public static final String TABLE_NAME = "scores";
        public static final String COLUMN_LEAGUE = "league";
        public static final String COLUMN_LEAGUE_CAPTION = "league_caption";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_HOME = "home";
        public static final String COLUMN_AWAY = "away";
        public static final String COLUMN_HOME_CREST = "home_crest";
        public static final String COLUMN_AWAY_CREST = "away_crest";
        public static final String COLUMN_HOME_GOALS = "home_goals";
        public static final String COLUMN_AWAY_GOALS = "away_goals";
        public static final String COLUMN_MATCH_ID = "match_id";
        public static final String COLUMN_MATCH_DAY = "match_day";

        public static Uri buildScoreWithLeague() {
            return BASE_CONTENT_URI.buildUpon().appendPath("league").build();
        }

        public static Uri buildScoreWithId() {
            return BASE_CONTENT_URI.buildUpon().appendPath("id").build();
        }

        public static Uri buildScoreWithDate() {
            return BASE_CONTENT_URI.buildUpon().appendPath("date").build();
        }
    }
}
