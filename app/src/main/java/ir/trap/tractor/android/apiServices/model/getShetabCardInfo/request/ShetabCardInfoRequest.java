
package ir.trap.tractor.android.apiServices.model.getShetabCardInfo.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShetabCardInfoRequest {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Cvv2")
    @Expose
    private String cvv2;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("DestinationPan")
    @Expose
    private String destinationPan;

    @SerializedName("Pan")
    @Expose
    private String pan;
    @SerializedName("Pin2")
    @Expose
    private String pin;
    @SerializedName("Amount")
    @Expose
    private String amount;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getDestinationPan() {
        return destinationPan;
    }

    public void setDestinationPan(String destinationPan) {
        this.destinationPan = destinationPan;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
