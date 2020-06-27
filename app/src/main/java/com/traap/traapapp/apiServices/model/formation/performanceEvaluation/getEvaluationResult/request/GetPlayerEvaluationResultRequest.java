package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.getEvaluationResult.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request.QuestionItemRequest;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetPlayerEvaluationResultRequest
{
    @SerializedName("player_id")
    @Expose @Getter @Setter
    private int playerId;
}
