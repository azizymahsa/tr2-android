package ir.trap.tractor.android.apiServices.model.getPackageIrancell.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import ir.trap.tractor.android.apiServices.model.getRightelPack.response.Detail;

/**
 * Created by Javad.Abadi on 6/8/2019.
 */
public class IrancellPackage {
    @SerializedName("Hourly")
    @Expose
    private List<Detail> hourly = null;
    @SerializedName("Daily")
    @Expose
    private List<Detail> daily = null;
    @SerializedName("Weekly")
    @Expose
    private List<Detail> weekly = null;
    @SerializedName("OneMonth")
    @Expose
    private List<Detail> oneMonth = null;
    @SerializedName("OneYear")
    @Expose
    private List<Detail> oneYear = null;

    @SerializedName("ThreeMonths")
    @Expose
    private List<Detail> threeMonths = null;

    @SerializedName("SixMonths")
    @Expose
    private List<Detail> sixMonths = null;


    @SerializedName("FifteenDays")
    @Expose
    private List<Detail> fifteenDays = null;

    @SerializedName("ThreeDays")
    @Expose
    private List<Detail> threeDays = null;

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

    public List<Detail> getOneMonth() {
        return oneMonth;
    }

    public void setOneMonth(List<Detail> oneMonth) {
        this.oneMonth = oneMonth;
    }

    public List<Detail> getOneYear() {
        return oneYear;
    }

    public void setOneYear(List<Detail> oneYear) {
        this.oneYear = oneYear;
    }

}
