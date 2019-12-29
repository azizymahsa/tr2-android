package com.traap.traapapp.apiServices.model.getBalancePasswordLess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.getMenu.response.GetMenuItemResponse;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class GetBalancePasswordLessRequest
{
    @SerializedName("is_wallet")
    @Expose
    @Getter
    @Setter
    private Boolean isWallet;

    @SerializedName("pin2_new")
    @Expose
    @Getter
    @Setter
    private String pin2_new;

    @SerializedName("pin2_old")
    @Expose
    @Getter
    @Setter
    private String pin2_old;

}
