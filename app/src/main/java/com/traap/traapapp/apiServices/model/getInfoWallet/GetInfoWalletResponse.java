
package com.traap.traapapp.apiServices.model.getInfoWallet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.LstPhoneBill;
import com.traap.traapapp.apiServices.model.getInfoPhoneBill.ServiceMessage;

import java.util.List;

public class GetInfoWalletResponse
{


    @SerializedName("date_time")
    @Expose
    private String lstPhoneBill;
    @SerializedName("full_name")
    @Expose
    private String full_name;

    @SerializedName("card_no")
    @Expose
    private String card_no;

    @SerializedName("customer_code")
    @Expose
    private String customer_code;

    public String getLstPhoneBill()
    {
        return lstPhoneBill;
    }

    public void setLstPhoneBill(String lstPhoneBill)
    {
        this.lstPhoneBill = lstPhoneBill;
    }

    public String getFull_name()
    {
        return full_name;
    }

    public void setFull_name(String full_name)
    {
        this.full_name = full_name;
    }

    public String getCard_no()
    {
        return card_no;
    }

    public void setCard_no(String card_no)
    {
        this.card_no = card_no;
    }

    public String getCustomer_code()
    {
        return customer_code;
    }

    public void setCustomer_code(String customer_code)
    {
        this.customer_code = customer_code;
    }
}
