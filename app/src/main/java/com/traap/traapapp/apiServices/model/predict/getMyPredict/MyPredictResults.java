package com.traap.traapapp.apiServices.model.predict.getMyPredict;

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

    @Expose @Getter @Setter
    @SerializedName("is_predict_true")
    private Boolean isPredictTrue;

    @Expose @Getter @Setter
    @SerializedName("id")
    private Integer id;
}
