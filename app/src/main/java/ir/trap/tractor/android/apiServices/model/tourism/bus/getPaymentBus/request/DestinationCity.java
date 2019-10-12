
package ir.trap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DestinationCity {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ID")
    @Expose
    private Integer iD;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getID() {
        return iD;
    }

    public void setID(Integer iD) {
        this.iD = iD;
    }

}
