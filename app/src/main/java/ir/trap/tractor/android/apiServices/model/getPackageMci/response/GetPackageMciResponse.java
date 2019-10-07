
package ir.trap.tractor.android.apiServices.model.getPackageMci.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPackageMciResponse {

    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;
    @SerializedName("Packages")
    @Expose
    private Packages packages;

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

}
