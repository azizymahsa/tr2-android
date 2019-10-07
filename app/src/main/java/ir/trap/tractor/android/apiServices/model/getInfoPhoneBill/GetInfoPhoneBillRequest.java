
package ir.trap.tractor.android.apiServices.model.getInfoPhoneBill;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetInfoPhoneBillRequest {

    @SerializedName("UserId")
    @Expose
    private Integer userId;
    @SerializedName("PhoneNumber")
    @Expose
    private String phoneNumber;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}
