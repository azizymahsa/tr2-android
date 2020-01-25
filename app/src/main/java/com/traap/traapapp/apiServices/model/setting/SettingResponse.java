package com.traap.traapapp.apiServices.model.setting;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public abstract class SettingResponse
{
    @Expose @Getter @Setter
    @SerializedName("buy_charge")
    private BuyCharge buyCharge;

    @Expose @Getter @Setter
    @SerializedName("stadium_ticket")
    private StadiumTicket stadiumTicket;

    @Expose @Getter @Setter
    @SerializedName("transaction")
    private Transaction transaction;

    @Expose @Getter @Setter
    @SerializedName("buy_internet_package")
    private BuyInternetPackage buyInternetPackage;

    @Expose @Getter @Setter
    @SerializedName("wallet")
    private WalletSetting wallet;
}
