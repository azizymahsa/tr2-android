package com.traap.traapapp.apiServices.model.getSimPackageList.request;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class GetSimPackageListRequest
{
    @SerializedName("type_operator")
    @Expose @Getter @Setter
    private int operatorType;

    @SerializedName("mobile")
    @Expose @Getter @Setter
    private String mobile;
}
