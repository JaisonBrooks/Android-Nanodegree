package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;


public final class Result {

    @Expose
    private Long goalsHomeTeam;
    @Expose
    private Long goalsAwayTeam;

    public Long getGoalsHomeTeam() {
        return goalsHomeTeam;
    }

    public Result setGoalsHomeTeam(Long goalsHomeTeam) {
        this.goalsHomeTeam = goalsHomeTeam;
        return this;
    }

    public Long getGoalsAwayTeam() {
        return goalsAwayTeam;
    }

    public Result setGoalsAwayTeam(Long goalsAwayTeam) {
        this.goalsAwayTeam = goalsAwayTeam;
        return this;
    }
}
