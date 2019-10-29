package ir.trap.tractor.android.ui.fragments.simcardPack.imp;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.buyPackage.request.PackageBuyRequest;
import ir.trap.tractor.android.apiServices.model.buyPackage.response.PackageBuyResponse;
import ir.trap.tractor.android.ui.fragments.payment.PaymentActionView;
import ir.trap.tractor.android.ui.fragments.payment.PaymentFragment;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public class BuyPackageImpl implements BuyPackageInteractor
{

    public void findBuyPackageDataRequest(PaymentActionView listener,
                                          String requestId, int operatorType,
                                          Integer cardId, String titlePackageType,
                                          int profileId, String mobile, String pass,
                                          String cvv2, String expDate, String price)
    {
        PackageBuyRequest request = new PackageBuyRequest();
        request.setRequestId(requestId);
        request.setOperatorType(String.valueOf(operatorType));
        request.setCardId(cardId);
        request.setTitlePackage(titlePackageType);
        request.setBundleId(String.valueOf(profileId));
        request.setMobile(mobile);
        request.setPin2(pass);
        request.setCvv2(cvv2);
        request.setExpDate(expDate);
        if (price.isEmpty()){
            price="0";
        }
        request.setAmount(Integer.valueOf(price.replaceAll(",", "")));
        SingletonService.getInstance().packageBuyService().MciPackageBuyService(new OnServiceStatus<WebServiceClass<PackageBuyResponse>>(){

            @Override
            public void onReady(WebServiceClass<PackageBuyResponse> response)
            {

                try {
                    if (response.info.statusCode==200)
                    {
                        listener.onPaymentPackSimCard(response.data,mobile);
                    }else {
                        listener.onErrorPackSimcard(response.info.message);
                    }
                } catch (Exception e) {
                    listener.onErrorPackSimcard(e.getMessage());
                }


            }

            @Override
            public void onError(String message)
            {
                listener.onErrorPackSimcard(message);

            }
        },request);

    }
}
