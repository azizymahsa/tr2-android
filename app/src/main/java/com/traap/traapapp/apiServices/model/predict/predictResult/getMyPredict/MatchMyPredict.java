package com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.Cup;
import com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response.TeamDetails;

import lombok.Getter;
import lombok.Setter;

public class MatchMyPredict
{
    @Expose @Getter @Setter
    @SerializedName("result")
    private Score resultScore;

    @Expose @Getter @Setter
    @SerializedName("id")
    private int id;

    @Expose @Getter @Setter
    @SerializedName("match_datetime")
    private String matchDate;

    @Expose @Getter @Setter
    @SerializedName("match_datetime_formatted")
    private String matchDateStr;

    @Expose @Getter @Setter
    @SerializedName("stadium")
    private String stadium;

    @Expose @Getter @Setter
    @SerializedName("away_team")
    private TeamDetails awayTeam;

    @Expose @Getter @Setter
    @SerializedName("home_team")
    private TeamDetails homeTeam;

    @Expose @Getter @Setter
    @SerializedName("cup")
    private Cup cup;
}
