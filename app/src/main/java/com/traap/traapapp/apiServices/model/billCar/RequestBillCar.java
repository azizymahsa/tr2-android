package com.traap.traapapp.apiServices.model.billCar;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestBillCar implements Serializable
{

    @SerializedName("bill_code")
    @Expose
    private String billCode;
    @SerializedName("type")
    @Expose
    private String type;
    private final static long serialVersionUID = -7907031381600230644L;

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
