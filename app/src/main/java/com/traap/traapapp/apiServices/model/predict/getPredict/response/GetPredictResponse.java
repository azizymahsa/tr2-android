package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetPredictResponse
{
    @SerializedName("charts")
    @Expose @Getter @Setter
    private List<Chart> chart = null;

    @SerializedName("home_last_plays")
    @Expose @Getter @Setter
    private List<String> homeLastPlays = null;

    @SerializedName("away_last_plays")
    @Expose @Getter @Setter
    private List<String> awayLastPlays = null;

    @SerializedName("team_results")
    @Expose @Getter @Setter
    private List<TeamResults> teamResults = null;

    @SerializedName("predicts")
    @Expose @Getter @Setter
    private List<Predict> predict = null;

    @SerializedName("home_team")
    @Expose @Getter @Setter
    private TeamDetails homeTeam;

    @SerializedName("away_team")
    @Expose @Getter @Setter
    private TeamDetails awayTeam;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("you_predict")
    @Expose @Getter @Setter
    private Boolean youPredict;

    @SerializedName("match_datetime")
    @Expose @Getter @Setter
    private Integer matchDatetime;

    @SerializedName("you_predict_result")
    @Expose @Getter @Setter
    @Nullable
    private YouPredictResult youPredictResult = null;
}
