package com.traap.traapapp.ui.fragments.simcardPack.imp;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.buyPackage.request.PackageBuyRequest;
import com.traap.traapapp.apiServices.model.buyPackage.response.PackageBuyResponse;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;
import com.traap.traapapp.ui.fragments.simcardPack.PackFragment;

/**
 * Created by Javad.Abadi on 8/25/2018.
 */
public class BuyPackageImpl implements BuyPackageInteractor
{

    @Override
    public void findBuyPackageDataRequest(PaymentActionView listener, String requestId, Integer operatorType, String titlePackageType, Integer profileId, String mobile, String amount)
    {
        PackageBuyRequest request = new PackageBuyRequest();
        request.setRequestId(requestId);
        request.setOperatorType(String.valueOf(operatorType));
        request.setTitlePackage(titlePackageType);
        request.setBundleId(String.valueOf(profileId));
        request.setMobile(mobile);
        if (amount.isEmpty()){
            amount="0";
        }
        request.setAmount(Integer.valueOf(amount.replaceAll(",", "")));
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
