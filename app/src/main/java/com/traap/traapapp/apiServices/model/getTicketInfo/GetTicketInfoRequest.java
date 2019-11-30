package com.traap.traapapp.apiServices.model.getTicketInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public class GetTicketInfoRequest
{
    @SerializedName("transaction_id")
    @Expose
    private Integer transactionId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

}
