
package ir.trap.tractor.android.apiServices.model.billPayment.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillPaymentRequest {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("BillCode")
    @Expose
    private String billId;
    @SerializedName("PayCode")
    @Expose
    private String payId;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("Pan")
    @Expose
    private String cardNumber;
    @SerializedName("Pin2")
    @Expose
    private String password;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("Cvv2")
    @Expose
    private String cvv2;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

}
