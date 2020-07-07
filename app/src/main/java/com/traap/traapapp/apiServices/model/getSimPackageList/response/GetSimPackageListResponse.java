package com.traap.traapapp.apiServices.model.getSimPackageList.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class GetSimPackageListResponse
{
    @SerializedName("packages")
    @Expose @Getter @Setter
    private List<SimPackage> packageList;
}
