package com.traap.traapapp.apiServices.model.paymentPrintPos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentPrintPosRequest
{

    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("is_print_pos")
    @Expose
    private Boolean isPrintPos;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("card_id")
    @Expose
    private Integer cardId;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("cvv2")
    @Expose
    private String cvv2;
    @SerializedName("serial_number_pos")
    @Expose
    private String serialNumberPos;

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public Boolean getIsPrintPos() {
        return isPrintPos;
    }

    public void setIsPrintPos(Boolean isPrintPos) {
        this.isPrintPos = isPrintPos;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getSerialNumberPos() {
        return serialNumberPos;
    }

    public void setSerialNumberPos(String serialNumberPos) {
        this.serialNumberPos = serialNumberPos;
    }

}