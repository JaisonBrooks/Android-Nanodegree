package com.jaisonbrooks.football_scores.network;

import com.jaisonbrooks.football_scores.models.FixturesResponse;
import com.jaisonbrooks.football_scores.models.Season;
import com.jaisonbrooks.football_scores.models.TeamResponse;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

public interface FootballApi {

    @GET("/soccerseasons") List<Season> seasons();

    @GET("/soccerseasons/{id}/teams")
    TeamResponse teams(
            @Path("id") String seasonId);

    @GET("/fixtures")
    FixturesResponse fixtures(
            @Query("timeFrame") String timeFrame);

}
