package ir.trap.tractor.android.apiServices.model.card.editCard.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class EditCardRequest
{

    @SerializedName("card_number")
    @Expose @Getter @Setter
    private String cardNumber;

    @SerializedName("full_name")
    @Expose @Getter @Setter
    private String fullName;

    @SerializedName("order_list")
    @Expose @Getter @Setter
    private Integer orderList;

    @SerializedName("is_main_card")
    @Expose @Getter @Setter
    private Boolean isMainCard = false;

}
