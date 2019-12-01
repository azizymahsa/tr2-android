package com.traap.traapapp.apiServices.model.news.details.getComment.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class User
{
    @Expose @Getter @Setter
    @SerializedName("last_name")
    private String lastName;

    @Expose @Getter @Setter
    @SerializedName("first_name")
    private String firstName;
}
