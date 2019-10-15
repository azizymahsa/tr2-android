package ir.trap.tractor.android.ui.fragments.simcardPack.imp;

import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;

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

    void findBuyPackageDataRequest(OnFinishedBuyPackageListener listener, int bundleId, String packageType, int userId,
                                      String cardNumber, String passwor, String mobileNumber, String titlePackage,
                                   String amount, String cvv2, String expDate, String OperatorType, String RequestId);


}
