package com.traap.traapapp.apiServices.model.payBillCar;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestPayBillCar implements Serializable
{

    @SerializedName("bills")
    @Expose
    private List<Bill> bills = null;
    private final static long serialVersionUID = -3123501538844921302L;

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }

}