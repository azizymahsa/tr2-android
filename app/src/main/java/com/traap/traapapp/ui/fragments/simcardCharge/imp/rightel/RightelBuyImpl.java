package com.traap.traapapp.ui.fragments.simcardCharge.imp.rightel;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.mobileCharge.request.MobileChargeRequest;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/25/2018.
 */
public class RightelBuyImpl implements RightelBuyInteractor
{
    @Override
    public void findRightelBuyDataRequest(OnFinishedRightelBuyListener listener, int userId, String chargeType,
                                          int amount, String cardNumber, String password, String chargeMobileNumber, String cvv2, String expDate)
    {
//        RightelBuyRequest request = new RightelBuyRequest();
        MobileChargeRequest request = new MobileChargeRequest();
       /* request.setUserId(userId);
        request.setAmount(amount);
        request.setCardNumber(cardNumber);
        request.setChargeMobileNumber(chargeMobileNumber);
        request.setPassword(password);
        request.setChargeType(Integer.parseInt(chargeType));
        request.setCvv2(cvv2);
        request.setExpDate(expDate);
        request.setSimcardType(0);*/
        request.setOperatorType(3);

        SingletonService.getInstance().getMobileCharge().MobileChargeService(new OnServiceStatus<WebServiceClass<MobileChargeResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MobileChargeResponse> response)
            {
                try
                {
                    listener.onFinishedRightelBuy(response.data, chargeMobileNumber);
                } catch (Exception e)
                {
                    listener.onErrorRightelBuy(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorRightelBuy(message);
            }
        }, request);

//        SingletonService.getInstance().getRightel().RightelBuyService(new OnServiceStatus<RightelBuyResponse>()
//        {
//            @Override
//            public void onReady(RightelBuyResponse rightelBuyResponse)
//            {
//                try
//                {
//                    listener.onFinishedRightelBuy(rightelBuyResponse);
//
//                } catch (Exception e)
//                {
//                    listener.onErrorRightelBuy(e.getMessage());
//
//
//                }
//            }
//
//            @Override
//            public void showErrorMessage(String message)
//            {
//                listener.onErrorRightelBuy(message);
//
//            }
//        }, request);
    }
}
