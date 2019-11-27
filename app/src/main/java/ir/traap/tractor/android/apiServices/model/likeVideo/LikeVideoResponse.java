package ir.traap.tractor.android.apiServices.model.likeVideo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 11/26/2019.
 */
public class LikeVideoResponse
{
    @SerializedName("is_liked")
    @Expose
    private Boolean isLiked;

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

}
