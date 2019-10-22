package ir.trap.tractor.android.apiServices.model.getDecQrCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DecryptQrRequest
{
    @SerializedName("qr_code")
    @Expose
    private String decryptQr;


    public String getDecryptQr()
    {
        return decryptQr;
    }

    public void setDecryptQr(String decryptQr)
    {
        this.decryptQr = decryptQr;
    }


}
