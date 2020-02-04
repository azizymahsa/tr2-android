package com.traap.traapapp.apiServices.model.getTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FilterTransactionSetting
{
    @SerializedName("step_count")
    @Expose @Getter @Setter
    private Integer stepCount;

    @SerializedName("min_amount")
    @Expose @Getter @Setter
    private Integer minAmount;

    @SerializedName("max_amount")
    @Expose @Getter @Setter
    private Integer maxAmount;


}
