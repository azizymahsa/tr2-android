package com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Score
{
    @Expose @Getter @Setter
    @SerializedName("home_score")
    private int homeScore;

    @Expose @Getter @Setter
    @SerializedName("away_score")
    private int awayScore;
}
