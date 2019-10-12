
package ir.trap.tractor.android.apiServices.model.getHappyCardInfo.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHappyCardInfoResponse
{

    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;
    @SerializedName("BalanceAmount")
    @Expose
    private String balanceAmount;
    @SerializedName("Fullname")
    @Expose
    private String fullname;
    @SerializedName("CardNo")
    @Expose
    private String cardNo;
    @SerializedName("IsLoyal")
    @Expose
    private String isLoyal;
    @SerializedName("ServerTime")
    @Expose
    private String serverTime;
    @SerializedName("Email")
    @Expose
    private String email;
    @SerializedName("Mobile")
    @Expose
    private String mobile;
    @SerializedName("CardImage")
    @Expose
    private String cardImage;
    @SerializedName("ColorText")
    @Expose
    private String colorText;
    @SerializedName("ColorNumber")
    @Expose
    private String colorNumber;

    public String getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(String colorNumber) {
        this.colorNumber = colorNumber;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIsLoyal() {
        return isLoyal;
    }

    public void setIsLoyal(String isLoyal) {
        this.isLoyal = isLoyal;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
