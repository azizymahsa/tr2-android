
package ir.traap.tractor.android.apiServices.model.league.pastResult.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Outcomes {

    @SerializedName("extra_time")
    @Expose
    private Object extraTime;
    @SerializedName("half_time")
    @Expose
    private String halfTime;
    @SerializedName("full_time")
    @Expose
    private String fullTime;

    public Object getExtraTime() {
        return extraTime;
    }

    public void setExtraTime(Object extraTime) {
        this.extraTime = extraTime;
    }

    public String getHalfTime() {
        return halfTime;
    }

    public void setHalfTime(String halfTime) {
        this.halfTime = halfTime;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

}
