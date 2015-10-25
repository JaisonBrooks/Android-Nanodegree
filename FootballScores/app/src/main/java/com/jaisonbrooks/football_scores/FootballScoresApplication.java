package com.jaisonbrooks.football_scores;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class FootballScoresApplication extends Application {

    public RefWatcher refWatcher;

    public static FootballScoresApplication get(Context context) {
        return (FootballScoresApplication) context.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        refWatcher = LeakCanary.install(this);

    }


}
