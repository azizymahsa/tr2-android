package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.Cup;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.LastMatchResult;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.TeamDetails;

import lombok.Getter;
import lombok.Setter;

public class MatchEvaluation
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer matchId;

    @SerializedName("home_team")
    @Expose @Getter @Setter
    private TeamDetails homeTeam;

    @SerializedName("away_team")
    @Expose @Getter @Setter
    private TeamDetails awayTeam;

    @SerializedName("cup")
    @Expose @Getter @Setter
    private Cup cup;

    @SerializedName("result")
    @Expose @Getter @Setter
    private LastMatchResult lastMatchResult;

    @SerializedName("datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

}
