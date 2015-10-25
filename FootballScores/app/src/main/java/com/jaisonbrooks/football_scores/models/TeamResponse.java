package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public final class TeamResponse {

    @Expose
    private List<Team> teams = new ArrayList<>();

    public List<Team> getTeams() {
        return teams;
    }

    public TeamResponse setTeams(List<Team> teams) {
        this.teams = teams;
        return this;
    }
}
