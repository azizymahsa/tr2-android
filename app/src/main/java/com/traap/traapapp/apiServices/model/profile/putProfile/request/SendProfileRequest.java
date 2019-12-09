package com.traap.traapapp.apiServices.model.profile.putProfile.request;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;
import okhttp3.MultipartBody;
import retrofit2.http.Multipart;

public class SendProfileRequest
{
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
    private String birthday = null;

    @SerializedName("english_name")
    @Expose @Getter @Setter
    private String nickName;

    @SerializedName("sex")
    @Expose @Getter @Setter
    private Integer gender;

    @SerializedName("first_english_name")
    @Expose @Getter @Setter
    private String firstNameUS;

    @SerializedName("email")
    @Expose @Getter @Setter
    private String email;

    @SerializedName("last_english_name")
    @Expose @Getter @Setter
    private String lastNameUS;

}
