package com.traap.traapapp.ui.fragments.simcardCharge.imp;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;

/**
 * Created by MahtabAzizi on 12/10/2019.
 */
public class BuyChargeWalletImpl implements BuyChargeWalletInteractor
{
    @Override
    public void findBuyChargeWalletRequest(OnBuyChargeWalletListener listener, int operatorType, int simCardType, int typeCharge, String amount, String mobile, String pin2)
    {
        BuyChargeWalletRequest request = new BuyChargeWalletRequest();
        request.setMobile(mobile);
        request.setOperatorType(operatorType);
        request.setPin2(pin2);
        request.setSimCardType(simCardType);
        request.setTypeCharge(typeCharge);
        request.setAmount(Integer.valueOf(amount.replaceAll(",", "")));
        SingletonService.getInstance().buyChargeWalletService().BuyChargeWalletService(new OnServiceStatus<WebServiceClass<BuyChargeWalletResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BuyChargeWalletResponse> getBuyChargeWalletResponse)
            {
                try
                {
                    listener.onSuccessBuyChargeWallet(getBuyChargeWalletResponse);

                } catch (Exception e)
                {
                    listener.onErrorBuyChargeWallet(e.getMessage());


                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorBuyChargeWallet(message);

            }
        }, request);
    }
}
