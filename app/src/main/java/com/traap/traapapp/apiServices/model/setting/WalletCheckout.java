package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class WalletCheckout
{
    @Expose @Getter @Setter
    @SerializedName("withdraw_interval_value")
    private int withdrawIntervalValue;


    @Expose @Getter @Setter
    @SerializedName("add_value")
    private List<AddValue> addValue;

    @Expose @Getter @Setter
    @SerializedName("withdraw_max")
    private int withdrawMax;

    @Expose @Getter @Setter
    @SerializedName("withdraw_interval_title")
    private String withdrawIntervalTitle;
}
