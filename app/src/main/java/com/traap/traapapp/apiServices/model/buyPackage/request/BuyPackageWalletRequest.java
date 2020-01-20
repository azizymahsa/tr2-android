package com.traap.traapapp.apiServices.model.buyPackage.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 1/19/2020.
 */
public class BuyPackageWalletRequest
{
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("request_id")
    @Expose
    private String requestId;
    @SerializedName("bundle_id")
    @Expose
    private String bundleId;
    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("title_package")
    @Expose
    private String titlePackage;
    @SerializedName("operator_type")
    @Expose
    private String operatorType;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getTitlePackage() {
        return titlePackage;
    }

    public void setTitlePackage(String titlePackage) {
        this.titlePackage = titlePackage;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

}
