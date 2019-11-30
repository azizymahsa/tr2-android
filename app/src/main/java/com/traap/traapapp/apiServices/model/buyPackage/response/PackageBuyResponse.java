
package com.traap.traapapp.apiServices.model.buyPackage.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageBuyResponse
{

    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;
    @SerializedName("TrnBizKey")
    @Expose
    private String trnBizKey;
    @SerializedName("ResultCode")
    @Expose
    private String resultCode;
    @SerializedName("ResponseMessage")
    @Expose
    private String responseMessage;
    @SerializedName("GuidMciPackage")
    @Expose
    private String guidMciPackage;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;

    @SerializedName("RefNo")
    @Expose
    private String refNo;

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

    public String getTrnBizKey() {
        return trnBizKey;
    }

    public void setTrnBizKey(String trnBizKey) {
        this.trnBizKey = trnBizKey;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getGuidMciPackage() {
        return guidMciPackage;
    }

    public void setGuidMciPackage(String guidMciPackage) {
        this.guidMciPackage = guidMciPackage;
    }

}
