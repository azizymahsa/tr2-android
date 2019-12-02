package com.traap.traapapp.apiServices.model.profile.putProfile.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SendProfileResponse
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("popular_player")
    @Expose @Getter @Setter
    private Integer popularPlayer;

    @SerializedName("first_name")
    @Expose @Getter @Setter
    private String firstName;

    @SerializedName("last_name")
    @Expose @Getter @Setter
    private String lastName;

    @SerializedName("national_code")
    @Expose @Getter @Setter
    private String nationalCode;

    @SerializedName("birthday")
    @Expose @Getter @Setter
    @Nullable
    private String birthday;

    @SerializedName("english_name")
    @Expose @Getter @Setter
    private String englishName;
}
