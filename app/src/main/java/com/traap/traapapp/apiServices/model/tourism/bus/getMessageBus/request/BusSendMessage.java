
package com.traap.traapapp.apiServices.model.tourism.bus.getMessageBus.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import com.traap.traapapp.apiServices.model.tourism.bus.getPaymentBus.request.RouteDetail;

public class BusSendMessage {
    @SerializedName("Pin2")
    @Expose
    private String pin2;
    @SerializedName("Cvv2")
    @Expose
    private String cvv2;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("Pan")
    @Expose
    private String pan;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("IsSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("Pnr")
    @Expose
    private String pnr;
    @SerializedName("SearchId")
    @Expose
    private String searchId;
    @SerializedName("Amount")
    @Expose
    private Integer Amount;
    @SerializedName("RouteDetail")
    @Expose
    private List<RouteDetail> routeDetail = null;

    public Integer getAmount() {
        return Amount;
    }

    public void setAmount(Integer amount) {
        Amount = amount;
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

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public void setSuccess(Boolean success) {
        isSuccess = success;
    }

    public String getPnr() {
        return pnr;
    }

    public void setPnr(String pnr) {
        this.pnr = pnr;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public List<RouteDetail> getRouteDetail() {
        return routeDetail;
    }

    public void setRouteDetail(List<RouteDetail> routeDetail) {
        this.routeDetail = routeDetail;
    }
}
