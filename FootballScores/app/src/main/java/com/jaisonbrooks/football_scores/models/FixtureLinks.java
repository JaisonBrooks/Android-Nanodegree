package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class FixtureLinks {

    @Expose
    private HrefWrapper self;
    @Expose
    @SerializedName("soccerseason")
    private HrefWrapper soccerSeason;
    @Expose
    private HrefWrapper homeTeam;
    @Expose
    private HrefWrapper awayTeam;

    public HrefWrapper getSelf() {
        return self;
    }

    public FixtureLinks setSelf(HrefWrapper self) {
        this.self = self;
        return this;
    }

    public HrefWrapper getSoccerSeason() {
        return soccerSeason;
    }

    public FixtureLinks setSoccerSeason(HrefWrapper soccerSeason) {
        this.soccerSeason = soccerSeason;
        return this;
    }

    public HrefWrapper getHomeTeam() {
        return homeTeam;
    }

    public FixtureLinks setHomeTeam(HrefWrapper homeTeam) {
        this.homeTeam = homeTeam;
        return this;
    }

    public HrefWrapper getAwayTeam() {
        return awayTeam;
    }

    public FixtureLinks setAwayTeam(HrefWrapper awayTeam) {
        this.awayTeam = awayTeam;
        return this;
    }
}
