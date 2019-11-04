
package ir.traap.tractor.android.apiServices.model.tourism;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.traap.tractor.android.apiServices.model.tourism.flight.valueInfo.ServiceMessage;

public class GetUserPassResponse
{

    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;
    @SerializedName("UniqeCode")
    @Expose
    private String uniqeCode;
    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUniqeCode() {
        return uniqeCode;
    }

    public void setUniqeCode(String uniqeCode) {
        this.uniqeCode = uniqeCode;
    }

    public ServiceMessage getServiceMessage() {
        return serviceMessage;
    }

    public void setServiceMessage(ServiceMessage serviceMessage) {
        this.serviceMessage = serviceMessage;
    }

}
