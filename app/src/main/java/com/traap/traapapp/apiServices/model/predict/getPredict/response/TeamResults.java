package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TeamResults
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer teamResultsId;

    @SerializedName("home_team")
    @Expose @Getter @Setter
    private TeamDetails homeTeam;

    @SerializedName("away_team")
    @Expose @Getter @Setter
    private TeamDetails awayTeam;

    @SerializedName("cup")
    @Expose @Getter @Setter
    private Cup cup;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("home_team_score")
    @Expose @Getter @Setter
    private Integer homeTeamScore;

    @SerializedName("away_team_score")
    @Expose @Getter @Setter
    private Integer awayTeamScore;



}