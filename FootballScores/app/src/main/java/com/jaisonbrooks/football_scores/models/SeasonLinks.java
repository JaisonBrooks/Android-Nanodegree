package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class SeasonLinks {

    @Expose
    private HrefWrapper self;
    @Expose
    private HrefWrapper teams;
    @Expose
    private HrefWrapper fixtures;
    @Expose
    private HrefWrapper leagueTable;

    public HrefWrapper getSelf() {
        return self;
    }

    public SeasonLinks setSelf(HrefWrapper self) {
        this.self = self;
        return this;
    }

    public HrefWrapper getTeams() {
        return teams;
    }

    public SeasonLinks setTeams(HrefWrapper teams) {
        this.teams = teams;
        return this;
    }

    public HrefWrapper getFixtures() {
        return fixtures;
    }

    public SeasonLinks setFixtures(HrefWrapper fixtures) {
        this.fixtures = fixtures;
        return this;
    }

    public HrefWrapper getLeagueTable() {
        return leagueTable;
    }

    public SeasonLinks setLeagueTable(HrefWrapper leagueTable) {
        this.leagueTable = leagueTable;
        return this;
    }
}
