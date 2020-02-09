package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class BarChart
{
    @SerializedName("away_score")
    @Expose @Getter @Setter
    private Integer awayScore;

    @SerializedName("home_score")
    @Expose @Getter @Setter
    private Integer homeScore;

    @SerializedName("total_user")
    @Expose @Getter @Setter
    private Double totalUser;
}
