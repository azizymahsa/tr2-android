package com.traap.traapapp.apiServices.part;

import com.traap.traapapp.apiServices.generator.ServiceGenerator;
import com.traap.traapapp.apiServices.listener.OnServiceStatus;
import com.traap.traapapp.apiServices.model.WebServiceClass;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchRequest;
import com.traap.traapapp.apiServices.model.paymentMatch.PaymentMatchResponse;
import com.traap.traapapp.apiServices.model.paymentWallet.ResponsePaymentWallet;

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
