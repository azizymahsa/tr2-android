package com.traap.traapapp.ui.fragments.simcardCharge.imp.mci;


import com.traap.traapapp.apiServices.generator.SingletonService;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.mobileCharge.request.MobileChargeRequest;
import com.traap.traapapp.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
public class MciBuyImpl implements MciBuyInteractor
{
    @Override
    public void findDataMciBuyInRequest(OnFinishedMciBuyInListener listener, int userId, String chargeType,
                                        int amount, String cardNumber, String password, String chargeMobileNumber, String cvv2, String expDate)
    {
//        MciBuyRequest request = new MciBuyRequest();
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
        request.setOperatorType(2);

        SingletonService.getInstance().getMobileCharge().MobileChargeService(new OnServiceStatus<WebServiceClass<MobileChargeResponse>>()
        {
            @Override
            public void onReady(WebServiceClass<MobileChargeResponse> response)
            {
                try
                {
                    listener.onFinishedMciBuyIn(response.data, chargeMobileNumber);
                } catch (Exception e)
                {
                    listener.onErrorMciBuyIn(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorMciBuyIn(message);
            }
        }, request);

//        SingletonService.getInstance().getMCI().MciBuyInService(new OnServiceStatus<MciBuyResponse>()
//        {
//            @Override
//            public void onReady(MciBuyResponse mciBuyResponse)
//            {
//                try
//                {
//                    listener.onFinishedMciBuyIn(mciBuyResponse);
//                } catch (Exception e)
//                {
//                    listener.onErrorMciBuyIn(e.getMessage());
//                }
//            }
//
//            @Override
//            public void showErrorMessage(String message)
//            {
//                listener.onErrorMciBuyIn(message);
//
//            }
//        }, request);

    }
}
