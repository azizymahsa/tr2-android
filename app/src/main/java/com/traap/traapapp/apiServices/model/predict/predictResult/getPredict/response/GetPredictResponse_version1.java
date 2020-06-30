package com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetPredictResponse_version1
{
    @SerializedName("you_predict")
    @Expose @Getter @Setter
    private Boolean youPredict;

    @SerializedName("you_predict_result")
    @Expose @Getter @Setter
    @Nullable
    private String youPredictResult = null;

    @SerializedName("home_team_logo")
    @Expose @Getter @Setter
    private String homeTeamLogo;

    @SerializedName("away_team_name")
    @Expose @Getter @Setter
    private String awayTeamName;

    @SerializedName("pieChart")
    @Expose @Getter @Setter
    private List<PieChart> pieChart = null;

    @SerializedName("match_datetime")
    @Expose @Getter @Setter
    private Integer matchDatetime;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("barChart")
    @Expose @Getter @Setter
    private List<BarChart> barChart = null;

    @SerializedName("home_last_plays")
    @Expose @Getter @Setter
    private List<String> homeLastPlays = null;

    @SerializedName("cup")
    @Expose @Getter @Setter
    private String cup;

    @SerializedName("home_score")
    @Expose @Getter @Setter
    private Integer homeScore;

    @SerializedName("away_last_plays")
    @Expose @Getter @Setter
    private List<String> awayLastPlays = null;

    @SerializedName("home_team_color_code")
    @Expose @Getter @Setter
    private String homeTeamColorCode;

    @SerializedName("away_score")
    @Expose @Getter @Setter
    private Integer awayScore;

    @SerializedName("away_team_logo")
    @Expose @Getter @Setter
    private String awayTeamLogo;

    @SerializedName("away_team_color_code")
    @Expose @Getter @Setter
    private String awayTeamColorCode;

    @SerializedName("home_team_name")
    @Expose @Getter @Setter
    private String homeTeamName;
}
