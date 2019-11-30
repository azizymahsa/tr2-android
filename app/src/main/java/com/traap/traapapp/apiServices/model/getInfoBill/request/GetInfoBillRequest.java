
package com.traap.traapapp.apiServices.model.getInfoBill.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInfoBillRequest {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("BillCode")
    @Expose
    private String billId;
    @SerializedName("PayCode")
    @Expose
    private String payId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBillId() {
        return billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getPayId() {
        return payId;
    }

    public void setPayId(String payId) {
        this.payId = payId;
    }

}
