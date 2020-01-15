
package com.traap.traapapp.apiServices.model.getReport.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReportRequest {

    @SerializedName("is_wallet")
    @Expose
    private Boolean isWallet;
    @SerializedName("operation_type")
    @Expose
    private Integer operationType;
    @SerializedName("to_amount")
    @Expose
    private Integer toAmount;
    @SerializedName("from_amount")
    @Expose
    private Integer fromAmount;
    @SerializedName("page_number")
    @Expose
    private Integer pageNumber;
    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("from_date")
    @Expose
    private String fromDate;
    @SerializedName("page_size")
    @Expose
    private Integer pageSize;

    public Boolean getIsWallet() {
        return isWallet;
    }

    public void setIsWallet(Boolean isWallet) {
        this.isWallet = isWallet;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }

    public Integer getToAmount() {
        return toAmount;
    }

    public void setToAmount(Integer toAmount) {
        this.toAmount = toAmount;
    }

    public Integer getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Integer fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
