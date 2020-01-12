package com.traap.traapapp.apiServices.model.getTransaction;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ResponseTransaction
{
    @SerializedName("results")
    @Expose @Getter @Setter
    private List<TransactionList> transactionLists = null;

}