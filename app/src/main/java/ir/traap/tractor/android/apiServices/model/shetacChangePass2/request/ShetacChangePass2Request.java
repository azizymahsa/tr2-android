package ir.traap.tractor.android.apiServices.model.shetacChangePass2.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class ShetacChangePass2Request
{
    @SerializedName("card_id")
    @Expose @Setter @Getter
    private Integer cardId;

    @SerializedName("pin2_old")
    @Expose @Setter @Getter
    private String pin2Old;

    @SerializedName("pin2_new")
    @Expose @Setter @Getter
    private String pin2New;
}
