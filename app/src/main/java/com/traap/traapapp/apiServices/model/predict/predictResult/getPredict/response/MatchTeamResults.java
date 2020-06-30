package com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class MatchTeamResults
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer teamResultsId;

    @SerializedName("cup")
    @Expose @Getter @Setter
    private Cup cup;

    @SerializedName("match_datetime_formatted")
    @Expose @Getter @Setter
    private String matchDatetimeStr;

    @SerializedName("score")
    @Expose @Getter @Setter
    private TeamResultScore teamResultScore;
}
