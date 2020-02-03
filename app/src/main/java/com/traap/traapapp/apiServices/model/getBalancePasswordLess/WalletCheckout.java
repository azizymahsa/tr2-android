package com.traap.traapapp.apiServices.model.getBalancePasswordLess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.getAllBoxes.AllBoxesResult;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 2/3/2020.
 */
public class WalletCheckout
{
    @SerializedName("add_value")
    @Expose
    @Getter
    @Setter
    private List<AddValueList> addValueList ;

    @SerializedName("withdraw_interval_title")
    @Expose
    @Getter
    @Setter
    private String withdrawIntervalTitle;

    @SerializedName("withdraw_interval_value")
    @Expose
    @Getter
    @Setter
    private String withdrawIntervalValue;

}
