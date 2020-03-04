package com.traap.traapapp.apiServices.model.getLast5PastMatch.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Last5PastMatchRequest
{
    @SerializedName("livescore_id")
    @Expose @Getter @Setter
    private String liveScoreId;
}
