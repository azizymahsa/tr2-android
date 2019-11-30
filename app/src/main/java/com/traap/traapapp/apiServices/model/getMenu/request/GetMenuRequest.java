package com.traap.traapapp.apiServices.model.getMenu.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class GetMenuRequest
{
    @SerializedName("device_type")
    @Expose @Getter @Setter
    private Integer deviceType;

    @SerializedName("density")
    @Expose @Getter @Setter
    private Float density;

}
