package com.traap.traapapp.apiServices.model.predict.getMyPredict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Bundle
{
    @Expose @Getter @Setter
    @SerializedName("balance")
    private Integer balance;

    @Expose @Getter @Setter
    @SerializedName("my_predictions_true")
    private Integer myPredictionsTrue;

    @Expose @Getter @Setter
    @SerializedName("my_rank")
    private Integer myRank;

    @Expose @Getter @Setter
    @SerializedName("all_my_predictions")
    private Integer allMyPredictions;

    @Expose @Getter @Setter
    @SerializedName("my_predictions_false")
    private Integer myPredictionsFalse;
}
