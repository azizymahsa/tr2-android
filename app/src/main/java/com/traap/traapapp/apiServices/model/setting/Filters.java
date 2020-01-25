package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Filters
{
    @Expose @Getter @Setter
    @SerializedName("min_amount")
    private int minAmount;

    @Expose @Getter @Setter
    @SerializedName("max_amount")
    private int maxAmount;

    @Expose @Getter @Setter
    @SerializedName("step_count")
    private int stepCount;
}
