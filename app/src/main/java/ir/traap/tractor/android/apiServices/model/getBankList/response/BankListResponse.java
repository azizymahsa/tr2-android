package ir.traap.tractor.android.apiServices.model.getBankList.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class BankListResponse
{
    @SerializedName("count")
    @Expose @Getter @Setter
    private Integer count;

    @SerializedName("next")
    @Expose @Getter @Setter
    @Nullable
    private Integer next = null;

    @SerializedName("previous")
    @Expose @Getter @Setter
    @Nullable
    private Integer previous = null;

    @SerializedName("results")
    @Expose @Getter @Setter
    private List<Bank> bankList;

}
