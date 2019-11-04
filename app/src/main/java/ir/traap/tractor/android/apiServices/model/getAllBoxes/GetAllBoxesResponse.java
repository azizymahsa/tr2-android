package ir.traap.tractor.android.apiServices.model.getAllBoxes;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * Created by MahtabAzizi on 10/27/2019.
 */
public class GetAllBoxesResponse
{
    @SerializedName("results")
    @Expose
    private List<AllBoxesResult> results = null;

    public List<AllBoxesResult> getResults()
    {
        return results;
    }

    public void setResults(List<AllBoxesResult> results)
    {
        this.results = results;
    }
}
