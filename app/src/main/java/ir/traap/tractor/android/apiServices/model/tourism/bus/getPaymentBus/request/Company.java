
package ir.traap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("Bus")
    @Expose
    private String bus;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ID")
    @Expose
    private String iD;

    public String getBus() {
        return bus;
    }

    public void setBus(String bus) {
        this.bus = bus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

}
