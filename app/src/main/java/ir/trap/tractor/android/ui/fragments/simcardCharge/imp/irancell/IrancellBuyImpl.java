package ir.trap.tractor.android.ui.fragments.simcardCharge.imp.irancell;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.trap.tractor.android.ui.fragments.payment.PaymentActionView;
import ir.trap.tractor.android.ui.fragments.payment.PaymentFragment;

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
       request.setCardId(cardId);
       request.setCvv2(cvv2);
       request.setExpDate(expDate);
       request.setMobile(mobile);
       //operator type 1
       request.setOperatorType(operatorType);
       request.setSimCardType(simcardType);
       request.setTypeCharge(typeCharge);
       request.setPin(password);
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
