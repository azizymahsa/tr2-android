
package com.traap.traapapp.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DestinationAirport {

    @SerializedName("Code")
    @Expose
    private String code;
    @SerializedName("Name")
    @Expose
    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
