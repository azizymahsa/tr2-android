
package ir.trap.tractor.android.apiServices.model.getPackageIrancell.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPackageIrancellResponse {

    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

    @SerializedName("Packages")
    @Expose
    private IrancellPackage irancellPackage;

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

    public IrancellPackage getIrancellPackage() {
        return irancellPackage;
    }

    public void setIrancellPackage(IrancellPackage irancellPackage) {
        this.irancellPackage = irancellPackage;
    }
}
