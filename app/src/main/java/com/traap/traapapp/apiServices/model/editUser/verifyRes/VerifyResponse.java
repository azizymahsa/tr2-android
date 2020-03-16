package com.traap.traapapp.apiServices.model.editUser.verifyRes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.allService.response.Info;
import com.traap.traapapp.apiServices.model.availableAmount.Data;

public class VerifyResponse {

    @SerializedName("info")
    @Expose
    private Info info;
    @SerializedName("data")
    @Expose
    private Data data;

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}