package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetPredictResponse
{
    @SerializedName("home_last_plays")
    @Expose @Getter @Setter
    private List<String> homeLastPlays = null;

    @SerializedName("away_last_plays")
    @Expose @Getter @Setter
    private List<String> awayLastPlays = null;

    @SerializedName("you_predict")
    @Expose @Getter @Setter
    private Boolean youPredict;

    @SerializedName("you_predict_result")
    @Expose @Getter @Setter
    @Nullable
    private YouPredictResult youPredictResult = null;

    @SerializedName("charts")
    @Expose @Getter @Setter
    private Chart chart = null;

    @SerializedName("match")
    @Expose @Getter @Setter
    private MatchPredict matchPredict = null;

    @SerializedName("match_team_results")
    @Expose @Getter @Setter
    private List<MatchTeamResults> matchTeamResults = null;
}
