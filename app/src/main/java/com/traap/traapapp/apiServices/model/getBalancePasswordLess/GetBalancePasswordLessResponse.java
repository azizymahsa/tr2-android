package com.traap.traapapp.apiServices.model.getBalancePasswordLess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class GetBalancePasswordLessResponse
{

    @SerializedName("balance_amount")
    @Expose
    @Getter
    @Setter
    private String balanceAmount;
    @SerializedName("is_loyal")
    @Expose
    @Getter
    @Setter
    private String isLoyal;
    @SerializedName("date_time")
    @Expose
    @Getter
    @Setter
    private String dateTime;

    @SerializedName("setting")
    @Expose
    @Getter
    @Setter
    private SettingBalance setting;

    @SerializedName("filters")
    @Expose
    @Getter
    @Setter
    private FilterBalance filters;



}
