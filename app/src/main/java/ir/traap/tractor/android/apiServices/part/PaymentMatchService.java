package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.traap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;
import ir.traap.tractor.android.apiServices.model.paymentWallet.ResponsePaymentWallet;

/**
 * Created by MahtabAzizi on 11/3/2019.
 */
public class PaymentMatchService extends BasePart
{
    public PaymentMatchService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void PaymentMatchService(OnServiceStatus<WebServiceClass<PaymentMatchResponse>> listener, PaymentMatchRequest req)
    {
        start(getServiceGenerator().createService().paymentMatch(req), listener);
    }

    public void PaymentWalletService(OnServiceStatus<WebServiceClass<ResponsePaymentWallet>> listener, PaymentMatchRequest req)
    {
        start(getServiceGenerator().createService().paymentWallet(req), listener);
    }

}
