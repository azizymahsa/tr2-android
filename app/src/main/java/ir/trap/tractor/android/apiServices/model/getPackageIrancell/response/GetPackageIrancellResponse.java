
package ir.trap.tractor.android.apiServices.model.getPackageIrancell.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPackageIrancellResponse {


    @SerializedName("packages")
    @Expose
    private IrancellPackage irancellPackage;


    public IrancellPackage getIrancellPackage() {
        return irancellPackage;
    }

    public void setIrancellPackage(IrancellPackage irancellPackage) {
        this.irancellPackage = irancellPackage;
    }
}
