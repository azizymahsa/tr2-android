package com.traap.traapapp.ui.fragments.paymentGateWay.paymentWallet;

import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;

/**
 * Created by Mahsa.azizi on 11/25/2019.
 */
public interface PaymentWalletInteractor
{
    void paymentWalletRequest(OnFinishedPaymentWalletListener listener, PaymentMatchRequest paymentMatchRequest);


    interface OnFinishedPaymentWalletListener {
        void onFinishedPaymentWallet(ResponsePaymentWallet response);
        void onErrorPaymentWallet(String error);
    }
}
