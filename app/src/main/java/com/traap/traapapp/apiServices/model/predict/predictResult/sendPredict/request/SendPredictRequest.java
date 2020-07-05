package com.traap.traapapp.apiServices.model.predict.predictResult.sendPredict.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SendPredictRequest
{
    @SerializedName("match_id")
    @Expose@Getter @Setter
    private Integer matchId;

    @SerializedName("home_team_score")
    @Expose @Getter @Setter
    private Integer homeTeamScore;

    @SerializedName("away_team_score")
    @Expose @Getter @Setter
    private Integer awayTeamScore;

}