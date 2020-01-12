package com.traap.traapapp.apiServices.model.getTransaction;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Getter;
import lombok.Setter;

public class TransactionList
{
    @SerializedName("id")
    @Expose @Getter @Setter
    private Integer id;

    @SerializedName("amount")
    @Expose @Getter @Setter
    private Integer amount;

    @SerializedName("status")
    @Expose @Getter @Setter
    private Boolean status;

    @SerializedName("ref_no")
    @Expose @Getter @Setter
    private String refNo;

    @SerializedName("code_payment")
    @Expose @Getter @Setter
    private Object codePayment;

    @SerializedName("type_transaction")
    @Expose @Getter @Setter
    private String typeTransaction;

    @SerializedName("create_date")
    @Expose @Getter @Setter
    private String createDate;

    @SerializedName("create_date_formatted")
    @Expose @Getter @Setter
    private String create_date_formatted;

    @SerializedName("type_transaction_id")
    @Expose @Getter @Setter
    private Integer typeTransactionId;
}