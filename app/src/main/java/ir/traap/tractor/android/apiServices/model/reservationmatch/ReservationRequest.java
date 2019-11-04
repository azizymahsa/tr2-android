package ir.traap.tractor.android.apiServices.model.reservationmatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class ReservationRequest
{
    @SerializedName("match_id")
    @Expose
    private Integer matchId;
    @SerializedName("viewers")
    @Expose
    private Integer viewers;
    @SerializedName("box_id")
    @Expose
    private Integer boxId;

    public Integer getMatchId() {
        return matchId;
    }

    public void setMatchId(Integer matchId) {
        this.matchId = matchId;
    }

    public Integer getViewers() {
        return viewers;
    }

    public void setViewers(Integer viewers) {
        this.viewers = viewers;
    }

    public Integer getBoxId() {
        return boxId;
    }

    public void setBoxId(Integer boxId) {
        this.boxId = boxId;
    }
}
