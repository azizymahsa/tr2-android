package com.traap.traapapp.apiServices.model.withdrawWallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WithdrawWalletResponse implements Serializable
{

    @SerializedName("ref_number")
    @Expose
    private Integer refNumber;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(Integer refNumber) {
        this.refNumber = refNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

