package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class MatchPredict
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

    @SerializedName("datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("server_time")
    @Expose @Getter @Setter
    private Double serverTime;

    @SerializedName("predict_time")
    @Expose @Getter @Setter
    private Double predictTime;

    @SerializedName("is_predict")
    @Expose @Getter @Setter
    private boolean isPredict;

}
