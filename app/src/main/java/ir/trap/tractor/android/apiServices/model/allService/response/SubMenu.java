
package ir.trap.tractor.android.apiServices.model.allService.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubMenu {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("image_name")
    @Expose
    private String imageName;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("is_visible")
    @Expose
    private Boolean isVisible;
    @SerializedName("key_id")
    @Expose
    private Integer keyId;
    @SerializedName("key_name")
    @Expose
    private String keyName;
    @SerializedName("order_item")
    @Expose
    private Integer orderItem;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Integer getKeyId() {
        return keyId;
    }

    public void setKeyId(Integer keyId) {
        this.keyId = keyId;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

}
