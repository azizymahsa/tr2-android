package ir.trap.tractor.android.apiServices.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by RezaNejati on 9/30/2018.
 */
public class GlobalResponse3
{
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

    public ServiceMessage getServiceMessage()
    {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage)
    {
        this.serviceMessage = serviceMessage;
    }
}