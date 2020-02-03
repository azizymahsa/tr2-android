package com.traap.traapapp.apiServices.model.getTransaction;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ResponseTransaction
{
    @SerializedName("count")
    @Expose @Getter @Setter
    private Integer count;

    @SerializedName("setting")
    @Expose @Getter @Setter
    private TransactionSetting transactionSetting;

    @SerializedName("results")
    @Expose @Getter @Setter
    private List<TransactionList> transactionLists = null;

}