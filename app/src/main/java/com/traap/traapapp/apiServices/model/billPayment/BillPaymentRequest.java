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
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("gwe_bill_id")
    @Expose
    private String gweBillId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGweBillId() {
        return gweBillId;
    }

    public void setGweBillId(String gweBillId) {
        this.gweBillId = gweBillId;
    }


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
