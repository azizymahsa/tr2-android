package com.traap.traapapp.apiServices.model.league.pastResult.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class RequestPastResult
{
    @SerializedName("livescore_id")
    @Expose @Getter @Setter
    private String teamLiveScoreId;
}
