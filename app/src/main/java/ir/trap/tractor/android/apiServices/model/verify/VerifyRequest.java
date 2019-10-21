package ir.trap.tractor.android.apiServices.model.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class VerifyRequest {

    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("screen_size_width")
    @Expose
    private String screenSizeWidth;

    @SerializedName("screen_size_height")
    @Expose
    private String screenSizeHeight;

    @SerializedName("current_version")
    @Expose
    private String currentVersion;


    @SerializedName("device_type")
    @Expose
    private int device_type;

    @SerializedName("imei")
    @Expose
    private String imei;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getScreenSizeWidth()
    {
        return screenSizeWidth;
    }

    public void setScreenSizeWidth(String screenSizeWidth)
    {
        this.screenSizeWidth = screenSizeWidth;
    }

    public String getScreenSizeHeight()
    {
        return screenSizeHeight;
    }

    public void setScreenSizeHeight(String screenSizeHeight)
    {
        this.screenSizeHeight = screenSizeHeight;
    }

    public String getCurrentVersion()
    {
        return currentVersion;
    }

    public void setCurrentVersion(String currentVersion)
    {
        this.currentVersion = currentVersion;
    }


    public int getDevice_type()
    {
        return device_type;
    }

    public void setDevice_type(int device_type)
    {
        this.device_type = device_type;
    }

    public String getImei()
    {
        return imei;
    }

    public void setImei(String imei)
    {
        this.imei = imei;
    }
}
