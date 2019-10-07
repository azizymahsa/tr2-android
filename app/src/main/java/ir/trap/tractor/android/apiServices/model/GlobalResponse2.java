package ir.trap.tractor.android.apiServices.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RezaNejati on 9/30/2018.
 */
public class GlobalResponse2
{
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;
    @SerializedName("Token")
    @Expose
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public ServiceMessage getServiceMessage()
    {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage)
    {
        this.serviceMessage = serviceMessage;
    }
}