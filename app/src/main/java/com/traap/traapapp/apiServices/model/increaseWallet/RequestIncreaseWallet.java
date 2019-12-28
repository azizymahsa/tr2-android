package com.traap.traapapp.apiServices.model.increaseWallet;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.getBalancePasswordLess.GetBalancePasswordLessRequest;

public class RequestIncreaseWallet extends GetBalancePasswordLessRequest implements Serializable
{

    @SerializedName("amount")
    @Expose
    private Integer amount;
    private final static long serialVersionUID = -5433651708025640021L;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
