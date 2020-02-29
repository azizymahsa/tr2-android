package com.traap.traapapp.apiServices.model.spectatorInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 2/9/2020.
 */
public class SpectatorInfoResponse
{
    public SpectatorInfoResponse(String lastName, String firstName, String nationalCode)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.nationalCode = nationalCode;
    }

    public SpectatorInfoResponse()
    {
    }

    @Expose
    @Getter
    @Setter
    @SerializedName("last_name")
    private String lastName;

    @Expose
    @Getter
    @Setter
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @Getter
    @Setter
    @SerializedName("national_code")
    private String nationalCode;

    @Expose


    private Boolean isChecked=false;

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
