package ir.traap.tractor.android.apiServices.model.reservationmatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class ReservationResponse
{
    @SerializedName("amount")
    @Expose @Getter @Setter
    private Integer amount;

    @SerializedName("results")
    @Expose @Getter @Setter
    private List<Integer> results = null;

}
