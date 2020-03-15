package com.traap.traapapp.apiServices.model.editUser.verifyReq;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VerifyRequest {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("device_model")
    @Expose
    private String deviceModel;
    @SerializedName("screen_size_height")
    @Expose
    private String screenSizeHeight;
    @SerializedName("screen_size_width")
    @Expose
    private String screenSizeWidth;
    @SerializedName("imei")
    @Expose
    private String imei;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("current_version")
    @Expose
    private String currentVersion;
    @SerializedName("device_type")
    @Expose
    private Integer deviceType;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDeviceModel() {
        return deviceModel;
    }

    public void setDeviceModel(String deviceModel) {
        this.deviceModel = deviceModel;
    }

    public String getScreenSizeHeight() {
        return screenSizeHeight;
    }

    public void setScreenSizeHeight(String screenSizeHeight) {
        this.screenSizeHeight = screenSizeHeight;
    }

    public String getScreenSizeWidth() {
        return screenSizeWidth;
    }

    public void setScreenSizeWidth(String screenSizeWidth) {
        this.screenSizeWidth = screenSizeWidth;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCurrentVersion() {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion) {
        this.currentVersion = currentVersion;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

}