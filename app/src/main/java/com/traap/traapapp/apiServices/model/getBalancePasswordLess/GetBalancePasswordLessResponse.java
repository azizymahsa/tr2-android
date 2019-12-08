package com.traap.traapapp.apiServices.model.getBalancePasswordLess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 12/8/2019.
 */
public class GetBalancePasswordLessResponse
{

    @SerializedName("balance_amount")
    @Expose
    private String balanceAmount;
    @SerializedName("is_loyal")
    @Expose
    private String isLoyal;
    @SerializedName("date_time")
    @Expose
    private String dateTime;

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getIsLoyal() {
        return isLoyal;
    }

    public void setIsLoyal(String isLoyal) {
        this.isLoyal = isLoyal;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
