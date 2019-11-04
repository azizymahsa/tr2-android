package ir.traap.tractor.android.ui.fragments.simcardCharge.imp.irancell;

import ir.traap.tractor.android.apiServices.model.mobileCharge.response.MobileChargeResponse;
import ir.traap.tractor.android.ui.fragments.payment.PaymentActionView;

/**
 * Created by Javad.Abadi on 7/21/2018.
 */
public interface IrancellBuyInteractor {
    void findDataIrancellBuyRequest(PaymentActionView listener, String price, int simcardType, int operatorType, int typeCharge, String password, String mobile, String cvv2, String expDate, Integer cardId);


    interface OnFinishedIrancellBuyListener {
        void onFinishedIrancellBuy(MobileChargeResponse response, String mobileChargeNo);
        void onErrorIrancellBuy(String error);
    }

  /*  void findDataIrancellBuyRequest(OnFinishedIrancellBuyListener listener, int userId, int profileType, int amount,
                                    String cardNumber, String password, String chargeMobileNumber, String cvv2, String expDate, int simcardType);*/
}


