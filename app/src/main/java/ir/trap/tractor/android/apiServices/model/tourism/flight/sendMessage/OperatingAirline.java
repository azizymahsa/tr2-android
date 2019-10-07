
package ir.trap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OperatingAirline {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Logo")
    @Expose
    private String logo;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
