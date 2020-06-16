package com.traap.traapapp.apiServices.model.billPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 6/14/2020.
 */
public class BillPhoneResponse
{
    @SerializedName("1")
    @Expose
    private _1 _1;
    @SerializedName("2")
    @Expose
    private _2 _2;
    @SerializedName("bill_code")
    @Expose
    private String billCode;

    public _1 get1() {
        return _1;
    }

    public void set1(_1 _1) {
        this._1 = _1;
    }

    public _2 get2() {
        return _2;
    }

    public void set2(_2 _2) {
        this._2 = _2;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }
}
