package com.traap.traapapp.apiServices.model.getDecQrCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DecryptQrResponse
{


    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("is_active_push_notification")
    @Expose
    private Boolean isActivePushNotification;
    @SerializedName("merchant_id")
    @Expose
    private Integer merchantId;
    @SerializedName("merchant_shetac")
    @Expose
    private String merchantShetac;
    @SerializedName("is_print_pos")
    @Expose
    private Boolean isPrintPos;
    @SerializedName("terminal_shetac")
    @Expose
    private String terminalShetac;
    @SerializedName("terminal_shetab")
    @Expose
    private String terminalShetab;
    @SerializedName("merchant_name")
    @Expose
    private String merchantName;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("merchant_phone")
    @Expose
    private String merchantPhone;
    @SerializedName("serial_number_pos")
    @Expose
    private String serialNumberPos;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Boolean getIsActivePushNotification() {
        return isActivePushNotification;
    }

    public void setIsActivePushNotification(Boolean isActivePushNotification) {
        this.isActivePushNotification = isActivePushNotification;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }

    public String getMerchantShetac() {
        return merchantShetac;
    }

    public void setMerchantShetac(String merchantShetac) {
        this.merchantShetac = merchantShetac;
    }

    public Boolean getIsPrintPos() {
        return isPrintPos;
    }

    public void setIsPrintPos(Boolean isPrintPos) {
        this.isPrintPos = isPrintPos;
    }

    public String getTerminalShetac() {
        return terminalShetac;
    }

    public void setTerminalShetac(String terminalShetac) {
        this.terminalShetac = terminalShetac;
    }

    public String getTerminalShetab() {
        return terminalShetab;
    }

    public void setTerminalShetab(String terminalShetab) {
        this.terminalShetab = terminalShetab;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getSerialNumberPos() {
        return serialNumberPos;
    }

    public void setSerialNumberPos(String serialNumberPos) {
        this.serialNumberPos = serialNumberPos;
    }

}