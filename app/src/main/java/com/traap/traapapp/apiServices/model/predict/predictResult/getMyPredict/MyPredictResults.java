package com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class MyPredictResults
{
    @Expose @Getter @Setter
    @SerializedName("point")
    private Integer point;

    @Expose @Getter @Setter
    @SerializedName("match")
    private MatchMyPredict matchMyPredict;

    @Expose @Getter @Setter
    @SerializedName("predict")
    private Score predictScore;

    @SerializedName("formation_predict")
    @Expose @Getter @Setter
    @Nullable
    private FormationPredict formationPredict = null;

    @Expose @Getter @Setter
    @SerializedName("is_predict_true")
    private Boolean isPredictTrue;

    @Expose @Getter @Setter
    @SerializedName("id")
    private Integer id;
}
