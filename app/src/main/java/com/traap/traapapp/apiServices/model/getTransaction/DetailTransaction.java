package com.traap.traapapp.apiServices.model.getTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 12/30/2019.
 */
public class DetailTransaction
{

    @SerializedName("type_charge")
    @Expose
    private Integer typeCharge;
    @SerializedName("type_sim_card")
    @Expose
    private Integer typeSimCard;
    @SerializedName("type_operator")
    @Expose
    private Integer typeOperator;
    @SerializedName("mobile_number")
    @Expose
    private String mobileNumber;

    @SerializedName("title_package")
    @Expose
    private String titlePackage;

    @SerializedName("destination_card")
    @Expose @Getter @Setter
    private String destinationCard;

    @SerializedName("card_holder")
    @Expose @Getter @Setter
    private String cardHolder;

    public Integer getTypeCharge()
    {
        return typeCharge;
    }

    public void setTypeCharge(Integer typeCharge)
    {
        this.typeCharge = typeCharge;
    }

    public Integer getTypeSimCard()
    {
        return typeSimCard;
    }

    public void setTypeSimCard(Integer typeSimCard)
    {
        this.typeSimCard = typeSimCard;
    }

    public Integer getTypeOperator()
    {
        return typeOperator;
    }

    public void setTypeOperator(Integer typeOperator)
    {
        this.typeOperator = typeOperator;
    }

    public String getMobileNumber()
    {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber)
    {
        this.mobileNumber = mobileNumber;
    }

    public String getTitlePackage()
    {
        return titlePackage;
    }

    public void setTitlePackage(String titlePackage)
    {
        this.titlePackage = titlePackage;
    }
}
