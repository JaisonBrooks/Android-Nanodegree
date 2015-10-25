package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Team {

    @Expose
    private String name;

    @Expose
    private String crestUrl;

    @SerializedName("_links")
    @Expose
    private TeamLinks links;

    public String getName() {
        return name;
    }

    public Team setName(String name) {
        this.name = name;
        return this;
    }

    public String getCrestUrl() {
        return crestUrl;
    }

    public Team setCrestUrl(String crestUrl) {
        this.crestUrl = crestUrl;
        return this;
    }

    public TeamLinks getLinks() {
        return links;
    }

    public Team setLinks(TeamLinks links) {
        this.links = links;
        return this;
    }
}
