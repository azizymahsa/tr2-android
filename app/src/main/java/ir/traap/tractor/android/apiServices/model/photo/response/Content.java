
package ir.traap.tractor.android.apiServices.model.photo.response;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Content implements Serializable
{

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("caption")
    @Expose
    private Object caption;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_cover")
    @Expose
    private Boolean isCover;
    @SerializedName("likes")
    @Expose
    private Integer likes;
    @SerializedName("order_item")
    @Expose
    private Integer orderItem;
    @SerializedName("create_date_formatted")
    @Expose
    private String createDateFormatted;
    @SerializedName("update_date_formatted")
    @Expose
    private String updateDateFormatted;
    @SerializedName("is_bookmarked")
    @Expose
    private Boolean isBookmarked;
    @SerializedName("is_liked")
    @Expose
    private Boolean isLiked;
    @SerializedName("image_name")
    @Expose
    private ImageName imageName;
    private final static long serialVersionUID = -8818234929390553252L;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getCaption() {
        return caption;
    }

    public void setCaption(Object caption) {
        this.caption = caption;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getIsCover() {
        return isCover;
    }

    public void setIsCover(Boolean isCover) {
        this.isCover = isCover;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getCreateDateFormatted() {
        return createDateFormatted;
    }

    public void setCreateDateFormatted(String createDateFormatted) {
        this.createDateFormatted = createDateFormatted;
    }

    public String getUpdateDateFormatted() {
        return updateDateFormatted;
    }

    public void setUpdateDateFormatted(String updateDateFormatted) {
        this.updateDateFormatted = updateDateFormatted;
    }

    public Boolean getIsBookmarked() {
        return isBookmarked;
    }

    public void setIsBookmarked(Boolean isBookmarked) {
        this.isBookmarked = isBookmarked;
    }

    public Boolean getIsLiked() {
        return isLiked;
    }

    public void setIsLiked(Boolean isLiked) {
        this.isLiked = isLiked;
    }

    public ImageName getImageName() {
        return imageName;
    }

    public void setImageName(ImageName imageName) {
        this.imageName = imageName;
    }

}
