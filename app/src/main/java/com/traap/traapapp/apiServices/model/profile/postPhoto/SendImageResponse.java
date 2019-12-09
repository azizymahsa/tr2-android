package com.traap.traapapp.apiServices.model.profile.postPhoto;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SendImageResponse
{
    @Getter @Setter
    @SerializedName("photo")
    private String imageUrl;
}
