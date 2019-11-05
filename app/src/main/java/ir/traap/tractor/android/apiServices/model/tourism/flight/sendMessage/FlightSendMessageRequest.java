
package ir.traap.tractor.android.apiServices.model.tourism.flight.sendMessage;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class FlightSendMessageRequest
{
    @SerializedName("Pin2")
    @Expose
    @Getter @Setter
    private String password;

    @SerializedName("Cvv2")
    @Expose
    @Getter @Setter
    private String cvv2;

    @SerializedName("ExpDate")
    @Expose
    @Getter @Setter
    private String expDate;

    @SerializedName("Pan")
    @Expose
    @Getter @Setter
    private String pan;

    @SerializedName("UserId")
    @Expose
    @Getter @Setter
    private String userId;

    @SerializedName("OfferId")
    @Expose
    @Getter @Setter
    private String offerId;

    @SerializedName("BookedReservation")
    @Expose
    @Getter @Setter
    private BookedReservation bookedReservation ;
}
