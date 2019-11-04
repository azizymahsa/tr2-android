package ir.traap.tractor.android.apiServices.model.getAllBoxes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 10/27/2019.
 */
public class GetAllBoxesRequest
{
    @SerializedName("viewers")
    @Expose
    private Integer viewers;

    @SerializedName("match_id")
    @Expose
    private Integer matchId;

    public Integer getViewers() {
        return viewers;
    }

    public void setViewers(Integer viewers) {
        this.viewers = viewers;
    }

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }
}
