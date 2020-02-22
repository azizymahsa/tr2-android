
package com.traap.traapapp.apiServices.model.mainPage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bin")
    @Expose
    private String bin;
    @SerializedName("logo")
    @Expose
    private String logo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

}
