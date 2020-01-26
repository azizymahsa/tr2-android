package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Wallet
{
    @Expose @Getter @Setter
    @SerializedName("wallet_checkout")
    private WalletCheckout walletCheckout;

    @Expose @Getter @Setter
    @SerializedName("filters")
    private Filters filters;
}
