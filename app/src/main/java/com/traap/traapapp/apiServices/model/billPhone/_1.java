package com.traap.traapapp.apiServices.model.billPhone;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class _1 {

@SerializedName("billid")
@Expose
private String billid;
@SerializedName("pay_code")
@Expose
private String payCode;
@SerializedName("status")
@Expose
private Integer status;
@SerializedName("amount")
@Expose
private Integer amount;

public String getBillid() {
return billid;
}

public void setBillid(String billid) {
this.billid = billid;
}

public String getPayCode() {
return payCode;
}

public void setPayCode(String payCode) {
this.payCode = payCode;
}

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public Integer getAmount() {
return amount;
}

public void setAmount(Integer amount) {
this.amount = amount;
}

}