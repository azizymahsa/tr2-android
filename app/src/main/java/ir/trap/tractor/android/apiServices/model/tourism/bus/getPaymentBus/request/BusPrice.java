
package ir.trap.tractor.android.apiServices.model.tourism.bus.getPaymentBus.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BusPrice {

    @SerializedName("SalePrice")
    @Expose
    private Integer salePrice;
    @SerializedName("DiscountedPrice")
    @Expose
    private Integer discountedPrice;
    @SerializedName("FullPrice")
    @Expose
    private Integer fullPrice;

    public Integer getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(Integer salePrice) {
        this.salePrice = salePrice;
    }

    public Integer getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(Integer discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public Integer getFullPrice() {
        return fullPrice;
    }

    public void setFullPrice(Integer fullPrice) {
        this.fullPrice = fullPrice;
    }

}
