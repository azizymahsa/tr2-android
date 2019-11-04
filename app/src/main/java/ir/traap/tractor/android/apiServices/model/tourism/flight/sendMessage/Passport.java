
package ir.traap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passport {

    @SerializedName("ValidityDate")
    @Expose
    private String validityDate;

    public String getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(String validityDate) {
        this.validityDate = validityDate;
    }

}
