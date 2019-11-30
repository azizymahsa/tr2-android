
package com.traap.traapapp.apiServices.model.getRightelPack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Packages {


    @SerializedName("request_id")
    @Expose
    private String requestId;

    @SerializedName("ten_days")
    @Expose
    private List<Detail> tenDays = null;

    @SerializedName("four_months")
    @Expose
    private List<Detail> fourMonths = null;

    @SerializedName("monthly")
    @Expose
    private List<Detail> monthly = null;

    @SerializedName("two_months")
    @Expose
    private List<Detail> twoMonths = null;
    @SerializedName("hourly")
    @Expose
    private List<Detail> hourly = null;
    @SerializedName("daily")
    @Expose
    private List<Detail> daily = null;
    @SerializedName("weekly")
    @Expose
    private List<Detail> weekly = null;

    @SerializedName("one_year")
    @Expose
    private List<Detail> oneYear = null;

    @SerializedName("three_months")
    @Expose
    private List<Detail> threeMonths = null;

    @SerializedName("six_months")
    @Expose
    private List<Detail> sixMonths = null;

    @SerializedName("fifteen_days")
    @Expose
    private List<Detail> fifteenDays = null;

    @SerializedName("three_days")
    @Expose
    private List<Detail> threeDays = null;

    public String getRequestId()
    {
        return requestId;
    }

    public void setRequestId(String requestId)
    {
        this.requestId = requestId;
    }

    public List<Detail> getTenDays() {
        return tenDays;
    }

    public void setTenDays(List<Detail> tenDays) {
        this.tenDays = tenDays;
    }

    public List<Detail> getFourMonths() {
        return fourMonths;
    }

    public void setFourMonths(List<Detail> fourMonths) {
        this.fourMonths = fourMonths;
    }

    public List<Detail> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<Detail> monthly) {
        this.monthly = monthly;
    }

    public List<Detail> getTwoMonths() {
        return twoMonths;
    }

    public void setTwoMonths(List<Detail> twoMonths) {
        this.twoMonths = twoMonths;
    }

    public List<Detail> getThreeMonths() {
        return threeMonths;
    }

    public void setThreeMonths(List<Detail> threeMonths) {
        this.threeMonths = threeMonths;
    }

    public List<Detail> getSixMonths() {
        return sixMonths;
    }

    public void setSixMonths(List<Detail> sixMonths) {
        this.sixMonths = sixMonths;
    }

    public List<Detail> getFifteenDays() {
        return fifteenDays;
    }

    public void setFifteenDays(List<Detail> fifteenDays) {
        this.fifteenDays = fifteenDays;
    }

    public List<Detail> getThreeDays() {
        return threeDays;
    }

    public void setThreeDays(List<Detail> threeDays) {
        this.threeDays = threeDays;
    }



    public List<Detail> getHourly() {
        return hourly;
    }

    public void setHourly(List<Detail> hourly) {
        this.hourly = hourly;
    }

    public List<Detail> getDaily() {
        return daily;
    }

    public void setDaily(List<Detail> daily) {
        this.daily = daily;
    }

    public List<Detail> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<Detail> weekly) {
        this.weekly = weekly;
    }


    public List<Detail> getOneYear() {
        return oneYear;
    }

    public void setOneYear(List<Detail> oneYear) {
        this.oneYear = oneYear;
    }


}
