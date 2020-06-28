package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class QuestionItemRequest
{
    @SerializedName("player")
    @Expose @Getter @Setter
    private int playerId;

    @SerializedName("poll")
    @Expose @Getter @Setter
    private int questionId;

    @SerializedName("score")
    @Expose @Getter @Setter
    private int score;
}
