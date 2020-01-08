package com.traap.traapapp.apiServices.model.withdrawWallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.paymentMatch.Viewers;

import java.util.List;

/**
 * Created by MahsaAzizi on 03/01/2020.
 */
public class WithdrawWalletRequest
{
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("sheba_number")
    @Expose
    private String sheba_number;

    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public String getSheba_number()
    {
        return sheba_number;
    }

    public void setSheba_number(String sheba_number)
    {
        this.sheba_number = sheba_number;
    }
}