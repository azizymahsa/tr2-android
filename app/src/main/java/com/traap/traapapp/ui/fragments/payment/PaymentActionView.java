package com.traap.traapapp.ui.fragments.payment;

import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;

public interface PaymentActionView
{
    void onPaymentGDSFlight();
    void onPaymentGDSHotel();
    void onPaymentGDSBus();

    void onPaymentChargeSimCard(MobileChargeResponse data, String mobile);
    void onErrorCharge(String message);

    void onPaymentPackSimCard(PackageBuyResponse response, String mobile);
    void onErrorPackSimcard(String message);

    void onPaymentTransferMoney();

    void onPaymentWithoutCard();

    void onPaymentBill();

    void onPaymentTicket();
}
