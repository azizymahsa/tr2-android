package ir.trap.tractor.android.apiServices.model.verify;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/16/2019.
 */
public class VerifyResponse {

    @SerializedName("profile")
    @Expose
    private Profile profile;

    @SerializedName("access")
    @Expose
    private String access;

    @SerializedName("refresh")
    @Expose
    private String refresh;

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getRefresh() {
        return refresh;
    }

    public void setRefresh(String refresh) {
        this.refresh = refresh;
    }

}
