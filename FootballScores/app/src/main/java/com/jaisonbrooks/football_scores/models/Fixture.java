package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Fixture {

    @SerializedName("_links")
    @Expose
    private FixtureLinks Links;
    @Expose
    private String date;
    @Expose
    private String status;
    @Expose
    @SerializedName("matchday")
    private Long matchDay;
    @Expose
    private String homeTeamName;
    @Expose
    private String awayTeamName;
    @Expose
    private Result result;

    public FixtureLinks getLinks() {
        return Links;
    }

    public Fixture setLinks(FixtureLinks links) {
        Links = links;
        return this;
    }

    public String getDate() {
        return date;
    }

    public Fixture setDate(String date) {
        this.date = date;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Fixture setStatus(String status) {
        this.status = status;
        return this;
    }

    public Long getMatchDay() {
        return matchDay;
    }

    public Fixture setMatchDay(Long matchDay) {
        this.matchDay = matchDay;
        return this;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public Fixture setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
        return this;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public Fixture setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
        return this;
    }

    public Result getResult() {
        return result;
    }

    public Fixture setResult(Result result) {
        this.result = result;
        return this;
    }
}
