package com.traap.traapapp.apiServices.model.getSimPackageList.response;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class SimContentItem
{
    @SerializedName("bill_amount")
    @Expose @Getter @Setter
    private long billAmount;

    @SerializedName("amount")
    @Expose @Getter @Setter
    private long amount;

    @SerializedName("package_id")
    @Expose @Getter @Setter
    private int packageId;

    @SerializedName("package_type")
    @Expose @Getter @Setter
    private String packageType;

    @SerializedName("title_package_type")
    @Expose @Getter @Setter
    private String titlePackageType;

    @SerializedName("nick_name")
    @Expose @Getter @Setter
    private String nickName;

    @SerializedName("title")
    @Expose @Getter @Setter
    private String title;

}
