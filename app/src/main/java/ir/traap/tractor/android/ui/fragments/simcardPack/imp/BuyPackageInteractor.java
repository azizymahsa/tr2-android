package ir.traap.tractor.android.ui.fragments.simcardPack.imp;

import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.traap.tractor.android.ui.fragments.payment.PaymentActionView;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public interface BuyPackageInteractor
{
    interface OnFinishedBuyPackageListener
    {
        void onFinishedMciBuyPackagePackage(WebServiceClass<PackageBuyResponse> response);

        void onErrorMciBuyPackagePackage(String error);
    }

   void findBuyPackageDataRequest(PaymentActionView listener,
                              String requestId, int operatorType,
                              Integer cardId, String titlePackageType,
                              int profileId, String mobile, String pass,
                              String cvv2, String expDate, String price);


}
