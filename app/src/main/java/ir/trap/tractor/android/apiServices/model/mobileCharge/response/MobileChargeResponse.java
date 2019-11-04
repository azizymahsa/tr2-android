package ir.trap.tractor.android.apiServices.model.mobileCharge.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ir.trap.tractor.android.apiServices.model.ServiceMessage;

/**
 * Created by Javad.Abadi on 9/16/2019.
 */
public class MobileChargeResponse
{

    @SerializedName("amount")
    @Expose
    private Integer amount;

    @SerializedName("TrnBizKey")
    @Expose
    private String trnBizKey;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("create_date")
    @Expose
    private String createDate;


    public Integer getAmount()
    {
        return amount;
    }

    public void setAmount(Integer amount)
    {
        this.amount = amount;
    }

    public String getTrnBizKey() {
        return trnBizKey;
    }

    public void setTrnBizKey(String trnBizKey) {
        this.trnBizKey = trnBizKey;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

   /* @SerializedName("CreateDate")
    @Expose
    private String createDate;

    @SerializedName("RefNo")
    @Expose
    private String refNo;

    @SerializedName("Id")
    @Expose
    private Integer id;

    @SerializedName("TrnBizKey")
    @Expose
    private String trnBizKey;

    @SerializedName("Amount")
    @Expose
    private Integer amount;

    @SerializedName("ServiceMessage")
    @Expose
    private ServiceMessage serviceMessage;


    public String getCreateDate() { return createDate; }

    public void setCreateDate(String createDate) { this.createDate = createDate; }

    public String getRefNo() { return refNo; }

    public void setRefNo(String refNo) { this.refNo = refNo; }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getTrnBizKey() { return trnBizKey; }

    public void setTrnBizKey(String trnBizKey) { this.trnBizKey = trnBizKey; }

    public Integer getAmount() { return amount; }

    public void setAmount(Integer amount) { this.amount = amount; }

    public ServiceMessage getServiceMessage() { return serviceMessage; }

    public void setServiceMessage(ServiceMessage serviceMessage) { this.serviceMessage = serviceMessage; }
*/
}
