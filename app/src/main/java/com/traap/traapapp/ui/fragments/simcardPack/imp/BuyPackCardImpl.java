package com.traap.traapapp.ui.fragments.simcardPack.imp;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyChargeCard.BuyChargeCardRequest;
import com.traap.traapapp.apiServices.model.buyChargeWallet.BuyChargeWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;
import com.traap.traapapp.apiServices.model.buyPackageCard.request.BuyPackageCardRequest;
import com.traap.traapapp.utilities.Logger;

public class BuyPackCardImpl
{
    public static void BuyPackCard(String operatorType, int amount, String mobile, String pin2, String pan, String cvv2, String exDate,
                                   String titlePackage, String bundleId, String requestId, onBuyPackCardListener listener)
    {
        BuyPackageCardRequest request = new BuyPackageCardRequest();
        request.setAmount(amount);
        request.setCvv2(cvv2);
        request.setExpDate(exDate);
        request.setMobile(mobile);
        request.setOperatorType(operatorType);
        request.setPan(pan);
        request.setPin2(pin2);
        request.setBundleId(bundleId);
        request.setRequestId(requestId);
        request.setTitlePackage(titlePackage);

        SingletonService.getInstance().buyCardService().buyPackageCardService(request, new OnServiceStatus<WebServiceClass<BuyPackageWalletResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BuyPackageWalletResponse> response)
            {
                if (response == null || response.data == null)
                {
                    listener.onBuyPackCardError("خطا در دریافت اطلاعات از سرور!");
                    Logger.e("-onBuyChargeCardError-", "null");
                    return;
                }
                if (response.info.statusCode != 201)
                {
                    listener.onBuyPackCardError(response.info.message);
                }
                else
                {
                    listener.onBuyPackCardCompleted(response.info.message);
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onBuyPackCardError(message);
            }
        });
    }

    public interface onBuyPackCardListener
    {
        void onBuyPackCardCompleted(String message);

        void onBuyPackCardError(String message);
    }
}
