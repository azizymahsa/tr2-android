package com.traap.traapapp.apiServices.model.predict.predictResult.getPredict.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class PieChart
{
    @SerializedName("chart_prediction")
    @Expose @Getter @Setter
    @Nullable
    private Integer chartPrediction;

    @SerializedName("total_user")
    @Expose @Getter @Setter
    private Double totalUser;

}
