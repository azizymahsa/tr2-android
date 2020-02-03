package com.traap.traapapp.apiServices.model.getTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TransactionSetting
{
    @SerializedName("filters")
    @Expose @Getter @Setter
    private FilterTransactionSetting filters;

}
