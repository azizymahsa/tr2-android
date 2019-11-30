
package com.traap.traapapp.apiServices.model.getInfoBill.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInfoBillResponse {

    @SerializedName("BillCode")
    @Expose
    private String billId;
    @SerializedName("PayCode")
    @Expose
    private String payId;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Type")
    @Expose
    private String type;
    @SerializedName("LogoBill")
    @Expose
    private String logoBill;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("PayRefNo")
    @Expose
    private String payRefNo;
    @SerializedName("PayDateTime")
    @Expose
    private String payDateTime;
    @SerializedName("PayPan")
    @Expose
    private String payPan;
    @SerializedName("IsPaid")
    @Expose
    private Boolean isPaid;
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLogoBill() {
        return logoBill;
    }

    public void setLogoBill(String logoBill) {
        this.logoBill = logoBill;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPayRefNo() {
        return payRefNo;
    }

    public void setPayRefNo(String payRefNo) {
        this.payRefNo = payRefNo;
    }

    public String getPayDateTime() {
        return payDateTime;
    }

    public void setPayDateTime(String payDateTime) {
        this.payDateTime = payDateTime;
    }

    public String getPayPan() {
        return payPan;
    }

    public void setPayPan(String payPan) {
        this.payPan = payPan;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

}
