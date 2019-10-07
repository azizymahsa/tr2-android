
package ir.trap.tractor.android.apiServices.model.getRightelPack.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Detail {

    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("BundleId")
    @Expose
    private String bundleId;
    @SerializedName("Amount")
    @Expose
    private String amount;
    @SerializedName("BillAmount")
    @Expose
    private String billAmount;
    @SerializedName("TitlePackageType")
    @Expose
    private String titlePackageType;
    @SerializedName("PackageType")
    @Expose
    private String PackageType;
    @SerializedName("Duration")
    @Expose
    private Integer duration;
    @SerializedName("Systems")
    @Expose
    private Integer Systems;
    @SerializedName("ProfileId")
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

    public Integer getSystems() {
        return Systems;
    }

    public void setSystems(Integer systems) {
        Systems = systems;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillAmount() {
        return billAmount;
    }

    public void setBillAmount(String billAmount) {
        this.billAmount = billAmount;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

}
