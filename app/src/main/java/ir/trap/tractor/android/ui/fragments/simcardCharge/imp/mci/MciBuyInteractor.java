package ir.trap.tractor.android.ui.fragments.simcardCharge.imp.mci;

import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
public interface MciBuyInteractor
{
    interface OnFinishedMciBuyInListener
    {
        void onFinishedMciBuyIn(MobileChargeResponse response, String mobileChargeNo);

        void onErrorMciBuyIn(String error);
    }

    void findDataMciBuyInRequest(OnFinishedMciBuyInListener listener, int userId, String ChargeType, int amount,
                                 String cardNumber, String password, String chargeMobileNumber, String cvv2, String expDate);

}
