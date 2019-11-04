package ir.trap.tractor.android.apiServices.model.paymentMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class PaymentMatchRequest
{
    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("viewers")
    @Expose
    private List<Viewers> viewers = null;
    @SerializedName("cvv2")
    @Expose
    private String cvv2;
    @SerializedName("exp_date")
    @Expose
    private String expDate;
    @SerializedName("card_id")
    @Expose
    private Integer cardId;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public List<Viewers> getViewers() {
        return viewers;
    }

    public void setViewers(List<Viewers> viewers) {
        this.viewers = viewers;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}
