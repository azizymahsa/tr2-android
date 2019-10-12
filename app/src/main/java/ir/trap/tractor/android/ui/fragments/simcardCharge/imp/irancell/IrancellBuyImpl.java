package ir.trap.tractor.android.ui.fragments.simcardCharge.imp.irancell;

import ir.trap.tractor.android.apiServices.generator.SingletonService;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.mobileCharge.request.MobileChargeRequest;
import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
public class IrancellBuyImpl implements IrancellBuyInteractor
{
    @Override
    public void findDataIrancellBuyRequest(OnFinishedIrancellBuyListener listener, int userId, int profileType,
                                           int amount, String cardNumber, String password, String chargeMobileNumber,
                                           String cvv2, String expDate, int simcardType)
    {
//        IrancellChargeRequest request = new IrancellChargeRequest();
        MobileChargeRequest request = new MobileChargeRequest();
        request.setUserId(userId);
        request.setAmount(amount);
        request.setCardNumber(cardNumber);
        request.setChargeMobileNumber(chargeMobileNumber);
        request.setPassword(password);
//        request.setProfileType(profileType);
        request.setChargeType(profileType);
        request.setCvv2(cvv2);
        request.setExpDate(expDate);
        request.setSimcardType(simcardType);
        request.setOperatorType(1);

        SingletonService.getInstance().getMobileCharge().MobileChargeService(new OnServiceStatus<MobileChargeResponse>()
        {
            @Override
            public void onReady(MobileChargeResponse response)
            {
                try
                {
                    listener.onFinishedIrancellBuy(response, chargeMobileNumber);
                }
                catch (Exception e)
                {
                    listener.onErrorIrancellBuy(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorIrancellBuy(message);
            }
        }, request);

//        SingletonService.getInstance().getIrancell().IrancellBuyChargeService(new OnServiceStatus<IrancellChargeResponse>()
//        {
//            @Override
//            public void onReady(IrancellChargeResponse response)
//            {
//
//                try
//                {
//                    listener.onFinishedIrancellBuy(response);
//
//                } catch (Exception e)
//                {
//                    listener.onErrorIrancellBuy(e.getMessage());
//
//                }
//            }
//
//            @Override
//            public void onError(String message)
//            {
//                listener.onErrorIrancellBuy(message);
//
//
//            }
//        }, request);


    }
}
