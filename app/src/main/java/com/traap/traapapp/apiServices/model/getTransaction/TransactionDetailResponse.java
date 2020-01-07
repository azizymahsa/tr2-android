package com.traap.traapapp.apiServices.model.getTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by MahtabAzizi on 12/11/2019.
 */
public class TransactionDetailResponse
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("amount")
    @Expose
    private Integer amount;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("ref_no")
    @Expose
    private String refNo;
    @SerializedName("code_payment")
    @Expose
    private Object codePayment;
    @SerializedName("type_payment")
    @Expose
    private String typePayment;
    @SerializedName("type_transaction")
    @Expose
    private String typeTransaction;
    @SerializedName("create_date")
    @Expose
    private String createDate;
    @SerializedName("create_date_formatted")
    @Expose
    private String create_date_formatted;
    @SerializedName("type_transaction_id")
    @Expose
    private Integer typeTransactionId;

    @SerializedName("detail")
    @Expose
    private DetailTransaction detailTransaction;

    public DetailTransaction getDetailTransaction()
    {
        return detailTransaction;
    }

    public void setDetailTransaction(DetailTransaction detailTransaction)
    {
        this.detailTransaction = detailTransaction;
    }

    public String getCreate_date_formatted()
    {
        return create_date_formatted;
    }

    public void setCreate_date_formatted(String create_date_formatted)
    {
        this.create_date_formatted = create_date_formatted;
    }

    public String getTypePayment()
    {
        return typePayment;
    }

    public void setTypePayment(String typePayment)
    {
        this.typePayment = typePayment;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public Object getCodePayment() {
        return codePayment;
    }

    public void setCodePayment(Object codePayment) {
        this.codePayment = codePayment;
    }

    public String getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(String typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public Integer getTypeTransactionId() {
        return typeTransactionId;
    }

    public void setTypeTransactionId(Integer typeTransactionId) {
        this.typeTransactionId = typeTransactionId;
    }

}
