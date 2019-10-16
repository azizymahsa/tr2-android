package ir.trap.tractor.android.apiServices.model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.trap.tractor.android.apiServices.model.GlobalResponse;

/**
 * Created by MahtabAzizi on 10/15/2019.
 */
public class LoginResponse {

    @SerializedName("info")
    @Expose
    private GlobalResponse info;

    @SerializedName("data")
    @Expose
    private Data data;


    public GlobalResponse getInfo() {
        return info;
    }

    public void setInfo(GlobalResponse info) {
        this.info = info;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
