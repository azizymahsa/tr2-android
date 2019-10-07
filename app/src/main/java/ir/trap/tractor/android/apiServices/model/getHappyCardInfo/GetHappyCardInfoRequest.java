package ir.trap.tractor.android.apiServices.model.getHappyCardInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Javad.Abadi on 6/19/2019.
 */
public class GetHappyCardInfoRequest
{
    @SerializedName("Pan")
    @Expose
    private String Pan;
    @SerializedName("UserId")
    @Expose
    private String UserId;

    public String getPan() {
        return Pan;
    }

    public void setPan(String pan) {
        Pan = pan;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}
