
package ir.trap.tractor.android.apiServices.model.getPackageMci.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.trap.tractor.android.apiServices.model.getRightelPack.response.Detail;

public class Packages {

    @SerializedName("Daily")
    @Expose
    private List<Detail> daily = null;
    @SerializedName("Nightly")
    @Expose
    private List<Detail> nightly = null;
    @SerializedName("ThreeDays")
    @Expose
    private List<Detail> threeDays = null;
    @SerializedName("Weekly")
    @Expose
    private List<Detail> weekly = null;
    @SerializedName("Monthly")
    @Expose
    private List<Detail> monthly = null;
    @SerializedName("ThreeMonths")
    @Expose
    private List<Detail> threeMonths = null;
    @SerializedName("TwoMonths")
    @Expose
    private List<Detail> twoMonths = null;
    @SerializedName("FourMonths")
    @Expose
    private List<Detail> fourMonths = null;
    @SerializedName("SixMonths")
    @Expose
    private List<Detail> sixMonths = null;
    @SerializedName("OneYear")
    @Expose
    private List<Detail> oneYear = null;
    @SerializedName("Other")
    @Expose
    private List<Detail> other = null;

    public List<Detail> getDaily() {
        return daily;
    }

    public void setDaily(List<Detail> daily) {
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

    public List<Detail> getOther() {
        return other;
    }

    public void setOther(List<Detail> other) {
        this.other = other;
    }

    public List<Detail> getNightly() {
        return nightly;
    }

    public void setNightly(List<Detail> nightly) {
        this.nightly = nightly;
    }

    public List<Detail> getFourMonths() {
        return fourMonths;
    }

    public void setFourMonths(List<Detail> fourMonths) {
        this.fourMonths = fourMonths;
    }

    public List<Detail> getTwoMonths() {
        return twoMonths;
    }

    public void setTwoMonths(List<Detail> twoMonths) {
        this.twoMonths = twoMonths;
    }
}
