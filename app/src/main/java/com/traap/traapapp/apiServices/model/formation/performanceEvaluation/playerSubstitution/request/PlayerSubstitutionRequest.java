package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.playerSubstitution.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.MatchEvaluation;

import lombok.Getter;
import lombok.Setter;

public class PlayerSubstitutionRequest
{
    @SerializedName("position")
    @Expose @Getter @Setter
    private Integer positionId;

    public PlayerSubstitutionRequest(Integer positionId)
    {
        this.positionId = positionId;
    }
}
