
package ir.trap.tractor.android.apiServices.model.mobileCharge.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class MobileChargeRequest
{
    @SerializedName("UserId")
    @Expose
    @Getter @Setter
    private Integer userId;

    @SerializedName("Amount")
    @Expose
    @Getter @Setter
    private Integer amount;

    @SerializedName("Mobile")
    @Expose
    @Getter @Setter
    private String chargeMobileNumber;

    @SerializedName("Pan")
    @Expose
    @Getter @Setter
    private String cardNumber;

    @SerializedName("Pin2")
    @Expose
    @Getter @Setter
    private String password;

    @SerializedName("TypeCharge")
    @Expose
    @Getter @Setter
    private Integer chargeType;

    @SerializedName("Cvv2")
    @Expose
    @Getter @Setter
    private String cvv2;

    @SerializedName("ExpDate")
    @Expose
    @Getter @Setter
    private String expDate;

    @SerializedName("SimcardType")
    @Expose
    @Getter @Setter
    private Integer simcardType;    //daemi=1 , etebari=2

    @SerializedName("OperatorType")
    @Expose
    @Getter @Setter
    private Integer operatorType;   //mtn=1 , mci=2, rightel=3

}
