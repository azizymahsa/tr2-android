package ir.trap.tractor.android.apiServices.model.getPackageMci.response.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Javad.Abadi on 6/8/2019.
 */
public class GetPackageMciRequest {

    @SerializedName("mobile")
    @Expose
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
