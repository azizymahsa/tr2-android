
package com.traap.traapapp.apiServices.model.billCar;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBillCar implements Serializable
{

    @SerializedName("details")
    @Expose
    private List<Detail> details = null;
    @SerializedName("plate")
    @Expose
    private List<String> plate = null;
    @SerializedName("total_amount")
    @Expose
    private Integer totalAmount;
    private final static long serialVersionUID = -9202546409785380954L;

    public List<Detail> getDetails() {
        return details;
    }

    public void setDetails(List<Detail> details) {
        this.details = details;
    }

    public List<String> getPlate() {
        return plate;
    }

    public void setPlate(List<String> plate) {
        this.plate = plate;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

}
