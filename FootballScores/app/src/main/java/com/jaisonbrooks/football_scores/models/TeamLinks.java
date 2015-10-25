package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;

public final class TeamLinks {

    @Expose
    private HrefWrapper self;
    @Expose
    private HrefWrapper fixtures;
    @Expose
    private HrefWrapper player;

    public HrefWrapper getSelf() {
        return self;
    }

    public TeamLinks setSelf(HrefWrapper self) {
        this.self = self;
        return this;
    }

    public HrefWrapper getFixtures() {
        return fixtures;
    }

    public TeamLinks setFixtures(HrefWrapper fixtures) {
        this.fixtures = fixtures;
        return this;
    }

    public HrefWrapper getPlayer() {
        return player;
    }

    public TeamLinks setPlayer(HrefWrapper player) {
        this.player = player;
        return this;
    }
}
