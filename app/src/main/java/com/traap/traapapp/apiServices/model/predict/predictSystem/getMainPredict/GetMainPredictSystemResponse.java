package com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.MatchEvaluation;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetMainPredictSystemResponse
{
    @SerializedName("all_players")
    @Expose @Getter @Setter
    @Nullable
    private List<PlayerItem> allPlayers = null;

    @SerializedName("all_formations")
    @Expose @Getter @Setter
    @Nullable
    private List<FormationItem> formationItemList = null;

    @SerializedName("match")
    @Expose @Getter @Setter
    @Nullable
    private MatchEvaluation match = null;

    @SerializedName("match_background")
    @Expose @Getter @Setter
    private String backgroundImage;

    @SerializedName("default_formation_id")
    @Expose @Getter @Setter
    private int defaultFormationId;
}
