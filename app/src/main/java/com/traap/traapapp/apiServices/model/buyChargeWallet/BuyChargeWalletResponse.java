package com.traap.traapapp.apiServices.model.buyChargeWallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 12/10/2019.
 */
public class BuyChargeWalletResponse
{
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("ref_number")
    @Expose
    private String refNumber;

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public String getRefNumber()
    {
        return refNumber;
    }

    public void setRefNumber(String refNumber)
    {
        this.refNumber = refNumber;
    }
}
