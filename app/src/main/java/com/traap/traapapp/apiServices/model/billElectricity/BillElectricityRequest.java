package com.traap.traapapp.apiServices.model.billElectricity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 6/16/2020.
 */
public class BillElectricityRequest
{
    @SerializedName("bill_name")
    @Expose
    private String billName;
    @SerializedName("bill_code")
    @Expose
    private String billCode;
    @SerializedName("type")
    @Expose
    private String type;

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }

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
