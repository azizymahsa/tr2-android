
package com.traap.traapapp.apiServices.model.buyPackage.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageBuyRequest
{

    @SerializedName("operator_type")
    @Expose
    private String operatorType;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("bundle_id")
    @Expose
    private String bundleId;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("title_package")
    @Expose
    private String titlePackage;
    @SerializedName("request_id")
    @Expose
    private String requestId;

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTitlePackage() {
        return titlePackage;
    }

    public void setTitlePackage(String titlePackage) {
        this.titlePackage = titlePackage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }


}
