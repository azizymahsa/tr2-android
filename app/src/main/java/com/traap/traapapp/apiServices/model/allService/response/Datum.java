
package com.traap.traapapp.apiServices.model.allService.response;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("sub_menu")
    @Expose
    private List<SubMenu> subMenu = null;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("cupLogo")
    @Expose
    private String logo;
    @SerializedName("label")
    @Expose
    private Object label;
    @SerializedName("is_visible")
    @Expose
    private Boolean isVisible;
    @SerializedName("is_enable")
    @Expose
    private Boolean isEnable;
    @SerializedName("order_item")
    @Expose
    private Integer orderItem;
    @SerializedName("logo_selected")
    @Expose
    private String logoSelected;
    @SerializedName("device_type")
    @Expose
    private Integer deviceType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<SubMenu> getSubMenu() {
        return subMenu;
    }

    public void setSubMenu(List<SubMenu> subMenu) {
        this.subMenu = subMenu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Object getLabel() {
        return label;
    }

    public void setLabel(Object label) {
        this.label = label;
    }

    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean isEnable) {
        this.isEnable = isEnable;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public String getLogoSelected() {
        return logoSelected;
    }

    public void setLogoSelected(String logoSelected) {
        this.logoSelected = logoSelected;
    }

    public Integer getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(Integer deviceType) {
        this.deviceType = deviceType;
    }

}
