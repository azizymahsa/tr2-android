package ir.trap.tractor.android.ui.fragments.simcardCharge.imp.rightel;

import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/25/2018.
 */
public interface RightelBuyInteractor {
    interface OnFinishedRightelBuyListener {
        void onFinishedRightelBuy(MobileChargeResponse response, String mobileChargeNo);
        void onErrorRightelBuy(String error);
    }

    void findRightelBuyDataRequest(OnFinishedRightelBuyListener listener, int userId, String chargeType, int amount,
                                   String cardNumber, String password, String chargeMobileNumber, String cvv2, String expDate);



}
