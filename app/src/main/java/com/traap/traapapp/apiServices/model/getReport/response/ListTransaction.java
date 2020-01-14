
package com.traap.traapapp.apiServices.model.getReport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListTransaction {

    @SerializedName("trn_biz_Key")
    @Expose
    private String trnBizKey;
    @SerializedName("trn_date")
    @Expose
    private Double trnDate;
    @SerializedName("cari_code")
    @Expose
    private String cariCode;
    @SerializedName("debit_amount")
    @Expose
    private Double debitAmount;
    @SerializedName("credit_amount")
    @Expose
    private Double creditAmount;
    @SerializedName("discount")
    @Expose
    private String discount;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("balance")
    @Expose
    private String balance;

    public String getTrnBizKey() {
        return trnBizKey;
    }

    public void setTrnBizKey(String trnBizKey) {
        this.trnBizKey = trnBizKey;
    }

    public Double getTrnDate() {
        return trnDate;
    }

    public void setTrnDate(Double trnDate) {
        this.trnDate = trnDate;
    }

    public String getCariCode() {
        return cariCode;
    }

    public void setCariCode(String cariCode) {
        this.cariCode = cariCode;
    }

    public Double getDebitAmount() {
        return debitAmount;
    }

    public void setDebitAmount(Double debitAmount) {
        this.debitAmount = debitAmount;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

}
