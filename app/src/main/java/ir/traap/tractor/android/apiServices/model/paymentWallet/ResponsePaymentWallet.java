package ir.traap.tractor.android.apiServices.model.paymentWallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponsePaymentWallet implements Serializable
{

    @SerializedName("ref_number")
    @Expose
    private Integer refNumber;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 5850830858707872756L;

    public Integer getRefNumber() {
        return refNumber;
    }

    public void setRefNumber(Integer refNumber) {
        this.refNumber = refNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

