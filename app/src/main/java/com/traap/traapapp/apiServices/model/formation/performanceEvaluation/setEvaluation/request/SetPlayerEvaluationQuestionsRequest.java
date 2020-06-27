package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.setEvaluation.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SetPlayerEvaluationQuestionsRequest
{
    @SerializedName("results")
    @Expose @Getter @Setter
    private List<QuestionItemRequest> questionRequestList;
}
