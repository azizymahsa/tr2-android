package com.traap.traapapp.apiServices.model.getSimPackageList.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class SimPackage
{
    @SerializedName("duration_package")
    @Expose @Getter @Setter
    private String durationPackage;

    @SerializedName("content")
    @Expose @Getter @Setter
    private List<SimContentItem> contentList;

}
