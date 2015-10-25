package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Season {

    @SerializedName("_links")
    @Expose
    private SeasonLinks links;
    @Expose
    private String caption;
    @Expose
    private String league;
    @Expose
    private String year;
    @Expose
    private Integer numberOfTeams;
    @Expose
    private Integer numberOfGames;
    @Expose
    private String lastUpdated;

    public SeasonLinks getLinks() {
        return links;
    }

    public Season setLinks(SeasonLinks links) {
        this.links = links;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public Season setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getLeague() {
        return league;
    }

    public Season setLeague(String league) {
        this.league = league;
        return this;
    }

    public String getYear() {
        return year;
    }

    public Season setYear(String year) {
        this.year = year;
        return this;
    }

    public Integer getNumberOfTeams() {
        return numberOfTeams;
    }

    public Season setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
        return this;
    }

    public Integer getNumberOfGames() {
        return numberOfGames;
    }

    public Season setNumberOfGames(Integer numberOfGames) {
        this.numberOfGames = numberOfGames;
        return this;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public Season setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
        return this;
    }
}
