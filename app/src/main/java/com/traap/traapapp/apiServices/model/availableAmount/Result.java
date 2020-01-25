
package com.traap.traapapp.apiServices.model.availableAmount;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("operator_type")
    @Expose
    private Integer operatorType;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("amount_with_tax")
    @Expose
    private Integer amount_with_tax;
    @SerializedName("charge_type")
    @Expose
    private Integer chargeType;
    @SerializedName("sim_card_type")
    @Expose
    private Integer simCardType;

    public Integer getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(Integer operatorType) {
        this.operatorType = operatorType;
    }

    public Integer getAmount() {
        return amount;
    }

    public Integer getAmount_with_tax()
    {
        return amount_with_tax;
    }

    public void setAmount_with_tax(Integer amount_with_tax)
    {
        this.amount_with_tax = amount_with_tax;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getChargeType() {
        return chargeType;
    }

    public void setChargeType(Integer chargeType) {
        this.chargeType = chargeType;
    }

    public Integer getSimCardType() {
        return simCardType;
    }

    public void setSimCardType(Integer simCardType) {
        this.simCardType = simCardType;
    }

}
