package ir.traap.tractor.android.apiServices.model.news.details.sendRate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class LikeNewsDetailResponse
{
    @SerializedName("liked")
    @Expose @Getter @Setter
    private Boolean isLiked;
}
