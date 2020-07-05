package com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class LastMatchResult
{
    @SerializedName("home_score")
    @Expose @Getter @Setter
    private Integer homeScore;

    @SerializedName("away_score")
    @Expose @Getter @Setter
    private Integer awayScore;
}
