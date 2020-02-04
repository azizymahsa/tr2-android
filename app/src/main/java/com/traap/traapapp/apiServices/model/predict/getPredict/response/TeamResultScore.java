package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TeamResultScore
{
    @SerializedName("home_team")
    @Expose @Getter @Setter
    private Integer homeTeamScore;

    @SerializedName("away_team")
    @Expose @Getter @Setter
    private Integer awayTeamScore;

}
