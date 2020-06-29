package com.traap.traapapp.apiServices.model.sendComment;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSendComment implements Serializable
{

    @SerializedName("body")
    @Expose
    private String body;
    private final static long serialVersionUID = -7177445455352960916L;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
