
package com.traap.traapapp.apiServices.model.getInfoPhoneBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetInfoPhoneBillResponse {

    @SerializedName("lstPhoneBill")
    @Expose
    private List<LstPhoneBill> lstPhoneBill = null;
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

    public List<LstPhoneBill> getLstPhoneBill() {
        return lstPhoneBill;
    }

    public void setLstPhoneBill(List<LstPhoneBill> lstPhoneBill) {
        this.lstPhoneBill = lstPhoneBill;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

}
