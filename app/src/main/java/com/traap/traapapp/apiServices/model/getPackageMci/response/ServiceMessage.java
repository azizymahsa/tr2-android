
package com.traap.traapapp.apiServices.model.getPackageMci.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiceMessage {

    @SerializedName("Code")
    @Expose
    private Integer code;
    @SerializedName("Message")
    @Expose
    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
