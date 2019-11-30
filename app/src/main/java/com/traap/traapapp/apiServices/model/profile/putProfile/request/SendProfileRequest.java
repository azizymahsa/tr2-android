package com.traap.traapapp.apiServices.model.profile.putProfile.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SendProfileRequest
{
    @SerializedName("popular_player")
    @Expose @Getter @Setter
    private Integer popularPlayer;

    @SerializedName("key_invite")
    @Expose @Getter @Setter
    private Integer keyInvite;

    @SerializedName("english_name")
    @Expose @Getter @Setter
    private String englishName;

    @SerializedName("national_code")
    @Expose @Getter @Setter
    private String nationalCode;

    @SerializedName("first_name")
    @Expose @Getter @Setter
    private String firstName;

    @SerializedName("last_name")
    @Expose @Getter @Setter
    private String lastName;

    @SerializedName("birthday")
    @Expose @Getter @Setter
    private String birthday;
}
