
package ir.traap.tractor.android.apiServices.model.getInfoPhoneBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LstPhoneBill {

    @SerializedName("BillId")
    @Expose
    private String billId;
    @SerializedName("PayId")
    @Expose
    private String payId;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("MidTerm")
    @Expose
    private String midTerm;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMidTerm() {
        return midTerm;
    }

    public void setMidTerm(String midTerm) {
        this.midTerm = midTerm;
    }

}
