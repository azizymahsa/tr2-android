package com.traap.traapapp.apiServices.model.getBalancePasswordLess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 2/3/2020.
 */
public class AddValueList
{

    @SerializedName("price")
    @Expose
    @Getter
    @Setter
    private Long price;

}
