package com.traap.traapapp.apiServices.model.payBillCar;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bill implements Serializable
{

    @SerializedName("pay_code")
    @Expose
    private String payCode;
    @SerializedName("bill_code")
    @Expose
    private String billCode;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("type")
    @Expose
    private String type;
    private final static long serialVersionUID = -5827259691961956155L;

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}