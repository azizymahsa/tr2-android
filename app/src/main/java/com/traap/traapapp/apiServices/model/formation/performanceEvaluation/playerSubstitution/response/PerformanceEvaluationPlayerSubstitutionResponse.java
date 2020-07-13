package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.Player;
import lombok.Getter;
import lombok.Setter;

public class PerformanceEvaluationPlayerSubstitutionResponse
{
    @SerializedName("position_id")
    @Expose @Getter @Setter
    private Integer positionId;

    @SerializedName("player")
    @Expose @Getter @Setter
    private Player playerItem;

}
