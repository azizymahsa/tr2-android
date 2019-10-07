
package ir.trap.tractor.android.apiServices.model.getRightelPack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Packages {

    @SerializedName("Daily")
    @Expose
    private List<Detail> daily = null;
    @SerializedName("ThreeDays")
    @Expose
    private List<Detail> threeDays = null;
    @SerializedName("Weekly")
    @Expose
    private List<Detail> weekly = null;
    @SerializedName("TenDays")
    @Expose
    private List<Detail> tenDays = null;
    @SerializedName("Monthly")
    @Expose
    private List<Detail> monthly = null;
    @SerializedName("ThreeMonths")
    @Expose
    private List<Detail> threeMonths = null;
    @SerializedName("SixMonths")
    @Expose
    private List<Detail> sixMonths = null;
    @SerializedName("OneYear")
    @Expose
    private List<Detail> oneYear = null;

    public List<Detail> getDaily() {
        return daily;
    }

    public void setDetail(List<Detail> daily) {
        this.daily = daily;
    }

    public List<Detail> getThreeDays() {
        return threeDays;
    }

    public void setThreeDays(List<Detail> threeDays) {
        this.threeDays = threeDays;
    }

    public List<Detail> getWeekly() {
        return weekly;
    }

    public void setWeekly(List<Detail> weekly) {
        this.weekly = weekly;
    }

    public List<Detail> getTenDays() {
        return tenDays;
    }

    public void setTenDays(List<Detail> tenDays) {
        this.tenDays = tenDays;
    }

    public List<Detail> getMonthly() {
        return monthly;
    }

    public void setMonthly(List<Detail> monthly) {
        this.monthly = monthly;
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

    public List<Detail> getOneYear() {
        return oneYear;
    }

    public void setOneYear(List<Detail> oneYear) {
        this.oneYear = oneYear;
    }

}
