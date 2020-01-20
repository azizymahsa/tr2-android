package com.traap.traapapp.ui.fragments.simcardPack.imp;

import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.response.BuyPackageWalletResponse;

/**
 * Created by MahtabAzizi on 1/19/2020.
 */
public interface BuyPackageWalletInteractor
{

    interface OnBuyPackageWalletListener
    {
        void onSuccessBuyPackageWallet(WebServiceClass<BuyPackageWalletResponse> response);

        void onErrorBuyPackageWallet(String error);
    }

    void findBuyPackageWalletRequest(OnBuyPackageWalletListener listener,
                                    String operatorType,
                                    String amount,
                                    String mobile,
                                    String pin2,
                                    String requestId,
                                     String bundleId,
                                     String titlePackage);

}
