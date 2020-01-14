package com.traap.traapapp.apiServices.model.doTransfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 1/14/2020.
 */
public class DoTransferWalletRequest
{
    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("to")
    @Expose
    private String to;

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
