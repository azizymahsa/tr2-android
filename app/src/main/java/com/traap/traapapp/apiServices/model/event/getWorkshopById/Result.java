
package com.traap.traapapp.apiServices.model.event.getWorkshopById;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result implements Serializable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("register_start_date")
    @Expose
    private Integer registerStartDate;
    @SerializedName("register_end_date")
    @Expose
    private Integer registerEndDate;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("available_capacity")
    @Expose
    private Integer availableCapacity;
    @SerializedName("event")
    @Expose
    private Integer event;
    private final static long serialVersionUID = -8138985867328885035L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRegisterStartDate() {
        return registerStartDate;
    }

    public void setRegisterStartDate(Integer registerStartDate) {
        this.registerStartDate = registerStartDate;
    }

    public Integer getRegisterEndDate() {
        return registerEndDate;
    }

    public void setRegisterEndDate(Integer registerEndDate) {
        this.registerEndDate = registerEndDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAvailableCapacity() {
        return availableCapacity;
    }

    public void setAvailableCapacity(Integer availableCapacity) {
        this.availableCapacity = availableCapacity;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

}
