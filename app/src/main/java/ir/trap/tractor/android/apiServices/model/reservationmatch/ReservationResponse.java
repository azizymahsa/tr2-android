package ir.trap.tractor.android.apiServices.model.reservationmatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class ReservationResponse
{
    @SerializedName("results")
    @Expose
    private List<Integer> results = null;

    public List<Integer> getResults() {
        return results;
    }

    public void setResults(List<Integer> results) {
        this.results = results;
    }
}
