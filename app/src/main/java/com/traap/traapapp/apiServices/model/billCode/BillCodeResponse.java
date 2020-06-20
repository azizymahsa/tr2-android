package com.traap.traapapp.apiServices.model.billCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 6/16/2020.
 */
public class BillCodeResponse
{
    @SerializedName("owner_name")
    @Expose
    private String ownerName;
    @SerializedName("pay_code")
    @Expose
    private String payCode;
    @SerializedName("bill_code")
    @Expose
    private String billCode;
    @SerializedName("current_date")
    @Expose
    private String currentDate;
    @SerializedName("previous_date")
    @Expose
    private String previousDate;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("remain_pay_date")
    @Expose
    private String remainPayDate;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("address")
    @Expose
    private String address;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

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

    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    public String getPreviousDate() {
        return previousDate;
    }

    public void setPreviousDate(String previousDate) {
        this.previousDate = previousDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRemainPayDate() {
        return remainPayDate;
    }

    public void setRemainPayDate(String remainPayDate) {
        this.remainPayDate = remainPayDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
