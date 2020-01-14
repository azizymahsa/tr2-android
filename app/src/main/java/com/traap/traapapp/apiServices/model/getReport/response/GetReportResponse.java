
package com.traap.traapapp.apiServices.model.getReport.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetReportResponse {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("info")
    @Expose
    private Info info;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
