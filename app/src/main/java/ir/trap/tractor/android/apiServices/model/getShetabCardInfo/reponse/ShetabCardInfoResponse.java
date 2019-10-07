
package ir.trap.tractor.android.apiServices.model.getShetabCardInfo.reponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShetabCardInfoResponse {

    @SerializedName("TransactionId")
    @Expose
    private String transactionId;
    @SerializedName("FirstName")
    @Expose
    private String firstName="";
    @SerializedName("LastName")
    @Expose
    private String lastName="";
    @SerializedName("CardImage")
    @Expose
    private String cardImage;
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;
    @SerializedName("ColorText")
    @Expose
    private String colorText;
    @SerializedName("ColorNumber")
    @Expose
    private String colorNumber;
    @SerializedName("Stna")
    @Expose
    private String stna;

    public String getColorNumber() {
        return colorNumber;
    }

    public void setColorNumber(String colorNumber) {
        this.colorNumber = colorNumber;
    }

    public String getStna() {
        return stna;
    }

    public void setStna(String stna) {
        this.stna = stna;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCardImage() {
        return cardImage;
    }

    public void setCardImage(String cardImage) {
        this.cardImage = cardImage;
    }
}
