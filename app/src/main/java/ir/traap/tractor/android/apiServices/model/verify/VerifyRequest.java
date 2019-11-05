package ir.traap.tractor.android.apiServices.model.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class VerifyRequest
{

    @SerializedName("username")
    @Expose @Getter @Setter
    private String username;

    @SerializedName("code")
    @Expose @Getter @Setter
    private String code;

    @SerializedName("screen_size_width")
    @Expose @Getter @Setter
    private String screenSizeWidth;

    @SerializedName("screen_size_height")
    @Expose @Getter @Setter
    private String screenSizeHeight;

    @SerializedName("current_version")
    @Expose @Getter @Setter
    private String currentVersion;

    @SerializedName("device_model")
    @Expose @Getter @Setter
    private String deviceModel;

    @SerializedName("device_type")
    @Expose @Getter @Setter
    private int device_type;

    @SerializedName("imei")
    @Expose @Getter @Setter
    private String imei;

}
