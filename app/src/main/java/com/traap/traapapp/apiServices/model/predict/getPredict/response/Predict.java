package com.traap.traapapp.apiServices.model.predict.getPredict.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Predict
{
    @SerializedName("predict")
    @Expose @Getter @Setter
    private String predict;

    @SerializedName("total_user")
    @Expose @Getter @Setter
    private Double totalUser;
}
