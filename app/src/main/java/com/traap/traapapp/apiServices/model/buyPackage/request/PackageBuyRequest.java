
package com.traap.traapapp.apiServices.model.buyPackage.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageBuyRequest
{
    @SerializedName("request_id")
    @Expose
    private String requestId;

    @SerializedName("operator_type")
    @Expose
    private String operatorType;

    @SerializedName("card_id")
    @Expose
    private Integer cardId;

    @SerializedName("title_package")
    @Expose
    private String titlePackage;

    @SerializedName("bundle_id")
    @Expose
    private String bundleId;

    @SerializedName("mobile")
    @Expose
    private String mobile;

    @SerializedName("pin2")
    @Expose
    private String pin2;

    @SerializedName("cvv2")
    @Expose
    private String cvv2;

    @SerializedName("exp_Date")
    @Expose
    private String expDate;

    @SerializedName("amount")
    @Expose
    private Integer amount;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public String getTitlePackage() {
        return titlePackage;
    }

    public void setTitlePackage(String titlePackage) {
        this.titlePackage = titlePackage;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
