
package com.traap.traapapp.apiServices.model.mainPage;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("banks")
    @Expose
    private List<Bank> bank = null;
    @SerializedName("to")
    @Expose
    private List<Object> to = null;
    @SerializedName("from")
    @Expose
    private List<From> from = null;

    public List<Bank> getBank() {
        return bank;
    }

    public void setBank(List<Bank> bank) {
        this.bank = bank;
    }

    public List<Object> getTo() {
        return to;
    }

    public void setTo(List<Object> to) {
        this.to = to;
    }

    public List<From> getFrom() {
        return from;
    }

    public void setFrom(List<From> from) {
        this.from = from;
    }

}
