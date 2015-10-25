package com.jaisonbrooks.football_scores.models;

import com.google.gson.annotations.Expose;

public final class HrefWrapper {

    @Expose
    private String href;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
