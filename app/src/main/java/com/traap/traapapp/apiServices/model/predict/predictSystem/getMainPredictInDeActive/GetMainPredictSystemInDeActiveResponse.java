package com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredictInDeActive;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.Column;
import com.traap.traapapp.apiServices.model.formation.performanceEvaluation.main.MatchEvaluation;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.FormationItem;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getMainPredict.PlayerItem;
import com.traap.traapapp.apiServices.model.predict.predictSystem.getSystem.response.GetPredictSystemFromIdResponse;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetMainPredictSystemInDeActiveResponse
{
    @SerializedName("match_background")
    @Expose @Getter @Setter
    private String backgroundImage;

    @SerializedName("match")
    @Expose @Getter @Setter
    @Nullable
    private MatchEvaluation match = null;

    @SerializedName("main")
    @Expose @Getter @Setter
    private List<List<GetPredictSystemFromIdResponse>> rowList;
}
