
package ir.trap.tractor.android.apiServices.model.billPayment.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BillPaymentResponse {

    @SerializedName("RefNo")
    @Expose
    private String refNo;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

}
