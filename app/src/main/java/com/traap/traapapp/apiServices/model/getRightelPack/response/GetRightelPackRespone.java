
package com.traap.traapapp.apiServices.model.getRightelPack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetRightelPackRespone {


    @SerializedName("RequestId")
    @Expose
    private String requestId;
    @SerializedName("packages")
    @Expose
    private Packages packages;


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
