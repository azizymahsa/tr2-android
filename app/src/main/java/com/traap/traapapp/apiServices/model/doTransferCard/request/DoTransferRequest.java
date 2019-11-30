
package com.traap.traapapp.apiServices.model.doTransferCard.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoTransferRequest {

    @SerializedName("Pan")
    @Expose
    private String pan;

    @SerializedName("DestinationPan")
    @Expose
    private String destinationPan;
    @SerializedName("ToCardHolder")
    @Expose
    private String toCardHolder;
    @SerializedName("Pin2")
    @Expose
    private String password;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("Cvv2")
    @Expose
    private String cvv2;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("HolderTransactionId")
    @Expose
    private String holderTransactionId;
    @SerializedName("Stna")
    @Expose
    private String stna;
    @SerializedName("DestinationCardHolderName")
    @Expose
    private String destinationCardHolderName;
    @SerializedName("VerificationCodeKeshavarzi")
    @Expose
    private String verificationCodeKeshavarzi;

    public String getVerificationCodeKeshavarzi() {
        return verificationCodeKeshavarzi;
    }

    public void setVerificationCodeKeshavarzi(String verificationCodeKeshavarzi) {
        this.verificationCodeKeshavarzi = verificationCodeKeshavarzi;
    }

    public String getDestinationCardHolderName() {
        return destinationCardHolderName;
    }

    public void setDestinationCardHolderName(String destinationCardHolderName) {
        this.destinationCardHolderName = destinationCardHolderName;
    }

    public String getStna() {
        return stna;
    }

    public void setStna(String stna) {
        this.stna = stna;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getDestinationPan() {
        return destinationPan;
    }

    public void setDestinationPan(String destinationPan) {
        this.destinationPan = destinationPan;
    }

    public String getToCardHolder() {
        return toCardHolder;
    }

    public void setToCardHolder(String toCardHolder) {
        this.toCardHolder = toCardHolder;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getHolderTransactionId() {
        return holderTransactionId;
    }

    public void setHolderTransactionId(String holderTransactionId) {
        this.holderTransactionId = holderTransactionId;
    }


}
