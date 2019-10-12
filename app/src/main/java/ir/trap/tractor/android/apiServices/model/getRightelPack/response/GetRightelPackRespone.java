
package ir.trap.tractor.android.apiServices.model.getRightelPack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRightelPackRespone {

    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;
    @SerializedName("RequestId")
    @Expose
    private String requestId;
    @SerializedName("Packages")
    @Expose
    private Packages packages;

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

}
