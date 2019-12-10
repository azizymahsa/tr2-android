package com.traap.traapapp.apiServices.model.buyChargeWallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 12/10/2019.
 */
public class BuyChargeWalletRequest
{
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("operator_type")
    @Expose
    private Integer operatorType;
    @SerializedName("sim_card_type")
    @Expose
    private Integer simCardType;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("pin2")
    @Expose
    private String pin2;
    @SerializedName("type_charge")
    @Expose
    private Integer typeCharge;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getSimCardType() {
        return simCardType;
    }

    public void setSimCardType(Integer simCardType) {
        this.simCardType = simCardType;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getPin2() {
        return pin2;
    }

    public void setPin2(String pin2) {
        this.pin2 = pin2;
    }

    public Integer getTypeCharge() {
        return typeCharge;
    }

    public void setTypeCharge(Integer typeCharge) {
        this.typeCharge = typeCharge;
    }
}
