package ir.traap.tractor.android.ui.fragments.paymentGateWay.paymentWallet;


import ir.traap.tractor.android.apiServices.generator.SingletonService;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.traap.tractor.android.apiServices.model.paymentWallet.ResponsePaymentWallet;

/**
 * Created by Mahsa.azizi on 11/25/2019.
 */
public class PaymentWalletImpl implements PaymentWalletInteractor
{
    @Override
    public void paymentWalletRequest(OnFinishedPaymentWalletListener listener,PaymentMatchRequest paymentMatchRequest)
    {
        PaymentMatchRequest request = new PaymentMatchRequest();
        request.setPin2(paymentMatchRequest.getPin2());
        request.setAmount(paymentMatchRequest.getAmount());
        request.setViewers(paymentMatchRequest.getViewers());
        SingletonService.getInstance().paymentMatch().PaymentWalletService(new OnServiceStatus<WebServiceClass<ResponsePaymentWallet>>()
        {
            @Override
            public void onReady(WebServiceClass<ResponsePaymentWallet> response)
            {
                try
                {
                    if (response.info.statusCode == 200)
                    {
                        listener.onFinishedPaymentWallet(response.data);
                    }
                    else
                    {
                        listener.onErrorPaymentWallet(response.info.message);
                    }
                }
                catch (Exception e)
                {

                    listener.onErrorPaymentWallet(e.getMessage());
                }
            }

            @Override
            public void onError(String message)
            {
                listener.onErrorPaymentWallet(message);

            }
        }, request);

    }

}


