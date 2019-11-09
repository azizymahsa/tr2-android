package ir.traap.tractor.android.apiServices.part;


import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.GlobalResponse2;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.tourism.GetUserPassResponse;
import ir.traap.tractor.android.apiServices.model.tourism.flight.payment.request.FlightPaymentRequest;

/**
 * Created by Javad.Abadi on 2/23/2019.
 */
public class FlightService extends BasePart
{
    public FlightService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void flightPayment(OnServiceStatus<WebServiceClass<GlobalResponse2>> listener, FlightPaymentRequest request)
    {
        start(getServiceGenerator().createService().flightPayment(request), listener);
    }

    public void sendMessage(OnServiceStatus<WebServiceClass<GlobalResponse2>> listener, FlightPaymentRequest request)
    {
        start(getServiceGenerator().createService().flightSendMessage(request), listener);
    }

    public void userPass(OnServiceStatus<WebServiceClass<GetUserPassResponse>> listener)
    {
        start(getServiceGenerator().createService().getFlightUserPass(), listener);
    }
}