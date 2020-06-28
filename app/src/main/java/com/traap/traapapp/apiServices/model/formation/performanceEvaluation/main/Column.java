package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.Player;

import lombok.Getter;
import lombok.Setter;

public class Column
{
    @SerializedName("position_id")
    @Expose @Getter @Setter
    private Integer positionId;

    @SerializedName("is_active")
    @Expose @Getter @Setter
    private Boolean isActive;

    @SerializedName("player")
    @Expose @Getter @Setter
    @Nullable
    private Player player = null;
}
