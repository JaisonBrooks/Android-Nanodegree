package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

public final class FixturesResponse {

    @Expose
    private String timeFrameStart;
    @Expose
    private String timeFrameEnd;
    @Expose
    private Long count;
    @Expose
    private List<Fixture> fixtures = new ArrayList<>();

    public String getTimeFrameStart() {
        return timeFrameStart;
    }

    public FixturesResponse setTimeFrameStart(String timeFrameStart) {
        this.timeFrameStart = timeFrameStart;
        return this;
    }

    public String getTimeFrameEnd() {
        return timeFrameEnd;
    }

    public FixturesResponse setTimeFrameEnd(String timeFrameEnd) {
        this.timeFrameEnd = timeFrameEnd;
        return this;
    }

    public Long getCount() {
        return count;
    }

    public FixturesResponse setCount(Long count) {
        this.count = count;
        return this;
    }

    public List<Fixture> getFixtures() {
        return fixtures;
    }

    public FixturesResponse setFixtures(List<Fixture> fixtures) {
        this.fixtures = fixtures;
        return this;
    }
}
