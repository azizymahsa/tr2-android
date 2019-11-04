package ir.traap.tractor.android.apiServices.model.getVersion.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class GetVersionRequest
{
    @SerializedName("version")
    @Expose @Getter @Setter
    private Integer version;
}
