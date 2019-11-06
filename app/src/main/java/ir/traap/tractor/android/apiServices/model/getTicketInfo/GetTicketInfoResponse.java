package ir.traap.tractor.android.apiServices.model.getTicketInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by MahtabAzizi on 11/6/2019.
 */
public class GetTicketInfoResponse
{
    @SerializedName("results")
    @Expose
    private List<ResultTicketInfo> results = null;

    public List<ResultTicketInfo> getResults() {
        return results;
    }

    public void setResults(List<ResultTicketInfo> results) {
        this.results = results;
    }
}
