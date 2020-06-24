package com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class PerformanceEvaluationMainResponse
{
    @SerializedName("match")
    @Expose @Getter @Setter
    private MatchEvaluation match;

    @SerializedName("main")
    @Expose @Getter @Setter
    private List<RowItem> rowList;

    @SerializedName("match_background")
    @Expose @Getter @Setter
    private String backgroundImage;
}
