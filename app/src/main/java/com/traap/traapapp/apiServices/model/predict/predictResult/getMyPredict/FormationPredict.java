package com.traap.traapapp.apiServices.model.predict.predictResult.getMyPredict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FormationPredict
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private int id;

    @SerializedName("user_formation_predict")
    @Expose @Getter @Setter
    private String yourFormationPredict;

    @SerializedName("true_formation")
    @Expose @Getter @Setter
    private String trueFormationResult;

    @SerializedName("formation_predict_accuracy")
    @Expose @Getter @Setter
    private float playerAccuracy;

    @SerializedName("is_formation_predict_true")
    @Expose @Getter @Setter
    private boolean isFormationPredictTrue;
}
