package com.traap.traapapp.ui.fragments.simcardCharge.imp;

import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;

/**
 * Created by MahtabAzizi on 12/10/2019.
 */
public interface BuyChargeWalletInteractor
{
    interface OnBuyChargeWalletListener
    {
        void onSuccessBuyChargeWallet(WebServiceClass<BuyChargeWalletResponse> response);

        void onErrorBuyChargeWallet(String error);
    }

    void findBuyChargeWalletRequest(OnBuyChargeWalletListener listener,
                                    int operatorType,
                                    int simCardType,
                                    int typeCharge,
                                    String amount,
                                    String mobile,
                                    String pin2);
}
