package com.traap.traapapp.apiServices.model.predict.getMyPredict;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.annotation.Nullable;

import lombok.Getter;
import lombok.Setter;

public class MyPredictResponse
{
    @Expose @Getter @Setter
    @Nullable
    @SerializedName("next")
    private String next = null;

    @Expose @Getter @Setter
    @Nullable
    @SerializedName("previous")
    private String previous = null;

    @Expose @Getter @Setter
    @Nullable
    @SerializedName("setting")
    private Object setting = null;

    @Expose @Getter @Setter
    @SerializedName("results")
    private List<MyPredictResults> myPredictResults;

    @Expose @Getter @Setter
    @SerializedName("bundle")
    private Bundle bundle;

    @Expose @Getter @Setter
    @SerializedName("count")
    private Integer count;

}
