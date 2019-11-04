package ir.trap.tractor.android.apiServices.part;

import ir.trap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.trap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.trap.tractor.android.apiServices.model.WebServiceClass;
import ir.trap.tractor.android.apiServices.model.paymentMatch.PaymentMatchRequest;
import ir.trap.tractor.android.apiServices.model.paymentMatch.PaymentMatchResponse;

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

}
