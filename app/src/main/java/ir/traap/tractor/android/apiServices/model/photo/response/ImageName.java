
package ir.traap.tractor.android.apiServices.model.photo.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageName implements Serializable
{

    @SerializedName("thumbnail_large")
    @Expose
    private String thumbnailLarge;
    @SerializedName("thumbnail_small")
    @Expose
    private String thumbnailSmall;
    private final static long serialVersionUID = -1712213055120978177L;

    public String getThumbnailLarge() {
        return thumbnailLarge;
    }

    public void setThumbnailLarge(String thumbnailLarge) {
        this.thumbnailLarge = thumbnailLarge;
    }

    public String getThumbnailSmall() {
        return thumbnailSmall;
    }

    public void setThumbnailSmall(String thumbnailSmall) {
        this.thumbnailSmall = thumbnailSmall;
    }

}
