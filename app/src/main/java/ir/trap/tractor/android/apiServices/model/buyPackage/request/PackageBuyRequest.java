
package ir.trap.tractor.android.apiServices.model.buyPackage.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PackageBuyRequest
{

    @SerializedName("BundleId")
    @Expose
    private String bundleId;
    @SerializedName("PackageType")
    @Expose
    private String packageType;
    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("Mobile")
    @Expose
    private String mobileNumber;
    @SerializedName("Pan")
    @Expose
    private String cardNumber;
    @SerializedName("Pin2")
    @Expose
    private String password;
    @SerializedName("TitlePackage")
    @Expose
    private String titlePackage;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("Cvv2")
    @Expose
    private String cvv2;
    @SerializedName("ExpDate")
    @Expose
    private String expDate;
    @SerializedName("OperatorType")
    @Expose
    private String operatorType;
    @SerializedName("RequestId")
    @Expose
    private String requestId;
    @SerializedName("ProfileId")
    @Expose
    private String profileId;

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getCvv2() {
        return cvv2;
    }

    public void setCvv2(String cvv2) {
        this.cvv2 = cvv2;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
    }

    public String getBundleId() {
        return bundleId;
    }

    public void setBundleId(String bundleId) {
        this.bundleId = bundleId;
    }

    public String getPackageType() {
        return packageType;
    }

    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTitlePackage() {
        return titlePackage;
    }

    public void setTitlePackage(String titlePackage) {
        this.titlePackage = titlePackage;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

}
