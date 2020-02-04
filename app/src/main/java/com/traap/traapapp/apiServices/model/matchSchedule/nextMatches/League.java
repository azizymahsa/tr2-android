
package com.traap.traapapp.apiServices.model.matchSchedule.nextMatches;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class League implements Serializable
{

    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("cupName")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;
    private final static long serialVersionUID = -4499877742249947732L;

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
