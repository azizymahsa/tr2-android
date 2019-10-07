package ir.trap.tractor.android.ui.fragments.simcardCharge.imp.irancell;

import ir.trap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
public interface IrancellBuyInteractor {
    interface OnFinishedIrancellBuyListener {
        void onFinishedIrancellBuy(MobileChargeResponse response, String mobileChargeNo);
        void onErrorIrancellBuy(String error);
    }

    void findDataIrancellBuyRequest(OnFinishedIrancellBuyListener listener, int userId, int profileType, int amount,
                                    String cardNumber, String password, String chargeMobileNumber, String cvv2, String expDate, int simcardType);
}


