
package ir.trap.tractor.android.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank
{

    @SerializedName("BankBin")
    @Expose
    private String bankBin;
    @SerializedName("BankName")
    @Expose
    private String bankName;
    @SerializedName("ImageLogo")
    @Expose
    private String imageLogo;
    @SerializedName("ImageCard")
    @Expose
    private String imageCard;
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

    public String getBankBin() {
        return bankBin;
    }

    public void setBankBin(String bankBin) {
        this.bankBin = bankBin;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getImageLogo() {
        return imageLogo;
    }

    public void setImageLogo(String imageLogo) {
        this.imageLogo = imageLogo;
    }

    public String getImageCard() {
        return imageCard;
    }

    public void setImageCard(String imageCard) {
        this.imageCard = imageCard;
    }

    public String getColorText() {
        return colorText;
    }

    public void setColorText(String colorText) {
        this.colorText = colorText;
    }
}
