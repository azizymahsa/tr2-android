package ir.traap.tractor.android.apiServices.model.predict.getPredict.response;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class Chart
{
    @SerializedName("chart_prediction")
    @Expose @Getter @Setter
    @Nullable
    private Integer chartPrediction;

    @SerializedName("total_user")
    @Expose @Getter @Setter
    private Double totalUser;

}
