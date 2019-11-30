package com.traap.traapapp.apiServices.model.paymentMatch;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class PaymentMatchResponse
{
    @SerializedName("url")
    @Expose
    private String url;

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }
}
