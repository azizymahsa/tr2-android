package com.traap.traapapp.ui.fragments.simcardPack.imp;

import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;
import com.traap.traapapp.ui.fragments.simcardPack.PackFragment;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public interface BuyPackageInteractor
{
    void findBuyPackageDataRequest(PaymentActionView packFragment, String requestId, Integer operatorType, String titlePackageType, Integer profileId, String mobile, String amount);

    interface OnFinishedBuyPackageListener
    {
        void onFinishedMciBuyPackagePackage(WebServiceClass<PackageBuyResponse> response);

        void onErrorMciBuyPackagePackage(String error);
    }

}
