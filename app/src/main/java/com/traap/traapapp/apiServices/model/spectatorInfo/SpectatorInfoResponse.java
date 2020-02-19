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


}
