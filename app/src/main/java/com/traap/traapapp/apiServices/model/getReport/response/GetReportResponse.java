
package com.traap.traapapp.apiServices.model.getReport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetReportResponse
{

    @SerializedName("balance_amount")
    @Expose
    private String balanceAmount;
    @SerializedName("full_name")
    @Expose
    private String fullName;
    @SerializedName("total_trn_count")
    @Expose
    private String totalTrnCount;
    @SerializedName("is_loyal")
    @Expose
    private String isLoyal;
    @SerializedName("list_transaction")
    @Expose
    private List<ListTransaction> listTransaction = null;

    public String getBalanceAmount()
    {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount)
    {
        this.balanceAmount = balanceAmount;
    }

    public String getFullName()
    {
        return fullName;
    }

    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }

    public String getTotalTrnCount()
    {
        return totalTrnCount;
    }

    public void setTotalTrnCount(String totalTrnCount)
    {
        this.totalTrnCount = totalTrnCount;
    }

    public String getIsLoyal()
    {
        return isLoyal;
    }

    public void setIsLoyal(String isLoyal)
    {
        this.isLoyal = isLoyal;
    }

    public List<ListTransaction> getListTransaction()
    {
        return listTransaction;
    }

    public void setListTransaction(List<ListTransaction> listTransaction)
    {
        this.listTransaction = listTransaction;
    }

}
