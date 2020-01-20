package com.traap.traapapp.ui.fragments.simcardPack.imp;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.request.BuyPackageWalletRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;

/**
 * Created by MahtabAzizi on 1/19/2020.
 */
public class BuyPackageWalletImpl implements BuyPackageWalletInteractor
{
    @Override
    public void findBuyPackageWalletRequest(OnBuyPackageWalletListener listener, String operatorType, String amount, String mobile, String pin2, String requestId, String bundleId, String titlePackage)
    {

        BuyPackageWalletRequest request = new BuyPackageWalletRequest();
        request.setMobile(mobile);
        request.setOperatorType(operatorType);
        request.setPin2(pin2);
        request.setRequestId(requestId);
        request.setBundleId(bundleId);
        request.setTitlePackage(titlePackage);
        request.setAmount(Integer.valueOf(amount.replaceAll(",", "")));
        SingletonService.getInstance().buyPackageWalletService().BuyPackageWalletService(new OnServiceStatus<WebServiceClass<BuyPackageWalletResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<BuyPackageWalletResponse> getBuyPackageWalletResponse)
            {
                try
                {
                    listener.onSuccessBuyPackageWallet(getBuyPackageWalletResponse);

                } catch (Exception e)
                {
                    listener.onErrorBuyPackageWallet(e.getMessage());


                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorBuyPackageWallet(message);

            }
        }, request);

    }
}