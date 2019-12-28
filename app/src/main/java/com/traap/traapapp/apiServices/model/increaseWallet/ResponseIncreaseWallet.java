package com.traap.traapapp.apiServices.model.increaseWallet;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseIncreaseWallet implements Serializable
{

    @SerializedName("url")
    @Expose
    private String url;
    private final static long serialVersionUID = -5075446545706723008L;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
