
package com.traap.traapapp.apiServices.model.mobileCharge.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileChargeRequest
{
    @SerializedName("cvv2")
    @Expose
    private String cvv2;
    @SerializedName("card_id")
    @Expose
    private Integer cardId;
    @SerializedName("sim_card_type")
    @Expose
    private Integer simCardType;
    @SerializedName("type_charge")
    @Expose
    private Integer typeCharge;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("operator_type")
    @Expose
    private Integer operatorType;

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getSimCardType() {
        return simCardType;
    }

    public void setSimCardType(Integer simCardType) {
        this.simCardType = simCardType;
    }

    public Integer getTypeCharge() {
        return typeCharge;
    }

    public void setTypeCharge(Integer typeCharge) {
        this.typeCharge = typeCharge;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }
 /*   @SerializedName("card_id")
    @Expose
    private Integer cardId;
    @SerializedName("pin")
    @Expose
    private String pin;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("type_charge")
    @Expose
    private Integer typeCharge;
    @SerializedName("operator_type")
    @Expose
    private Integer operatorType;
    @SerializedName("cvv2")
    @Expose
    private String cvv2;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("sim_card_type")
    @Expose
    private Integer simCardType;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public Integer getTypeCharge() {
        return typeCharge;
    }

    public void setTypeCharge(Integer typeCharge) {
        this.typeCharge = typeCharge;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getSimCardType() {
        return simCardType;
    }

    public void setSimCardType(Integer simCardType) {
        this.simCardType = simCardType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
*/
}
