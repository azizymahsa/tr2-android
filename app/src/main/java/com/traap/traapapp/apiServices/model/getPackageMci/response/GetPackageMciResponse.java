
package com.traap.traapapp.apiServices.model.getPackageMci.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPackageMciResponse {

    @SerializedName("packages")
    @Expose
    private Packages packages;

    public Packages getPackages() {
        return packages;
    }

    public void setPackages(Packages packages) {
        this.packages = packages;
    }

}
