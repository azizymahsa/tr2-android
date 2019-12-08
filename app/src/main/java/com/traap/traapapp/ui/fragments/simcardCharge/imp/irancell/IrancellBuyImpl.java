package com.traap.traapapp.ui.fragments.simcardCharge.imp.irancell;

import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.mobileCharge.request.MobileChargeRequest;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;
import com.traap.traapapp.ui.fragments.payment.PaymentActionView;
import com.traap.traapapp.ui.fragments.simcardCharge.ChargeFragment;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
public class IrancellBuyImpl implements IrancellBuyInteractor
{
    @Override
    public void findDataIrancellBuyRequest(PaymentActionView listener, String price, int simcardType, int operatorType, int typeCharge, String password
            , String mobile, String cvv2, String expDate,Integer cardId)
    {
        MobileChargeRequest request = new MobileChargeRequest();
       request.setAmount(Integer.valueOf(price.replaceAll(",", "")));
       request.setMobile(mobile);
       //operator type 1
       request.setOperatorType(operatorType);
       request.setSimCardType(simcardType);
       request.setTypeCharge(typeCharge);

        SingletonService.getInstance().getMobileCharge().MobileChargeService(new OnServiceStatus<WebServiceClass<MobileChargeResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MobileChargeResponse> response)
            {
                try
                {
                    if (response.info.statusCode==200)
                    {
                        listener.onPaymentChargeSimCard(response.data, mobile);
                    }else {
                        listener.onErrorCharge(response.info.message);
                    }

                }
                catch (Exception e)
                {
                    listener.onErrorCharge(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorCharge(message);
            }
        }, request);
    }

    public void findDataIrancellBuyRequest(PaymentActionView listener, String amount, int operatorType, int simcardType, String chargeType, String mobile)
    {
        MobileChargeRequest request = new MobileChargeRequest();
        request.setAmount(Integer.valueOf(amount.replaceAll(",", "")));
        request.setMobile(mobile);
        //operator type 1
        request.setOperatorType(operatorType);
        request.setSimCardType(simcardType);
        request.setTypeCharge(Integer.valueOf(chargeType));

        SingletonService.getInstance().getMobileCharge().MobileChargeService(new OnServiceStatus<WebServiceClass<MobileChargeResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MobileChargeResponse> response)
            {
                try
                {
                    if (response.info.statusCode==200)
                    {
                        listener.onPaymentChargeSimCard(response.data, mobile);
                    }else {
                        listener.onErrorCharge(response.info.message);
                    }

                }
                catch (Exception e)
                {
                    listener.onErrorCharge(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorCharge(message);
            }
        }, request);
    }
}
