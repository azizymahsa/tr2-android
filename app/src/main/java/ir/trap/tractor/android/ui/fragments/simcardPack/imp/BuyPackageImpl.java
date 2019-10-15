package ir.trap.tractor.android.ui.fragments.simcardPack.imp;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.buyPackage.request.PackageBuyRequest;
import ir.trap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public class BuyPackageImpl implements BuyPackageInteractor
{

    @Override
    public void findBuyPackageDataRequest(OnFinishedBuyPackageListener listener, int bundleId,
                                             String packageType, int userId, String cardNumber,
                                             String password, String mobileNumber, String titlePackage,
                                             String amount, String cvv2, String expDate, String OperatorType, String RequestId) {
        PackageBuyRequest request = new PackageBuyRequest();
        request.setAmount(Integer.valueOf(amount));
        request.setBundleId(bundleId+"");
        request.setMobileNumber(mobileNumber);
        request.setCardNumber(cardNumber);
       // request.setPackageType(packageType);
        request.setPassword(password);
        request.setTitlePackage(titlePackage);
        request.setUserId(userId);
        request.setCvv2(cvv2);
        request.setExpDate(expDate);
       // request.setProfileId(packageType);
        request.setOperatorType(OperatorType);
        request.setRequestId(RequestId);

        SingletonService.getInstance().packageBuyService().MciPackageBuyService(new OnServiceStatus<WebServiceClass<PackageBuyResponse>>() {
            @Override
            public void onReady(WebServiceClass<PackageBuyResponse> response) {
                try {
                    listener.onFinishedMciBuyPackagePackage(response);

                } catch (Exception e) {
                    listener.onErrorMciBuyPackagePackage(e.getMessage());
                }
            }

            @Override
            public void onError(String message) {
                listener.onErrorMciBuyPackagePackage(message);

            }
        }, request);

    }

}
