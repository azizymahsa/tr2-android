package com.traap.traapapp.apiServices.model.billPayment;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 6/15/2020.
 */
public class BillPaymentRequest
{
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("bill_code")
    @Expose
    private String billCode;
    @SerializedName("bill_term")
    @Expose
    private String billTerm;
    @SerializedName("pay_code")
    @Expose
    private String payCode;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getBillTerm() {
        return billTerm;
    }

    public void setBillTerm(String billTerm) {
        this.billTerm = billTerm;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

}
