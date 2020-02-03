package com.traap.traapapp.apiServices.model.getBalancePasswordLess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 2/3/2020.
 */
public class SettingBalance
{
    @SerializedName("wallet_checkout")
    @Expose
    @Getter
    @Setter
    private WalletCheckout walletCheckout;

    @SerializedName("filters")
    @Expose
    @Getter
    @Setter
    private FilterBalance filters;

}
