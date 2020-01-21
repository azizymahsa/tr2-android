package com.traap.traapapp.apiServices.model.doTransfer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 1/14/2020.
 */
public class DoTransferWalletResponse
{

    @SerializedName("is_loyal")
    @Expose
    private boolean isLoyal;

    @SerializedName("balance_amount")
    @Expose
    private String balanceAmount;

    @SerializedName("date_time")
    @Expose
    private String dateTime;

    @SerializedName("full_name")
    @Expose
    private String fullName;

    @SerializedName("trn_biz_key")
    @Expose
    private String trnBizKey;

    @SerializedName("ref_no")
    @Expose
    private String refrenceNumber;

    public String getRefrenceNumber()
    {
        return refrenceNumber;
    }

    public void setRefrenceNumber(String refrenceNumber)
    {
        this.refrenceNumber = refrenceNumber;
    }

    public boolean isLoyal()
    {
        return isLoyal;
    }

    public void setLoyal(boolean loyal)
    {
        isLoyal = loyal;
    }

    public String getBalanceAmount()
    {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount)
    {
        this.balanceAmount = balanceAmount;
    }

    public String getDateTime()
    {
        return dateTime;
    }

    public void setDateTime(String dateTime)
    {
        this.dateTime = dateTime;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getTrnBizKey()
    {
        return trnBizKey;
    }

    public void setTrnBizKey(String trnBizKey)
    {
        this.trnBizKey = trnBizKey;
    }
}
