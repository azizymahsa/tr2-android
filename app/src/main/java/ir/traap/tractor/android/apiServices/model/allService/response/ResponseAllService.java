
package ir.traap.tractor.android.apiServices.model.allService.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseAllService {

    @SerializedName("data")
    @Expose
    private List<Datum> data = null;
    @SerializedName("info")
    @Expose
    private Info info;

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
