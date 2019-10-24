package ir.trap.tractor.android.apiServices.model.paymentPrintPos;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentPrintPosResponse {


    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("ResponseStatusDebit")
    @Expose
    private String responseStatusDebit;
    @SerializedName("DataIdPrint")
    @Expose
    private Long dataIdPrint;
    @SerializedName("CreateDate")
    @Expose
    private String createDate;
    @SerializedName("CreateDateDebit")
    @Expose
    private String createDateDebit;
    @SerializedName("MerchantName")
    @Expose
    private String merchantName;
    @SerializedName("MerchantPhone")
    @Expose
    private String merchantPhone;
    @SerializedName("TrnBizKey")
    @Expose
    private String tnBizKey;
    @SerializedName("Amount")
    @Expose
    private Integer amount;
    @SerializedName("CariCode")
    @Expose
    private String cariCode;
    @SerializedName("BalanceAmount")
    @Expose
    private String balanceAmount;
    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("TerminalId")
    @Expose
    private String terminalId;
    @SerializedName("PrintId")
    @Expose
    private String pirntId;
    @SerializedName("RefNo")
    @Expose
    private String refNo;

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getPirntId() {
        return pirntId;
    }

    public void setPirntId(String pirntId) {
        this.pirntId = pirntId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }



    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseStatusDebit() {
        return responseStatusDebit;
    }

    public void setResponseStatusDebit(String responseStatusDebit) {
        this.responseStatusDebit = responseStatusDebit;
    }

    public Long getDataIdPrint() {
        return dataIdPrint;
    }

    public void setDataIdPrint(Long dataIdPrint) {
        this.dataIdPrint = dataIdPrint;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateDebit() {
        return createDateDebit;
    }

    public void setCreateDateDebit(String createDateDebit) {
        this.createDateDebit = createDateDebit;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantPhone() {
        return merchantPhone;
    }

    public void setMerchantPhone(String merchantPhone) {
        this.merchantPhone = merchantPhone;
    }

    public String getTnBizKey() {
        return tnBizKey;
    }

    public void setTnBizKey(String tnBizKey) {
        this.tnBizKey = tnBizKey;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCariCode() {
        return cariCode;
    }

    public void setCariCode(String cariCode) {
        this.cariCode = cariCode;
    }

    public String getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(String balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

}

