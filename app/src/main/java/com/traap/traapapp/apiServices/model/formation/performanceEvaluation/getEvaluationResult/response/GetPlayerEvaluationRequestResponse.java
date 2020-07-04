package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationResult.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class GetPlayerEvaluationRequestResponse
{
    @SerializedName("poll_user_score")
    @Expose @Getter @Setter
    private float score;

    @SerializedName("poll_average_score")
    @Expose @Getter @Setter
    private float averageScore;

    @SerializedName("poll_title")
    @Expose @Getter @Setter
    private String title;

}
