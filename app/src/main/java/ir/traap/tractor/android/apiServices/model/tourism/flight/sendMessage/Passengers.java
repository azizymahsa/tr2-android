
package ir.traap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Passengers {

    @SerializedName("ADT")
    @Expose
    private Integer aDT;
    @SerializedName("CHD")
    @Expose
    private Integer cHD;
    @SerializedName("INF")
    @Expose
    private Integer iNF;

    public Integer getADT() {
        return aDT;
    }

    public void setADT(Integer aDT) {
        this.aDT = aDT;
    }

    public Integer getCHD() {
        return cHD;
    }

    public void setCHD(Integer cHD) {
        this.cHD = cHD;
    }

    public Integer getINF() {
        return iNF;
    }

    public void setINF(Integer iNF) {
        this.iNF = iNF;
    }

}
