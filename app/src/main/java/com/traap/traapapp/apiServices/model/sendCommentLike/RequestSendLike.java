package com.traap.traapapp.apiServices.model.sendCommentLike;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestSendLike
{

    @SerializedName("value")
    @Expose
    private Boolean value;
    private final static long serialVersionUID = -7177445455352960916L;

    public Boolean getValue()
    {
        return value;
    }

    public void setValue(Boolean value)
    {
        this.value = value;
    }
}
