package ir.traap.tractor.android.ui.fragments.paymentGateWay.paymentWallet;

import java.util.List;

import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;
import ir.traap.tractor.android.apiServices.model.paymentMatch.Viewers;
import ir.traap.tractor.android.apiServices.model.paymentWallet.ResponsePaymentWallet;

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
