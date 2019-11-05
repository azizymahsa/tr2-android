package ir.traap.tractor.android.apiServices.model.getBillCodePayCode;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/21/2019.
 */
public class GetBillCodePayCodeRequest
{
    @SerializedName("parameter")
    @Expose
    private String parameter;
    @SerializedName("bill_type")
    @Expose
    private Integer billType;

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }
}
