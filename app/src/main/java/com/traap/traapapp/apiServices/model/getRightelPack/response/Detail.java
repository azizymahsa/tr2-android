
package com.traap.traapapp.apiServices.model.getRightelPack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("amount")
    @Expose
    private String amount;

    @SerializedName("title_package_type")
    @Expose
    private String titlePackageType;

    @SerializedName("package_type")
    @Expose
    private String PackageType;

    @SerializedName("profile_id")
    @Expose
    private Integer profileId;

    public String getTitlePackageType() {
        return titlePackageType;
    }

    public void setTitlePackageType(String titlePackageType) {
        this.titlePackageType = titlePackageType;
    }

    public String getPackageType() {
        return PackageType;
    }

    public void setPackageType(String packageType) {
        PackageType = packageType;
    }

    public Integer getProfileId() {
        return profileId;
    }

    public void setProfileId(Integer profileId) {
        this.profileId = profileId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

}
