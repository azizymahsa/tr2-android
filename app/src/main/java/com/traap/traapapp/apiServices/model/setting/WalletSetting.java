package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class WalletSetting
{
    @Expose @Getter @Setter
    @SerializedName("filters")
    private Filters filters;

    @Expose @Getter @Setter
    @SerializedName("wallet")
    private Wallet wallet;
}
