package ir.trap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FlightPrice
{
    @SerializedName("BasePrice")
    @Expose
    @Getter @Setter
    private Integer BasePrice;

    @SerializedName("FullPrice")
    @Expose
    @Getter @Setter
    private Integer FullPrice;

    @SerializedName("OperatorFee")
    @Expose
    @Getter @Setter
    private Integer OperatorFee;

    @SerializedName("SalePrice")
    @Expose
    @Getter @Setter
    private Integer SalePrice;

}
