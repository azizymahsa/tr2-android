package com.traap.traapapp.apiServices.model.profile.deleteProfile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class DeleteProfileResponse
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("popular_player")
    @Expose @Getter @Setter
    private Integer popularPlayer;

    @SerializedName("username")
    @Expose @Getter @Setter
    private String userName;

    @SerializedName("first_name")
    @Expose @Getter @Setter
    private String firstName;

    @SerializedName("last_name")
    @Expose @Getter @Setter
    private String lastName;

    @SerializedName("national_code")
    @Expose @Getter @Setter
    private String nationalCode;

    @SerializedName("email")
    @Expose @Getter @Setter
    private String email;

    @SerializedName("birthday")
    @Expose @Getter @Setter
    private String birthday;

    @SerializedName("english_name")
    @Expose @Getter @Setter
    private String englishName;

    @SerializedName("key_invite")
    @Expose @Getter @Setter
    private Integer keyInvite;

    @SerializedName("sex")
    @Expose @Getter @Setter
    private Integer gender;

    @SerializedName("share")
    @Expose @Getter @Setter
    private String shareText;

    @SerializedName("photo")
    @Expose @Getter @Setter
    private String photoUrl;

    @SerializedName("first_english_name")
    @Expose @Getter @Setter
    private String firstNameUS;

    @SerializedName("last_english_name")
    @Expose @Getter @Setter
    private String lastNameUS;

}
