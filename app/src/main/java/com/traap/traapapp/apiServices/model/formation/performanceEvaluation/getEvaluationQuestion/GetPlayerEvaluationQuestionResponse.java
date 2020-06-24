package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationQuestion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.Player;

import lombok.Getter;
import lombok.Setter;

public class GetPlayerEvaluationQuestionResponse
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer questionId;

    @SerializedName("poll_title")
    @Expose @Getter @Setter
    private String questionTitle;

    @SerializedName("range")
    @Expose @Getter @Setter
    private Integer range;

    @SerializedName("match")
    @Expose @Getter @Setter
    private Integer matchId;

}
