
package ir.trap.tractor.android.apiServices.model.doTransferCard.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoTransferResponse {

    @SerializedName("SwitchResponserrn")
    @Expose
    private String switchResponserrn;
    @SerializedName("AvailableBalance")
    @Expose
    private Integer availableBalance;
    @SerializedName("Currency")
    @Expose
    private String currency;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getSwitchResponserrn() {
        return switchResponserrn;
    }

    public void setSwitchResponserrn(String switchResponserrn) {
        this.switchResponserrn = switchResponserrn;
    }

    public Integer getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(Integer availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

}
