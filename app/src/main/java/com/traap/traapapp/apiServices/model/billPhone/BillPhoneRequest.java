package com.traap.traapapp.apiServices.model.billPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 6/14/2020.
 */
public class BillPhoneRequest
{

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("bill_code")
    @Expose
    private String billCode;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
}
